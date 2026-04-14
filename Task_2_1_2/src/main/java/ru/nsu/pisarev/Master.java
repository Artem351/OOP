package ru.nsu.pisarev;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

public class Master {

    private final int[] array;
    private final int port;
    private final int workerTimeoutMs = 15000;
    private final int taskTimeoutMs = 30000;
    private final int maxRetries = 3;

    private ServerSocket serverSocket;
    private volatile boolean running = true;
    private volatile boolean foundNonPrime = false;

    private final BlockingQueue<Task> pendingTasks = new LinkedBlockingQueue<>();
    private final Map<Integer, WorkerInfo> workers = new ConcurrentHashMap<>();
    private final Map<String, Task> activeTasks = new ConcurrentHashMap<>();
    private final Set<String> processedMessageIds = new ConcurrentSkipListSet<>();

    private final Object resultLock = new Object();

    public Master(int[] array, int port) {
        this.array = array;
        this.port = port;
    }

    public boolean start() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);

            System.out.println("Master started on port " + port);

            startTimeoutMonitor();
            Thread acceptThread = new Thread(this::acceptWorkers, "Master-Acceptor");
            acceptThread.start();
            distributeTasks();
            boolean result = waitForResults();
            shutdown();
            return result;
        } catch (IOException e) {
            System.err.println("Master error: " + e.getMessage());
            return false;
        }
    }

    private void acceptWorkers() {
        while (running && !foundNonPrime) {
            try {
                Socket client = serverSocket.accept();
                client.setSoTimeout(5000);

                int workerId = workers.size() + 1;
                WorkerInfo info = new WorkerInfo(workerId, client);
                workers.put(workerId, info);

                new Thread(() -> handleWorker(info), "Worker-" + workerId).start();
                System.out.println("Worker " + workerId + " connected from " +
                        client.getRemoteSocketAddress());

            } catch (SocketTimeoutException e) {
                //Nothing to do
            } catch (IOException e) {
                if (running) {
                    System.err.println("Accept error: " + e.getMessage());
                }
            }
        }
    }

    private void distributeTasks() {
        for (int i = 0; i < array.length && !foundNonPrime; i++) {
            String taskId = "task-" + UUID.randomUUID().toString().substring(0, 8);
            boolean offer = pendingTasks.offer(new Task(taskId, array[i], i));
            System.out.println("Task queued: " + taskId + " → " + array[i]);
        }
    }

    private void handleWorker(WorkerInfo worker) {
        try {
            MessageProtocol.sendObject(worker.getOut(),
                    new DataTransferObject(DataTransferObject.Command.PONG, "init", worker.getId()));
            while (running && !foundNonPrime && !worker.getSocket().isClosed()) {
                if (MessageProtocol.hasCompleteMessageHeader(worker.getSocket())) {
                    DataTransferObject msg = MessageProtocol.receiveObject(worker.getIn());
                    processWorkerMessage(worker, msg);
                }
                if (worker.isIdle() && !pendingTasks.isEmpty() && !foundNonPrime) {
                    sendTaskToWorker(worker);
                }
                Thread.sleep(50);
            }

        } catch (Exception e) {
            System.err.println("Worker " + worker.getId() + " error: " + e.getMessage());
            handleWorkerFailure(worker);
        } finally {
            worker.close();
            workers.remove(worker.getId());
            System.out.println("Worker " + worker.getId() + " disconnected");
        }
    }

    private void processWorkerMessage(WorkerInfo worker, DataTransferObject msg) {
        worker.setLastActivity(System.currentTimeMillis());

        if (!processedMessageIds.add(msg.getMessageId())) {
            System.out.println("Duplicate message ignored: " + msg.getMessageId());
            return;
        }

        switch (msg.getCommand()) {
            case HEARTBEAT:
                worker.setIdle(true);
                worker.setLastActivity(System.currentTimeMillis());
                break;
            case RESULT:
                handleResult(worker, msg);
                break;
            case ERROR:
                System.err.println("Worker " + worker.getId() + " reported error for task " + msg.getTaskId());
                requeueTask(msg.getTaskId());
                worker.setIdle(true);
                break;
            case ACK:
                break;
        }
    }

    private void handleResult(WorkerInfo worker, DataTransferObject msg) {
        Task completed = activeTasks.remove(msg.getTaskId());
        if (completed == null) {
            System.out.println("Unknown task completed: " + msg.getTaskId());
            worker.setIdle(true);
            return;
        }
        if (msg.getNonPrime() != null) {
            if (msg.getNonPrime()) {
                System.out.println("Found non-prime: " + msg.getNumber() +
                        " (task: " + msg.getTaskId() + ")");
                synchronized (resultLock) {
                    foundNonPrime = true;
                    resultLock.notifyAll();
                }
                broadcastShutdown();
            } else {
                System.out.println("Prime confirmed: " + msg.getNumber());
            }
        }
        worker.setIdle(true);
    }

    private void sendTaskToWorker(WorkerInfo worker) {
        Task task = pendingTasks.poll();
        if (task == null) {
            return;
        }

        task.setAssignedWorker(worker.getId());
        task.setAssignedTime(System.currentTimeMillis());
        activeTasks.put(task.getId(), task);

        DataTransferObject dto = new DataTransferObject(
                DataTransferObject.Command.ASSIGN_TASK, task.getId(), worker.getId());
        dto.setNumber(task.getNumber());
        dto.setSequenceNumber(task.getIndex());

        try {
            MessageProtocol.sendObject(worker.getOut(), dto);
            worker.setIdle(false);
            System.out.println("Task " + task.getId() + " sent to Worker " + worker.getId());
        } catch (IOException e) {
            System.err.println("Failed to send task: " + e.getMessage());
            requeueTask(task.getId());
        }
    }

    private void startTimeoutMonitor() {
        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(2000);
                    long now = System.currentTimeMillis();
                    for (WorkerInfo worker : workers.values()) {
                        if (now - worker.getLastActivity() > workerTimeoutMs) {
                            System.out.println("Worker " + worker.getId() + " timeout");
                            handleWorkerFailure(worker);
                        }
                    }
                    for (Task task : activeTasks.values()) {
                        if (now - task.getAssignedTime() > taskTimeoutMs) {
                            System.out.println("Task " + task.getId() + " timeout, re-queueing");
                            requeueTask(task.getId());
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "TimeoutMonitor").start();
    }

    private void handleWorkerFailure(WorkerInfo worker) {
        for (Task task : activeTasks.values()) {
            if (task.getAssignedWorker() == worker.getId()) {
                requeueTask(task.getId());
            }
        }
        activeTasks.values().removeIf(t -> t.getAssignedWorker() == worker.getId());
        worker.close();
    }

    private void requeueTask(String taskId) {
        Task task = activeTasks.remove(taskId);
        if (task != null) {
            if (task.getAttempts() < maxRetries) {
                task.setAttempts(task.getAttempts()+1);
                task.setAssignedWorker(-1);
                boolean offer = pendingTasks.offer(task);
                System.out.println("Task " + taskId + " re-queued (attempt " + task.getAttempts() + ")");
            } else {
                System.err.println("Task " + taskId + " failed after " + maxRetries + " attempts");
            }
        }
    }

    private boolean waitForResults() {
        long startTime = System.currentTimeMillis();

        /*
         * 2 minutes
         */
        final long globalTimeout = 120000;
        while (!foundNonPrime && running) {
            if (pendingTasks.isEmpty() && activeTasks.isEmpty()) {
                System.out.println("All tasks completed");
                return false;
            }

            if (System.currentTimeMillis() - startTime > globalTimeout) {
                System.out.println("Global timeout reached");
                return foundNonPrime;
            }

            synchronized (resultLock) {
                try {
                    resultLock.wait(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        return foundNonPrime;
    }

    private void broadcastShutdown() {
        System.out.println("Broadcasting SHUTDOWN to all workers");
        for (WorkerInfo worker : workers.values()) {
            try {
                MessageProtocol.sendObject(worker.getOut(),
                        new DataTransferObject(DataTransferObject.Command.SHUTDOWN, "all", worker.getId()));
            } catch (IOException ignored) {}
        }
    }

    public void shutdown() {
        running = false;
        broadcastShutdown();
        try {
            synchronized (resultLock) {
                resultLock.wait(3000);
            }
        } catch (InterruptedException ignored) {}

        for (WorkerInfo w : workers.values()) w.close();
        workers.clear();

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException ignored) {

        }

        System.out.println("Master shutdown complete");
    }
}