package ru.nsu.pisarev;
import java.io.IOException;
import java.net.Socket;

public class Worker {
    private final int id;
    private final String masterHost;
    private final int masterPort;
    private final Utils utils = new Utils();
    private volatile boolean running = true;

    private static final int HEARTBEAT_INTERVAL_MS = 3000;
    private static final int RECONNECT_DELAY_MS = 2000;

    public Worker(int id, String masterHost, int masterPort) {
        this.id = id;
        this.masterHost = masterHost;
        this.masterPort = masterPort;
    }

    public void start() {
        System.out.println("Worker " + id + " starting, connecting to " + masterHost + ":" + masterPort);

        while (running) {
            Socket socket = null;
            java.io.ObjectOutputStream out = null;
            java.io.ObjectInputStream in = null;

            try {
                socket = new Socket(masterHost, masterPort);
                socket.setSoTimeout(30000);

                out = new java.io.ObjectOutputStream(socket.getOutputStream());
                in = new java.io.ObjectInputStream(socket.getInputStream());

                System.out.println("Worker " + id + " connected to Master");

                DataTransferObject handshake = MessageProtocol.receiveObject(in);
                if (handshake.getCommand() != DataTransferObject.Command.PONG) {
                    System.err.println("Unexpected handshake from Master");
                    continue;
                }

                Thread heartbeatThread = startHeartbeat(out);

                while (running && !socket.isClosed()) {
                    DataTransferObject msg = MessageProtocol.receiveObject(in);
                    processMessage(msg, out);
                }
                heartbeatThread.interrupt();
            } catch (java.net.ConnectException e) {
                System.err.println("Worker " + id + " cannot connect to Master: " + e.getMessage());
            } catch (java.io.EOFException e) {
                System.out.println("Worker " + id + " connection closed by Master");
                break;
            } catch (Exception e) {
                if (running) {
                    System.err.println("Worker " + id + " connection error: " + e.getMessage());
                }
                break;
            } finally {
                closeResources(socket, out, in);
            }

            if (running) {
                System.out.println("Worker " + id + " reconnecting in " + RECONNECT_DELAY_MS + "ms...");
                try {
                    Thread.sleep(RECONNECT_DELAY_MS);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        System.out.println("Worker " + id + " stopped");
    }

    private Thread startHeartbeat(java.io.ObjectOutputStream out) {
        Thread t = new Thread(() -> {
            while (running) {
                try {
                    MessageProtocol.sendObject(out,
                            new DataTransferObject(DataTransferObject.Command.HEARTBEAT, "hb", id));
                    Thread.sleep(HEARTBEAT_INTERVAL_MS);
                } catch (Exception e) {
                    break;
                }
            }
        }, "Worker-" + id + "-Heartbeat");
        t.setDaemon(true);
        t.start();
        return t;
    }

    private void processMessage(DataTransferObject msg, java.io.ObjectOutputStream out) {
        try {
            switch (msg.getCommand()) {
                case ASSIGN_TASK:
                    processTask(msg, out);
                    break;

                case SHUTDOWN:
                    System.out.println("Worker " + id + " received SHUTDOWN");
                    running = false;
                    break;

                case PING:
                    MessageProtocol.sendObject(out,
                            new DataTransferObject(DataTransferObject.Command.PONG, msg.getTaskId(), id));
                    break;
            }
        } catch (Exception e) {
            System.err.println("Worker " + id + " error processing message: " + e.getMessage());
            sendError(msg.getTaskId(), out, e.getMessage());
        }
    }

    private void processTask(DataTransferObject msg, java.io.ObjectOutputStream out) throws IOException {
        int number = msg.getNumber();
        String taskId = msg.getTaskId();

        System.out.println("Worker " + id + " checking: " + number + " (task: " + taskId + ")");

        boolean isPrime = utils.isPrime(number);
        boolean isNonPrime = !isPrime;

        DataTransferObject result = new DataTransferObject(
                DataTransferObject.Command.RESULT, taskId, id, isNonPrime, number);
        result.setSequenceNumber(msg.getSequenceNumber());

        MessageProtocol.sendObject(out, result);

        if (isNonPrime) {
            System.out.println("Worker " + id + " found non-prime: " + number);
        } else {
            System.out.println("Worker " + id + " confirmed prime: " + number);
        }
    }

    private void sendError(String taskId, java.io.ObjectOutputStream out, String error) {
        try {
            DataTransferObject errorDto = new DataTransferObject(
                    DataTransferObject.Command.ERROR, taskId, id);
            MessageProtocol.sendObject(out, errorDto);
        } catch (IOException e) {
            System.err.println("Failed to send error: " + e.getMessage());
        }
    }

    private void closeResources(Socket socket, java.io.ObjectOutputStream out,
                                java.io.ObjectInputStream in) {
        if (out != null) {
            try
            {
                out.close();
            } catch (IOException ignored) {
                //Nothing to do
            }
        }
        if (in != null){
            try
            {
                in.close();
            } catch (IOException ignored) {

            }
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException ignored) {

            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java Worker <id> <master-host> <master-port>");
            return;
        }

        int id = Integer.parseInt(args[0]);
        String host = args[1];
        int port = Integer.parseInt(args[2]);

        new Worker(id, host, port).start();
    }
}