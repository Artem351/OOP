package ru.nsu.pisarev;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Pizzeria {
    private final int N;//production
    private final int M;//delivery
    private final int T;//warehouse
    private final Warehouse warehouse;
    private final Queue<Order> orderQueue=new LinkedList<>();
    private final List<Integer> speeds;
    private final List<Integer> capacities;
    volatile boolean running = true;

    public Pizzeria(int n, int m, int t,List<Integer> speeds,List<Integer> capacities) {
        N = n;
        M = m;
        T = t;
        this.warehouse = new Warehouse(T);
        this.speeds=speeds;
        this.capacities=capacities;
    }

    public synchronized void addOrder(Order order){
        orderQueue.add(order);
        notifyAll();
    }
    public synchronized Order getNextOrder(){
        while(orderQueue.isEmpty() && running){
            try{
                wait();
            }catch(InterruptedException e){
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return orderQueue.poll();
    }
    public boolean hasOrders() {
        return !orderQueue.isEmpty();
    }
    public synchronized void shutdown(){
        running = false;
        notifyAll();
        warehouse.notifyAll();

    }
    public void startBakers(){
        for(int i=0;i<N;i++){
            int speed = (i < speeds.size()) ? speeds.get(i) : 1;
            Baker baker = new Baker(speed,this,warehouse);
            Thread t = new Thread(baker);
            t.start();
        }
    }
    public void startCarriers(){
        for(int i=0;i<M;i++){
            int capacity = (i < capacities.size()) ? capacities.get(i) : 1;
            Carrier carrier = new Carrier(capacity,this, warehouse);
            Thread t = new Thread(carrier);
            t.start();
        }
    }
    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public int getT() {
        return T;
    }
}
