package ru.nsu.pisarev;

public class Baker implements Runnable{
    private final int speed;
    private final Pizzeria pizzeria;
    private final Warehouse warehouse;

    public Baker(int speed, Pizzeria pizzeria, Warehouse warehouse) {
        this.speed = speed;
        this.pizzeria = pizzeria;
        this.warehouse = warehouse;
    }

    public int getSpeed() {
        return speed;
    }

    public void bakeLoop() throws InterruptedException{
        while(pizzeria.running || pizzeria.hasOrders()) {
            Order order = pizzeria.getNextOrder();
            if (order == null){
                break;
            }
            bake(order);
        }
    }
    public void bake(Order order) throws InterruptedException{
        order.setStatus(OrderStatus.BAKING);
        System.out.println(order);
        Thread.sleep(1000 / speed);
        order.setStatus(OrderStatus.READY);
        warehouse.tryPut(order);
        System.out.println(order);
    }

    @Override
    public void run() {
        try {
            bakeLoop();
        }catch(InterruptedException e){
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
