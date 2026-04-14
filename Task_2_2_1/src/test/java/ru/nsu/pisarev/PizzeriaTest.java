package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PizzeriaTest {
    @TempDir
    Path tempDir;
    @Test
    public void testOrderCreation() {
        Order order = new Order();
        assertSame(OrderStatus.NEW, order.getStatus());
        assertTrue(order.getId() > 0);
    }

    @Test
    public void testOrderStatusChange() {
        Order order = new Order();
        order.setStatus(OrderStatus.BAKING);
        assertSame(OrderStatus.BAKING, order.getStatus());

        order.setStatus(OrderStatus.READY);
        assertSame(OrderStatus.READY, order.getStatus());
    }

    @Test
    public void testOrderIdUniqueness() {
        Order o1 = new Order();
        Order o2 = new Order();
        Order o3 = new Order();

        assertTrue(o1.getId() < o2.getId());
        assertTrue(o2.getId() < o3.getId());
    }

    @Test
    public void testWarehousePutWhenSpace() {
        Warehouse w = new Warehouse(3);
        Order order = new Order();

        w.putWithWait(order);
        assertEquals(1, w.getAmountOfPizzas());
    }

    @Test
    public void testWarehousePutWhenFull() {
        Warehouse w = new Warehouse(2);
        w.putWithWait(new Order());
        w.putWithWait(new Order());

        assertEquals(2, w.getAmountOfPizzas());
    }

    @Test
    public void testWarehouseTake() {
        Warehouse w = new Warehouse(3);
        Order o1 = new Order();
        Order o2 = new Order();

        w.putWithWait(o1);
        w.putWithWait(o2);

        Order taken = w.takePizza();
        assertNotNull(taken);
        assertEquals(1, w.getAmountOfPizzas());
    }

    @Test
    public void testPizzeriaAddOrder() {
        Pizzeria p = new Pizzeria(1, 1, 5, List.of(1), List.of(1));
        Order order = new Order();

        p.addOrder(order);
        assertTrue(p.hasOrders());
    }

    @Test
    public void testPizzeriaGetNextOrder() {
        Pizzeria p = new Pizzeria(1, 1, 5, List.of(1), List.of(1));
        Order order = new Order();
        p.addOrder(order);

        Order retrieved = p.getNextOrder();
        assertEquals(order.getId(), retrieved.getId(), "getNextOrder() вернул тот же заказ");
        assertFalse(p.hasOrders());
    }

    @Test
    void testConfigParsing_invalidJson_throwsException() {

        String invalidJson = "{ \"bakers\": \"not_a_number\", \"carriers\": 1 }";

        assertThrows(IOException.class, () -> {
            Path configFile = tempDir.resolve("invalid.json");
            Files.writeString(configFile, invalidJson);
            Config.fromFile(configFile.toString());
        });
        try {
            Path configFile = tempDir.resolve("invalid.json");
            assertNotNull(Config.fromFile(configFile.toString()));
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }



    @Test
    public void testOrderToStringFormat() {
        Order order = new Order();
        order.setStatus(OrderStatus.READY);

        String result = order.toString();
        assertTrue(result.contains(String.valueOf(order.getId())));
        assertTrue(result.contains("READY"));
    }
}


