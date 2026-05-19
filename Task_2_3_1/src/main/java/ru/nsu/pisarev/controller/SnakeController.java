package ru.nsu.pisarev.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.pisarev.model.Direction;
import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.SnakeModel;
import ru.nsu.pisarev.view.GameLoop;
import ru.nsu.pisarev.view.MainWindow;

public class SnakeController {
    public final static int COLS = 30;
    public final static int ROWS = 20;

    private SnakeModel model;

    private MainWindow mainWindow;
    private GameController gameController;
    private int levelCounter = 1;


    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void gameLoopTick(long now) {
        if (gameController != null) {
            gameController.handle(now);
        }
    }


    public void initialize() {
        model = new SnakeModel(COLS, ROWS);

        GameLoop gameLoop = new GameLoop(this);
        gameController = new GameController(gameLoop, model, mainWindow, mainWindow.STEP_INTERVAL);

        updateUI();
        render();
    }

    public void startGame() {
        model.init(levelCounter);
        if (gameController.isRunning()) {
            gameController.stop();
        }
        gameController.reset();
        gameController.start();
        mainWindow.requestFocus();
        updateUI();
        render();
    }

    public void togglePause() {
        if (gameController.isRunning()) {
            gameController.stop();
            model.setState(GameState.PAUSED);
            mainWindow.setPauseLabel();
        } else if (model.getState() == GameState.PAUSED) {
            model.setState(GameState.RUNNING);
            gameController.start();
            mainWindow.setPlayingLabel();
        }
        updateUI();
        render();
    }

    public void nextLevel() {
        mainWindow.disableNextLevelBtn();
        levelCounter++;
        mainWindow.setLevel(levelCounter);
        model.init(levelCounter);
        gameController.setSpeed(1 + (levelCounter - 1) * 0.2);
        updateUI();
        mainWindow.render(model.getSnake(), model.getObstacles(), model.getFood(), model.getState());
    }

    public boolean handleKey(KeyEvent event) {
        if (!gameController.isRunning()) {
            return false;
        }
        KeyCode code = event.getCode();
        boolean moved = false;

        switch (code) {
            case W, UP -> {
                model.setDirection(Direction.UP);
                moved = true;
            }
            case S, DOWN -> {
                model.setDirection(Direction.DOWN);
                moved = true;
            }
            case A, LEFT -> {
                model.setDirection(Direction.LEFT);
                moved = true;
            }
            case D, RIGHT -> {
                model.setDirection(Direction.RIGHT);
                moved = true;
            }
            default -> {
                //Nothing to do
            }
        }
        return moved;
    }

    public void render() {
        mainWindow.render(model.getSnake(), model.getObstacles(), model.getFood(), model.getState());
    }

    private void updateUI() {
        mainWindow.updateUI(model.getScore(), model.getState(), gameController.isRunning());
    }
}
