package ru.nsu.pisarev;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;


public class SnakeView {
    public void render(Canvas canvas, SnakeModel model) {
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        int cols = model.getWidth();
        int rows = model.getHeight();


        double cellSize = Math.min(w / cols, h / rows);
        double offsetX = (w - cols * cellSize) / 2;
        double offsetY = (h - rows * cellSize) / 2;

        //background
        gc.setFill(Color.rgb(18, 18, 22));
        gc.fillRect(0, 0, w, h);

        //cells
        gc.setStroke(Color.rgb(35, 35, 45));
        gc.setLineWidth(1);
        for (int x = 0; x <= cols; x++) {
            gc.strokeLine(offsetX + x * cellSize, offsetY, offsetX + x * cellSize, offsetY + rows * cellSize);
        }
        for (int y = 0; y <= rows; y++) {
            gc.strokeLine(offsetX, offsetY + y * cellSize, offsetX + cols * cellSize, offsetY + y * cellSize);
        }

        gc.setFill(Color.rgb(90, 90, 100));
        for (Point p : model.getObstacles()){
            drawRect(gc, p, cellSize, offsetX, offsetY);
        }

        // food
        gc.setFill(Color.rgb(255, 160, 40));
        for (Point p : model.getFood()) {
            drawOval(gc, p, cellSize, offsetX, offsetY);
        }

        // snake
        List<Point> snake = model.getSnake();
        if (!snake.isEmpty()) {
            gc.setFill(Color.rgb(80, 220, 100));
            for (int i = 1; i < snake.size(); i++) {
                drawRect(gc, snake.get(i), cellSize, offsetX, offsetY);
            }
            gc.setFill(Color.WHITE);
            drawRect(gc, snake.get(0), cellSize, offsetX, offsetY);
        }
        // obstacles
        gc.setFill(Color.rgb(210, 45, 45));
        for (Point p : model.getObstacles()) {
            drawRect(gc, p, cellSize, offsetX, offsetY);
        }

        if (model.getState() != GameState.RUNNING) {
            gc.setFill(Color.rgb(0, 0, 0, 0.6));
            gc.fillRect(offsetX, offsetY, cols * cellSize, rows * cellSize);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("System Bold", 22));
            String msg = switch (model.getState()) {
                case READY -> "Press «Start»";
                case WON -> "You won!";
                case LOST -> "You lose";
                case PAUSED -> "⏸ Pause";
                default -> "";
            };
            double tw = gc.getFont().getSize() * msg.length() * 0.5;
            gc.fillText(msg, offsetX + (cols * cellSize - tw) / 2, offsetY + rows * cellSize / 2 + 8);
        }
    }

    private void drawRect(GraphicsContext gc, Point p, double cellSize, double ox, double oy) {
        gc.fillRect(ox + p.x() * cellSize + 1, oy + p.y() * cellSize + 1, cellSize - 2, cellSize - 2);
    }

    private void drawOval(GraphicsContext gc, Point p, double cellSize, double ox, double oy) {
        gc.fillOval(ox + p.x() * cellSize + 2, oy + p.y() * cellSize + 2, cellSize - 4, cellSize - 4);
    }
}