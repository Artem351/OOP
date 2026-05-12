package ru.nsu.pisarev.controller;

import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.Point;
import ru.nsu.pisarev.model.SnakeModel;
import ru.nsu.pisarev.view.GameLoop;
import ru.nsu.pisarev.view.MainWindow;
import ru.nsu.pisarev.view.SnakeView;

import java.util.List;
import java.util.Set;

public class GameController {
    private final SnakeView view;
    private final GameLoop gameLoop;
    private final SnakeModel model;
    private final MainWindow mainWindow;
    private long stepInterval;
    private long lastUpdate = 0;

    private boolean running = false;

    public GameController(SnakeView view, GameLoop gameLoop, SnakeModel model, MainWindow mainWindow, long initStepInterval) {
        this.view = view;
        this.gameLoop = gameLoop;
        this.model = model;
        this.mainWindow = mainWindow;
        this.stepInterval = initStepInterval;
    }

    public void initialize(int cols, int rows,
                           List<Point> snake, Set<Point> obstacles,
                           List<Point> food, int score, GameState state, boolean isRunning) {
        mainWindow.initialize();
        mainWindow.updateUI(score, state, isRunning);
        mainWindow.render(snake, obstacles,food, state);
    }


    public void handle(long now) {
        if (lastUpdate == 0) {
            lastUpdate = now;
            return;
        }
        if (now - lastUpdate >= stepInterval) {
            if (model.getState() == GameState.RUNNING) {
                model.step();
            }
            mainWindow.updateUI(model.getScore(), model.getState(), running);
            mainWindow.render(model.getSnake(), model.getObstacles(), model.getFood(), model.getState());
            lastUpdate = now;
        }
    }

    public void start() {
        running = true;
        gameLoop.start();
    }

    public void stop() {
        lastUpdate = 0;
        running = false;
        gameLoop.stop();
    }

    public void reset() {
        lastUpdate = 0;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setSpeed(double factor) {
        stepInterval = Math.max(40_000_000, (long) (120_000_000 / factor));
    }
}
