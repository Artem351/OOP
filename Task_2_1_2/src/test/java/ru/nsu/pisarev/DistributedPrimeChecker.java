package ru.nsu.pisarev;

import java.util.Arrays;

/**
 * Distributed Prime Check:
 * 1. Master: java DistributedPrimeChecker master <port> <num1> <num2> ...
 * 2. Worker: java Worker <id> <master-host> <master-port>
 */
public class DistributedPrimeChecker {

    public static void main(String[] args) throws Exception {
        if (args.length < 2 && !args[0].equalsIgnoreCase("test")) {
            System.err.println("There are a few args.");
            printUsage();
            return;
        }

        String mode = args[0];
        if ("master".equalsIgnoreCase(mode)) {
            runMaster(args);
        } else if ("worker".equalsIgnoreCase(mode)) {
            runWorker(args);
        } else if ("test".equals(mode)) {
            runLocalTest();
        } else {
            System.err.println("Your mode is:" + mode);
            printUsage();
        }
    }

    private static void runMaster(String[] args) {
        if (args.length < 3) {
            System.out.println("Master usage: java DistributedPrimeChecker master <port> <num1> <num2>");
            return;
        }
        int port = Integer.parseInt(args[1]);
        int[] array = new int[args.length - 2];
        for (int i = 2; i < args.length; i++) {
            array[i - 2] = Integer.parseInt(args[i]);
        }

        System.out.println("Starting Master with array: " + Arrays.toString(array));

        Master master = new Master(array, port);
        boolean result = master.start();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESULT: " + (result ? "TRUE (found non-prime)" : "FALSE (all prime)"));
        System.out.println("=".repeat(50));

        System.exit(result ? 0 : 1);
    }

    private static void runWorker(String[] args) {
        if (args.length < 4) {
            System.out.println("Worker usage: java DistributedPrimeChecker worker <id> <host> <port>");
            return;
        }
        int id = Integer.parseInt(args[1]);
        String host = args[2];
        int port = Integer.parseInt(args[3]);

        new Worker(id, host, port).start();
    }

    private static void runLocalTest() throws Exception {
        System.out.println("Running local test with simulated workers...");

        int[] test1 = {6, 8, 7, 13, 5, 9, 4};
        System.out.println("\nTest 1: " + Arrays.toString(test1));
        boolean result1 = runTest(test1, 9999, 3);
        System.out.println("Expected: true, Got: " + result1 + " = " +
                (result1 ? "PASS" : "FAIL"));

        int[] test2 = {20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149,
                6998051, 6998053};
        System.out.println("\n Test 2: " + Arrays.toString(test2));
        boolean result2 = runTest(test2, 9998, 3);
        System.out.println("Expected: false, Got: " + result2 + " = " +
                (!result2 ? "PASS" : "FAIL"));
    }

    private static boolean runTest(int[] array, int port, int workerCount) throws Exception {
        Master master = new Master(array, port);

        Thread masterThread = new Thread(master::start, "Test-Master");
        masterThread.start();

        Thread.sleep(500);

        Thread[] workers = new Thread[workerCount];
        for (int i = 0; i < workerCount; i++) {
            final int wid = i + 1;
            workers[i] = new Thread(() -> {
                try {
                    Thread.sleep(100L * wid);
                    new Worker(wid, "localhost", port).start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }, "Test-Worker-" + wid);
            workers[i].start();
        }

        masterThread.join(60000);

        Thread.sleep(500);
        for (Thread w : workers) {
            if (w != null && w.isAlive()) {
                w.interrupt();
            }
        }

        return !masterThread.isAlive() && (array.length > 0 && !allPrime(array));
    }

    private static boolean allPrime(int[] array) {
        Utils utils = new Utils();
        for (int n : array) {
            if (!utils.isPrime(n)) {
                return false;
            }
        }
        return true;
    }

    private static void printUsage() {
        System.out.println("""
            Distributed Prime Checker
            
            Modes:
              master  <port> <num1> <num2> ...  — run master
              worker  <id> <host> <port>         — run workers
              test                                 — run local test
            
            Examples:
              java DistributedPrimeChecker master 9999 6 8 7 13 5 9 4
              java DistributedPrimeChecker worker 1 localhost 9999
              java DistributedPrimeChecker test
            """);
    }
}
