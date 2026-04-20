package ru.nsu.pisarev;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

public class GameLoop extends AnimationTimer {
    private final SnakeController controller;
    private final Canvas canvas;
    private final SnakeModel model;
    private final SnakeView view;
    private long stepInterval;
    private long lastUpdate = 0;

    private boolean running = false;

    @Override
    public void handle(long now) {
        if (lastUpdate == 0) {
            lastUpdate = now;
            return;
        }
        if (now - lastUpdate >= stepInterval) {
            if (model.getState() == GameState.RUNNING) {
                model.step();
                controller.updateUI();
            }
            view.render(canvas, model);
            lastUpdate = now;
        }
    }

    public GameLoop(SnakeController controller, Canvas canvas, SnakeModel model, SnakeView view, long stepInterval) {
        this.controller = controller;
        this.canvas = canvas;
        this.model = model;
        this.view = view;
        this.stepInterval = stepInterval;
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

    public void setSpeed(double speedFactor) {
        this.stepInterval = Math.max(40_000_000, (long)(120_000_000 / speedFactor));
    }

    public void reset() {
        lastUpdate = 0;
    }
}