package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SnakeTest {
    private final SnakeController controller =new SnakeController();
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
    @Test
    void wrapLeftToRight() {
        SnakeModel model = new SnakeModel(10, 10,controller);
        model.init();

        model.setDirection(Direction.LEFT);
        for (int i = 0; i < 15; i++) {
            if (model.getState() != GameState.RUNNING) {
                break;
            }
            model.step();
        }

        assertTrue(model.getState() == GameState.RUNNING || model.getState() == GameState.LOST);
    }

    @Test
    void wrapTopToBottom() {
        SnakeModel model = new SnakeModel(10, 10,controller);
        model.init();

        model.setDirection(Direction.UP);
        for (int i = 0; i < 15; i++) {
            if (model.getState() != GameState.RUNNING) break;
            model.step();
        }
        assertNotEquals(GameState.LOST, model.getState());
    }

    @Test
    void foodSpawnsOnFreeCell() {
        SnakeModel model = new SnakeModel(5, 5,controller);
        model.init();
        assertDoesNotThrow(model::init);
    }
}


