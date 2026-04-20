package ru.nsu.pisarev;

import java.util.*;

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
    private final SnakeController controller;

    public SnakeModel(int width, int height, SnakeController controller) {
        this.width = width;
        this.height = height;
        this.controller = controller;
    }

    public void init() {
        snake.clear();
        bodySet.clear();
        obstacles.clear();
        food.clear();
        direction = Direction.RIGHT;
        state = GameState.RUNNING;

        Point start = new Point(width / 2, height / 2);
        snake.add(start);
        bodySet.add(start);
        //  100/24 ~ 4% obstacles
        int obstacleCount = (width * height) * (controller.getLevelCounter()-1)/50;
        placeRandomObstacles(obstacleCount);

        for (int i = 0; i < foodCount; i++) spawnFood();
    }

    private void placeRandomObstacles(int count) {
        int placed = 0;
        int attempts = 0;
        int maxAttempts = count * 20;

        while (placed < count && attempts < maxAttempts) {
            Point p = new Point(random.nextInt(width), random.nextInt(height));
            if (!bodySet.contains(p) && !obstacles.contains(p)) {
                if (Math.abs(p.x() - width / 2) <= 1 && Math.abs(p.y() - height / 2) <= 1) {
                    attempts++;
                    continue;
                }
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

        Point newHead = getPoint();
        if (obstacles.contains(newHead)) {
            state = GameState.LOST;
            return;
        }
        if (bodySet.contains(newHead)) {
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

    private Point getPoint() {
        Point head = snake.getFirst();
        int x = head.x();
        int y = head.y();
        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
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
        Point newHead = new Point(x, y);
        return newHead;
    }

    public void setDirection(Direction dir) {
        if (state != GameState.RUNNING) {
            return;
        }
        if (dir == Direction.UP && direction != Direction.DOWN) {
            direction = dir;
        }
        else {
            if (dir == Direction.DOWN && direction != Direction.UP) {
                direction = dir;
            }
            else {
                if (dir == Direction.LEFT && direction != Direction.RIGHT){
                    direction = dir;
                }
                else {
                    if (dir == Direction.RIGHT && direction != Direction.LEFT){
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
        } while (bodySet.contains(p) || obstacles.contains(p) || food.contains(p));
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

    public void addObstacles(Set<Point> obs) {
        obstacles.addAll(obs);
    }

    public int getFoodCount() {
        return foodCount;
    }
}
