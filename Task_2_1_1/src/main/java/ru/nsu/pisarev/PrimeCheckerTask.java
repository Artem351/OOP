package ru.nsu.pisarev;

import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeCheckerTask implements Runnable {

    private final int[] array;
    private final int startID;
    private final int endID;
    private final AtomicBoolean foundNonPrime;

    public PrimeCheckerTask(int[] array, int startID, int endID, AtomicBoolean foundNonPrime) {
        this.array = array;
        this.startID = startID;
        this.endID = endID;
        this.foundNonPrime = foundNonPrime;
    }

    public void run() {
        for (int i = startID; i < endID; i++) {
            if (i%100 == 0 && foundNonPrime.get()) {
                return;
            }
            if (!PrimeChecker.isPrime(array[i])) {
                foundNonPrime.set(true);
                return;
            }
        }
    }
}
