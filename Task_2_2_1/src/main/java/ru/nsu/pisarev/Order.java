package ru.nsu.pisarev;

public class Order {
    private  static int idCounter = 0;
    private final int id;
    private OrderStatus status;

    private static synchronized int getNextId() {
        return idCounter++;
    }
    public Order() {
        this.id = getNextId();
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
        return id+" "+status;
    }
}
