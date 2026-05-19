package ru.nsu.pisarev.controller;

import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.SnakeModel;
import ru.nsu.pisarev.view.GameLoop;
import ru.nsu.pisarev.view.MainWindow;

public class GameController {
    private final GameLoop gameLoop;
    private final SnakeModel model;
    private final MainWindow mainWindow;
    private long stepInterval;
    private long lastUpdate = 0;
    private boolean running = false;

    public GameController(GameLoop gameLoop, SnakeModel model, MainWindow mainWindow, long initStepInterval) {
        this.gameLoop = gameLoop;
        this.model = model;
        this.mainWindow = mainWindow;
        this.stepInterval = initStepInterval;
    }

    public void handle(long now) {
        if (!running) {
            return;
        }

        if (lastUpdate == 0) {
            lastUpdate = now;
            return;
        }

        if (now - lastUpdate >= stepInterval) {
            if (model.getState() == GameState.RUNNING) {
                model.step();  // Логика движения
            }
            if (mainWindow != null) {
                mainWindow.updateUI(model.getScore(), model.getState(), running);
                mainWindow.render(model.getSnake(), model.getObstacles(), model.getFood(), model.getState());
            }
            lastUpdate = now;
        }
    }

    public void start() {
        running = true;
        lastUpdate = 0;
        gameLoop.start();
    }

    public void stop() {
        running = false;
        lastUpdate = 0;
        gameLoop.stop();
    }

    public void reset() {
        lastUpdate = 0;
    }

    public boolean isRunning() {
        return running;
    }

    public void setSpeed(double factor) {
        stepInterval = Math.max(40_000_000, (long) (120_000_000 / factor));
    }
}