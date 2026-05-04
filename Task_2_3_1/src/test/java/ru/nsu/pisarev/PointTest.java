package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;
import ru.nsu.pisarev.model.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PointTest {

    @Test
    void pointEquality() {
        Point p1 = new Point(5, 10);
        Point p2 = new Point(5, 10);
        Point p3 = new Point(6, 10);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }

    @Test
    void pointHashCode() {
        Point p1 = new Point(3, 7);
        Point p2 = new Point(3, 7);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void pointImmutability() {
        Point p = new Point(1, 2);
        assertEquals(1, p.x());
        assertEquals(2, p.y());
        assertNotNull(p);
    }
}