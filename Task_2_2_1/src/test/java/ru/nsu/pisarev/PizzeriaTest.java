package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PizzeriaTest {
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
    public void testConfigParsing() throws Exception {
        String testJson = "{ \"bakers\": 2, \"carriers\": 1, \"warehouseSize\": 5, " +
                "\"bakerSpeeds\": [3, 2], \"carrierCapacities\": [4] }";

        Path path = Paths.get("test_config.json");
        java.nio.file.Files.write(
                path,
                testJson.getBytes()
        );

        Config cfg = Config.fromFile("test_config.json");

        assertEquals(2, cfg.amountOfBakers(), "Config: bakers = 2");
        assertEquals(1, cfg.amountOfCarriers(), "Config: carriers = 1");
        assertEquals(5, cfg.warehouseCapacity(), "Config: warehouseSize = 5");
        assertEquals(Arrays.asList(3, 2), cfg.bakerSpeeds(), "Config: bakerSpeeds = [3,2]");
        assertEquals(List.of(4), cfg.carrierCapacities(), "Config: carrierCapacities = [4]");

        java.nio.file.Files.deleteIfExists(path);
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


