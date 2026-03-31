package ru.nsu.pisarev;

import java.util.ArrayList;
import java.util.List;

public class Carrier implements Runnable {
    private final int capacity;
    /**
     * list of orders to be delivered by the current carrier
     */
    private final List<Order> orderList;
    private final Pizzeria pizzeria;
    private final Warehouse warehouse;

    public Carrier(int capacity, Pizzeria pizzeria, Warehouse warehouse) {
        this.capacity = capacity;
        this.warehouse = warehouse;
        this.pizzeria = pizzeria;
        orderList = new ArrayList<>(capacity);
    }

    public void deliverLoop() throws InterruptedException {
        while (pizzeria.running || warehouse.getAmountOfPizzas() > 0) {
            deliver();
        }
    }

    public void deliver() {

        while (orderList.size() < capacity) {
            Order order = warehouse.takePizza();
            if (order == null) {
                break;
            }
            orderList.add(order);
            order.setStatus(OrderStatus.DELIVERING);
            System.out.println(order);
        }
        while (!orderList.isEmpty()) {
            Order order = orderList.remove(orderList.size() - 1);
            order.setStatus(OrderStatus.DELIVERED);
            System.out.println(order);
        }
    }

    @Override
    public void run() {
        try {
            deliverLoop();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
