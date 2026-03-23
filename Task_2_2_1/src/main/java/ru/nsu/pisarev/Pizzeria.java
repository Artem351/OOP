package ru.nsu.pisarev;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Pizzeria {
    private final int amountOfBakers;//production
    private final int amountOfCarriers;//delivery
    private final Warehouse warehouse;
    private final Queue<Order> orderQueue = new LinkedList<>();
    private final List<Integer> speeds;
    private final List<Integer> capacities;
    volatile boolean running = true;

    public Pizzeria(int amountOfBakers, int amountOfCarriers, int warehouseCapacity, List<Integer> speeds, List<Integer> capacities) {
        this.amountOfBakers = amountOfBakers;
        this.amountOfCarriers = amountOfCarriers;
        //warehouse
        this.warehouse = new Warehouse(warehouseCapacity);
        this.speeds = speeds;
        this.capacities = capacities;
        startBakers();
        startCarriers();
    }

    public synchronized void addOrder(Order order) {
        orderQueue.add(order);
        notifyAll();
    }

    public synchronized Order getNextOrder() {
        while (orderQueue.isEmpty() && running) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return orderQueue.poll();
    }

    public boolean hasOrders() {
        return !orderQueue.isEmpty();
    }

    public synchronized void shutdown() {
        running = false;
        notifyAll();
        warehouse.notifyAll();

    }

    public void startBakers() {
        for (int i = 0; i < amountOfBakers; i++) {
            int speed = (i < speeds.size()) ? speeds.get(i) : 1;
            Baker baker = new Baker(speed, this, warehouse);
            Thread t = new Thread(baker);
            t.start();
        }
    }

    public void startCarriers() {
        for (int i = 0; i < amountOfCarriers; i++) {
            int capacity = (i < capacities.size()) ? capacities.get(i) : 1;
            Carrier carrier = new Carrier(capacity, this, warehouse);
            Thread t = new Thread(carrier);
            t.start();
        }
    }
}
