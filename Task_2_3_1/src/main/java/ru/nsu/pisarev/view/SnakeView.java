package ru.nsu.pisarev.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.Point;

import java.util.List;
import java.util.Set;

public class SnakeView {

    public void render(Canvas canvas, int cols, int rows,
                       List<Point> snake, Set<Point> obstacles,
                       List<Point> food, GameState state) {
        double w = canvas.getWidth(), h = canvas.getHeight();
        if (w <= 0 || h <= 0){
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        double cellSize = Math.min(w / cols, h / rows);
        double offsetX = (w - cols * cellSize) / 2;
        double offsetY = (h - rows * cellSize) / 2;

        gc.setFill(Color.rgb(18, 18, 22));
        gc.fillRect(0, 0, w, h);

        gc.setStroke(Color.rgb(35, 35, 45));
        gc.setLineWidth(1);
        for (int x = 0; x <= cols; x++) {
            gc.strokeLine(offsetX + x * cellSize, offsetY, offsetX + x * cellSize, offsetY + rows * cellSize);
        }
        for (int y = 0; y <= rows; y++) {
            gc.strokeLine(offsetX, offsetY + y * cellSize, offsetX + cols * cellSize, offsetY + y * cellSize);
        }

        gc.setFill(Color.rgb(210, 45, 45));
        for (Point p : obstacles) {
            drawRect(gc, p, cellSize, offsetX, offsetY);
        }

        gc.setFill(Color.rgb(255, 160, 40));
        for (Point p : food) {
            drawOval(gc, p, cellSize, offsetX, offsetY);
        }

        if (!snake.isEmpty()) {
            gc.setFill(Color.rgb(80, 220, 100));
            for (int i = 1; i < snake.size(); i++){
                drawRect(gc, snake.get(i), cellSize, offsetX, offsetY);
            }
            gc.setFill(Color.WHITE);
            drawRect(gc, snake.get(0), cellSize, offsetX, offsetY);
        }

        if (state != GameState.RUNNING) {
            gc.setFill(Color.rgb(0, 0, 0, 0.6));
            gc.fillRect(offsetX, offsetY, cols * cellSize, rows * cellSize);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("System Bold", 22));
            String msg = switch (state) {
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