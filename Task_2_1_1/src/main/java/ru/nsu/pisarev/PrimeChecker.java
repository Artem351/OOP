package ru.nsu.pisarev;


import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeChecker {

    public static boolean isPrime(int number) {
        if (number < 2)
            return false;
        for (int i = 2; i * i <= number; i++)
            if (number % i == 0)
                return false;
        return true;
    }

    public static boolean hasNonPrimeSequential(int[] array) {
        for (int num : array) {
            if (!isPrime(num)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNonPrimeThreaded(int[] array, int threadAmount) {
        AtomicBoolean foundNonPrime = new AtomicBoolean(false);
        int arrayLength = array.length;
        if (threadAmount < 1) {
            threadAmount = 1;
        }
        if (threadAmount > arrayLength) {
            threadAmount = arrayLength;
        }
        Thread[] threads = new Thread[threadAmount];
        int threadWindowLength = arrayLength / threadAmount;
        for (int i = 0; i < threadAmount; i++) {
            int startID = threadWindowLength * i;
            int endID;
            if (i != threadAmount - 1) {
                endID = threadWindowLength * (i + 1);
            } else {
                endID = arrayLength;
            }
            PrimeCheckerTask task = new PrimeCheckerTask(array, startID, endID, foundNonPrime);
            Thread thread = new Thread(task);
            threads[i] = thread;
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException");
                return foundNonPrime.get();
            }
        }

        return foundNonPrime.get();
    }

    public static boolean hasNonPrimeParallelStream(int[] array) {
        return Arrays.stream(array).parallel()
                .anyMatch(x ->!isPrime(x));
    }

}
