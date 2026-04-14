package ru.nsu.pisarev;

public class OrderIdGenerator {
    private static int counter = 0;
    public static synchronized int getNextId() {
        return counter++;
    }
}
