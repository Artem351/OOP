package ru.nsu.pisarev.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.nsu.pisarev.controller.SnakeController;
import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.Point;

import java.util.List;
import java.util.Set;

public class SnakeView {

    private static final Color COLOR_BACKGROUND = Color.rgb(18, 18, 22);
    private static final Color COLOR_GRID = Color.rgb(35, 35, 45);
    private static final Color COLOR_OBSTACLE = Color.rgb(210, 45, 45);
    private static final Color COLOR_FOOD = Color.rgb(255, 160, 40);
    private static final Color COLOR_SNAKE_BODY = Color.rgb(80, 220, 100);
    private static final Color COLOR_SNAKE_HEAD = Color.WHITE;
    private static final Color COLOR_OVERLAY_BACKGROUND = Color.rgb(0, 0, 0, 0.6);
    private static final Color COLOR_OVERLAY_TEXT = Color.WHITE;

    public void render(Canvas canvas,
                       List<Point> snake, Set<Point> obstacles,
                       List<Point> food, GameState state) {
        double w = canvas.getWidth(), h = canvas.getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Layout layout = calculateLayout(w, h, SnakeController.COLS, SnakeController.ROWS);

        drawBackground(gc, w, h);
        drawGrid(gc, layout);
        drawObstacles(gc, obstacles, layout);
        drawFood(gc, food, layout);
        drawSnake(gc, snake, layout);
        drawStateOverlay(gc, state, layout);
    }

    private record Layout(double cellSize, double offsetX, double offsetY, int cols, int rows) {
    }

    private Layout calculateLayout(double canvasW, double canvasH, int cols, int rows) {
        double cellSize = Math.min(canvasW / cols, canvasH / rows);
        double offsetX = (canvasW - cols * cellSize) / 2;
        double offsetY = (canvasH - rows * cellSize) / 2;
        return new Layout(cellSize, offsetX, offsetY, cols, rows);
    }

    private void drawBackground(GraphicsContext gc, double width, double height) {
        gc.setFill(COLOR_BACKGROUND);
        gc.fillRect(0, 0, width, height);
    }

    private void drawGrid(GraphicsContext gc, Layout layout) {
        gc.setStroke(COLOR_GRID);
        gc.setLineWidth(1);

        for (int x = 0; x <= layout.cols(); x++) {
            gc.strokeLine(
                    layout.offsetX() + x * layout.cellSize(), layout.offsetY(),
                    layout.offsetX() + x * layout.cellSize(), layout.offsetY() + layout.rows() * layout.cellSize()
            );
        }
        for (int y = 0; y <= layout.rows(); y++) {
            gc.strokeLine(
                    layout.offsetX(), layout.offsetY() + y * layout.cellSize(),
                    layout.offsetX() + layout.cols() * layout.cellSize(), layout.offsetY() + y * layout.cellSize()
            );
        }
    }

    private void drawObstacles(GraphicsContext gc, Set<Point> obstacles, Layout layout) {
        gc.setFill(COLOR_OBSTACLE);
        for (Point p : obstacles) {
            drawCell(gc, p, layout);
        }
    }

    private void drawFood(GraphicsContext gc, List<Point> food, Layout layout) {
        gc.setFill(COLOR_FOOD);
        for (Point p : food) {
            drawFoodCell(gc, p, layout);
        }
    }

    private void drawSnake(GraphicsContext gc, List<Point> snake, Layout layout) {
        if (snake.isEmpty()) {
            return;
        }

        gc.setFill(COLOR_SNAKE_BODY);
        for (int i = 1; i < snake.size(); i++) {
            drawCell(gc, snake.get(i), layout);
        }

        gc.setFill(COLOR_SNAKE_HEAD);
        drawCell(gc, snake.get(0), layout);
    }

    private void drawStateOverlay(GraphicsContext gc, GameState state, Layout layout) {
        if (state == GameState.RUNNING) {
            return;
        }

        gc.setFill(COLOR_OVERLAY_BACKGROUND);
        gc.fillRect(
                layout.offsetX(), layout.offsetY(),
                layout.cols() * layout.cellSize(), layout.rows() * layout.cellSize()
        );

        gc.setFill(COLOR_OVERLAY_TEXT);
        gc.setFont(Font.font("System Bold", 22));
        String message = getMessageForState(state);

        double textWidth = gc.getFont().getSize() * message.length() * 0.5;
        double textX = layout.offsetX() + (layout.cols() * layout.cellSize() - textWidth) / 2;
        double textY = layout.offsetY() + layout.rows() * layout.cellSize() / 2 + 8;

        gc.fillText(message, textX, textY);
    }

    private String getMessageForState(GameState state) {
        return switch (state) {
            case READY -> "Press «Start»";
            case WON -> "You won!";
            case LOST -> "You lose";
            case PAUSED -> "⏸ Pause";
            default -> "";
        };
    }

    private void drawCell(GraphicsContext gc, Point p, Layout layout) {
        gc.fillRect(
                layout.offsetX() + p.x() * layout.cellSize() + 1,
                layout.offsetY() + p.y() * layout.cellSize() + 1,
                layout.cellSize() - 2,
                layout.cellSize() - 2
        );
    }

    private void drawFoodCell(GraphicsContext gc, Point p, Layout layout) {
        gc.fillOval(
                layout.offsetX() + p.x() * layout.cellSize() + 2,
                layout.offsetY() + p.y() * layout.cellSize() + 2,
                layout.cellSize() - 4,
                layout.cellSize() - 4
        );
    }

    private void drawRect(GraphicsContext gc, Point p, double cellSize, double ox, double oy) {
        gc.fillRect(ox + p.x() * cellSize + 1, oy + p.y() * cellSize + 1, cellSize - 2, cellSize - 2);
    }

    private void drawOval(GraphicsContext gc, Point p, double cellSize, double ox, double oy) {
        gc.fillOval(ox + p.x() * cellSize + 2, oy + p.y() * cellSize + 2, cellSize - 4, cellSize - 4);
    }
}