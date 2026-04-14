package ru.nsu.pisarev;

public record Baker(int speed, Pizzeria pizzeria, Warehouse warehouse) implements Runnable {

    public void bakeLoop() throws InterruptedException {
        while (pizzeria.running || pizzeria.hasOrders()) {
            Order order = pizzeria.getNextOrder();
            if (order == null) {
                break;
            }
            bake(order);
        }
    }

    public void bake(Order order) throws InterruptedException {
        order.setStatus(OrderStatus.BAKING);
        System.out.println(order);
        Thread.sleep(1000 / speed);
        order.setStatus(OrderStatus.READY);
        warehouse.putWithWait(order);
        System.out.println(order);
    }

    @Override
    public void run() {
        try {
            bakeLoop();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
