package ru.nsu.pisarev;

import java.util.ArrayList;
import java.util.List;


public class Warehouse {
    private final int T;
    private int currentAmount;
    private final List<Order> orderList;

    public Warehouse(int t) {
        T = t;
        currentAmount = 0;
        this.orderList = new ArrayList<>();
    }

    public synchronized int getAmountOfPizzas(){
        return currentAmount;
    }
    public synchronized void tryPut(Order order){
        while (currentAmount >= T){
            try{
                wait();
            }catch (InterruptedException e){
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
                return;
            }
        }
        currentAmount+=1;
        orderList.add(order);
        notifyAll();
    }
    public synchronized Order takePizza(){
        while (currentAmount==0) {
            try{
                wait();
            } catch(InterruptedException e){
                System.err.println(e.getMessage());
                Thread.currentThread().interrupt();
                return null;
            }
        }
        currentAmount -= 1;
        notifyAll();
        return orderList.remove(currentAmount);//orderToDeliver
    }
}
