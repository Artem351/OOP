package ru.nsu.pisarev.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.SnakeModel;
import ru.nsu.pisarev.view.SnakeView;

public class GameLoop extends AnimationTimer {
    private final SnakeController controller;
    private final Canvas canvas;
    private final SnakeModel model;
    private final SnakeView view;
    private long stepInterval;
    private long lastUpdate = 0;
    private boolean running = false;

    public GameLoop(SnakeController controller, Canvas canvas, SnakeModel model, SnakeView view, long stepInterval) {
        this.controller = controller;
        this.canvas = canvas;
        this.model = model;
        this.view = view;
        this.stepInterval = stepInterval;
    }

    @Override
    public void handle(long now) {
        if (lastUpdate == 0) {
            lastUpdate = now;
            return;
        }
        if (now - lastUpdate >= stepInterval) {
            if (model.getState() == GameState.RUNNING) {
                model.step();
            }
            view.render(canvas, model.getWidth(), model.getHeight(),
                    model.getSnake(), model.getObstacles(), model.getFood(), model.getState());
            controller.updateUI();
            lastUpdate = now;
        }
    }

    @Override
    public void start() {
        running = true;
        super.start();
    }

    @Override
    public void stop() {
        running = false;
        lastUpdate = 0;
        super.stop();
    }

    public boolean isRunning() {
        return running;
    }

    public void setSpeed(double factor) {
        stepInterval = Math.max(40_000_000, (long) (120_000_000 / factor));
    }

    public void reset() {
        lastUpdate = 0;
    }
}