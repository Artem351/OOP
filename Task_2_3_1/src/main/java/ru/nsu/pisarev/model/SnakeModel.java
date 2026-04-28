package ru.nsu.pisarev.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SnakeModel {
    private final int width, height;
    private int foodCount = 3;
    private int winLength = 10;
    private Direction direction = Direction.RIGHT;
    private LinkedList<Point> snake = new LinkedList<>();
    private Set<Point> bodySet = new HashSet<>();
    private Set<Point> obstacles = new HashSet<>();
    private List<Point> food = new ArrayList<>();
    private GameState state = GameState.READY;
    private final Random random = new Random();


    public SnakeModel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void init(int level) {
        snake.clear();
        bodySet.clear();
        obstacles.clear();
        food.clear();
        direction = Direction.RIGHT;
        state = GameState.RUNNING;

        Point start = new Point(width / 2, height / 2);
        snake.add(start);
        bodySet.add(start);


        int obstacleCount = (width * height) * Math.max(0, level - 1) / 50;
        placeRandomObstacles(obstacleCount);

        for (int i = 0; i < foodCount; i++) {
            spawnFood();
        }
    }

    private void placeRandomObstacles(int count) {
        int placed = 0, attempts = 0, maxAttempts = count * 20;
        while (placed < count && attempts < maxAttempts) {
            Point p = new Point(random.nextInt(width), random.nextInt(height));
            if (!bodySet.contains(p) && !obstacles.contains(p) &&
                    !(Math.abs(p.x() - width / 2) <= 1 && Math.abs(p.y() - height / 2) <= 1)) {
                obstacles.add(p);
                placed++;
            }
            attempts++;
        }
    }

    public void step() {
        if (state != GameState.RUNNING) {
            return;
        }
        Point newHead = calculateNextHead();

        if (obstacles.contains(newHead) || bodySet.contains(newHead)) {
            state = GameState.LOST;
            return;
        }

        boolean ateFood = food.removeIf(f -> f.equals(newHead));
        snake.addFirst(newHead);
        bodySet.add(newHead);

        if (ateFood) {
            spawnFood();
            if (snake.size() >= winLength) {
                state = GameState.WON;
            }
        } else {
            Point tail = snake.removeLast();
            bodySet.remove(tail);
        }
    }

    private Point calculateNextHead() {
        Point head = snake.getFirst();
        int x = head.x(), y = head.y();
        switch (direction) {
            case UP -> y--;
            case DOWN -> y++;
            case LEFT -> x--;
            case RIGHT -> x++;
        }
        if (x < 0) {
            x = width - 1;
        } else {
            if (x >= width) {
                x = 0;
            }
        }
        if (y < 0) {
            y = height - 1;
        } else {
            if (y >= height) {
                y = 0;
            }
        }

        return new Point(x, y);
    }

    public void setDirection(Direction dir) {
        if (state != GameState.RUNNING) {
            return;
        }
        if (dir == Direction.UP && direction != Direction.DOWN) {
            direction = dir;
        } else {
            if (dir == Direction.DOWN && direction != Direction.UP) {
                direction = dir;
            } else {
                if (dir == Direction.LEFT && direction != Direction.RIGHT) {
                    direction = dir;
                } else {
                    if (dir == Direction.RIGHT && direction != Direction.LEFT) {
                        direction = dir;
                    }
                }
            }
        }
    }

    private void spawnFood() {
        Point p;
        do {
            p = new Point(random.nextInt(width), random.nextInt(height));
        }
        while (bodySet.contains(p) || obstacles.contains(p) || food.contains(p));
        food.add(p);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Point> getSnake() {
        return Collections.unmodifiableList(snake);
    }

    public Set<Point> getObstacles() {
        return Collections.unmodifiableSet(obstacles);
    }

    public List<Point> getFood() {
        return Collections.unmodifiableList(food);
    }

    public GameState getState() {
        return state;
    }

    public int getScore() {
        return snake.size();
    }

    public void setState(GameState state) {
        this.state = state;
    }
}