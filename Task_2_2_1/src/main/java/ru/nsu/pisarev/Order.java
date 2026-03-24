package ru.nsu.pisarev;

public class Order {
    private final int id;
    private OrderStatus status;


    public Order() {
        this.id = OrderIdGenerator.getNextId();
        this.status = OrderStatus.NEW;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return id + " " + status;
    }
}
