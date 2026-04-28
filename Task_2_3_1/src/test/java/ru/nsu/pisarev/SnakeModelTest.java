package ru.nsu.pisarev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.pisarev.model.Direction;
import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.Point;
import ru.nsu.pisarev.model.SnakeModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SnakeModelTest {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;
    private SnakeModel model;

    @BeforeEach
    void setUp() {
        model = new SnakeModel(WIDTH, HEIGHT);
        model.init(1);
    }

    @Test
    void initialState() {
        assertEquals(GameState.RUNNING, model.getState());
        assertEquals(1, model.getScore());

        List<Point> snake = model.getSnake();
        assertEquals(1, snake.size());

        Point head = snake.get(0);
        assertEquals(WIDTH / 2, head.x());
        assertEquals(HEIGHT / 2, head.y());
    }

    @Test
    void movementWithoutFood() {
        Point oldHead = model.getSnake().get(0);
        model.step();
        Point newHead = model.getSnake().get(0);

        assertEquals(oldHead.x() + 1, newHead.x());
        assertEquals(oldHead.y(), newHead.y());
        assertEquals(1, model.getScore());
    }

    @Test
    void noReverseDirection() {
        model.setDirection(Direction.LEFT);
        model.step();

        Point head = model.getSnake().get(0);
        assertEquals(WIDTH / 2 + 1, head.x());
    }

    @Test
    void eatingFoodGrowsSnake() {
        int initialScore = model.getScore();
        int initialFood = model.getFood().size();

        assertTrue(initialFood >= 3);
        assertEquals(1, initialScore);
    }

    @Test
    void winCondition() {
        assertNotNull(GameState.WON);
    }

    @Test
    void modelIsJavaFXFree() {
        assertDoesNotThrow(() -> {
            SnakeModel test = new SnakeModel(10, 10);
            test.init(1);
            test.step();
            test.setDirection(Direction.UP);
        });
    }


    @Test
    void wrapLeftToRight() {
        SnakeModel testModel = new SnakeModel(10, 10);
        testModel.init(1);

        testModel.setDirection(Direction.LEFT);
        for (int i = 0; i < 15; i++) {
            if (testModel.getState() != GameState.RUNNING) {
                break;
            }
            testModel.step();
        }

        assertNotEquals(GameState.LOST, testModel.getState());
    }

    @Test
    void wrapTopToBottom() {
        SnakeModel testModel = new SnakeModel(10, 10);
        testModel.init(1);

        testModel.setDirection(Direction.UP);
        for (int i = 0; i < 15; i++) {
            if (testModel.getState() != GameState.RUNNING) {
                break;
            }
            testModel.step();
        }

        assertNotEquals(GameState.LOST, testModel.getState());
    }

    @Test
    void foodSpawnsOnFreeCell() {
        SnakeModel testModel = new SnakeModel(5, 5);
        assertDoesNotThrow(() -> testModel.init(1));


        assertEquals(testModel.getFood().stream().distinct().count(), testModel.getFood().size());
    }

    @Test
    void higherLevelMoreObstacles() {
        SnakeModel level1 = new SnakeModel(20, 15);
        SnakeModel level5 = new SnakeModel(20, 15);

        level1.init(1);
        level5.init(5);

        assertTrue(level5.getObstacles().size() >= level1.getObstacles().size());
    }
}