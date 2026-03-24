package ru.nsu.pisarev;

import java.util.ArrayList;
import java.util.List;


public class Warehouse {
    private final int capacity;
    private final List<Order> orderList = new ArrayList<>();
    private int currentAmount;

    public Warehouse(int capacity) {
        this.capacity = capacity;
        currentAmount = 0;
    }

    public synchronized int getAmountOfPizzas() {
        return currentAmount;
    }

    public synchronized void putWithWait(Order order) {
        while (warehouseIsFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
                return;
            }
        }
        currentAmount += 1;
        orderList.add(order);
        notifyAllCouriers();
    }

    public synchronized Order takePizza() {
        while (warehouseIsEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
                return null;
            }
        }
        currentAmount -= 1;
        notifyAllCouriers();
        return orderList.remove(currentAmount);//orderToDeliver
    }

    private boolean warehouseIsEmpty() {
        return currentAmount == 0;
    }

    private void notifyAllCouriers() {
        notifyAll();
    }
    private boolean warehouseIsFull() {
        return currentAmount == capacity;
    }

}
