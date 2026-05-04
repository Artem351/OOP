package ru.nsu.pisarev.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import ru.nsu.pisarev.model.Direction;
import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.SnakeModel;
import ru.nsu.pisarev.view.SnakeView;

public class SnakeController {
    @FXML
    public Button nextLevelBtn;
    @FXML
    public Button pauseBtn;
    @FXML
    public Label levelLabel;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button startBtn;

    private SnakeModel model;
    private SnakeView view;
    private GameLoop gameLoop;
    private long stepInterval = 120_000_000;
    private int levelCounter = 1;

    @FXML
    public void initialize() {
        int cols = 30, rows = 20;
        model = new SnakeModel(cols, rows);
        view = new SnakeView();

        gameCanvas.widthProperty().bind(rootPane.widthProperty());
        gameCanvas.heightProperty().bind(Bindings.subtract(rootPane.heightProperty(), 65));

        rootPane.setFocusTraversable(true);
        rootPane.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        gameCanvas.setOnMouseClicked(e -> rootPane.requestFocus());

        gameLoop = new GameLoop(this, gameCanvas, model, view, stepInterval);

        startBtn.setOnAction(e -> startGame());
        pauseBtn.setOnAction(e -> togglePause());
        nextLevelBtn.setDisable(true);
        nextLevelBtn.setOnAction(e -> nextLevel());

        gameCanvas.widthProperty().addListener((obs, old, val) -> render());
        gameCanvas.heightProperty().addListener((obs, old, val) -> render());

        updateUI();
        render();
    }

    private void render() {
        view.render(gameCanvas, model.getWidth(), model.getHeight(),
                model.getSnake(), model.getObstacles(), model.getFood(), model.getState());
    }

    private void nextLevel() {
        nextLevelBtn.setDisable(true);
        levelCounter++;
        levelLabel.setText("Level " + levelCounter);
        model.init(levelCounter);
        gameLoop.setSpeed(1 + (levelCounter - 1) * 0.2);
        updateUI();
        render();
    }

    public void updateUI() {
        scoreLabel.setText("Length: " + model.getScore() + " / 10");
        statusLabel.setText(switch (model.getState()) {
            case READY -> "Ready to play";
            case RUNNING -> "Playing";
            case WON -> "You win!";
            case LOST -> "You lose";
            default -> "";
        });
        nextLevelBtn.setDisable(model.getState() != GameState.WON);
        startBtn.setText(model.getState() == GameState.RUNNING ? "Restart" : "Start");
        pauseBtn.setDisable(model.getState() != GameState.RUNNING);
        pauseBtn.setText(model.getState() != GameState.RUNNING ? "" : (gameLoop.isRunning() ? "Pause" : "Resume"));
    }

    private void handleKeyPress(KeyEvent event) {
        if (!gameLoop.isRunning()) {
            return;
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

        if (moved) {
            event.consume();
        }
    }

    private void startGame() {
        model.init(levelCounter);
        if (gameLoop.isRunning()) {
            gameLoop.stop();
        }
        gameLoop.reset();
        gameLoop.start();
        rootPane.requestFocus();
        updateUI();
        render();
    }

    private void togglePause() {
        if (gameLoop.isRunning()) {
            gameLoop.stop();
            model.setState(GameState.PAUSED);
            statusLabel.setText("⏸ Pause");
        } else if (model.getState() == GameState.PAUSED) {
            model.setState(GameState.RUNNING);
            gameLoop.start();
            statusLabel.setText("Playing");
        }
        updateUI();
        render();
    }

    public SnakeModel getModel() {
        return model;
    }
}