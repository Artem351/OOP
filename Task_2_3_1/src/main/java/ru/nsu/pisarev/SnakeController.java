package ru.nsu.pisarev;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.beans.binding.Bindings;

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
    private long lastUpdate = 0;
    private int levelCounter = 1;

    public int getLevelCounter() {
        return levelCounter;
    }

    @FXML
    public void initialize() {
        int cols = 30, rows = 20;
        model = new SnakeModel(cols, rows,this);
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
        nextLevelBtn.setOnAction(e -> nexLevel());
        gameCanvas.widthProperty().addListener((obs, old, val) -> view.render(gameCanvas, model));
        gameCanvas.heightProperty().addListener((obs, old, val) -> view.render(gameCanvas, model));

        updateUI();
        view.render(gameCanvas, model);
    }

    private void nexLevel() {
        nextLevelBtn.setDisable(true);
        levelCounter++;
        levelLabel.setText("Level "+levelCounter);
        model.init();
        updateUI();
        view.render(gameCanvas, model);
    }


    public void updateUI() {
        scoreLabel.setText("Length: " + model.getScore() + " / " + 10);
        String result = switch (model.getState()) {
            case READY -> "Ready to play";
            case RUNNING -> "Playing";
            case WON -> "You win!";
            case LOST -> "You lose";
            default -> "";
        };
        statusLabel.setText(result);
        nextLevelBtn.setDisable(model.getState() != GameState.WON);
        startBtn.setText(model.getState() == GameState.RUNNING ? "Restart" : "Start");
        pauseBtn.setDisable(model.getState()!= GameState.RUNNING);
        pauseBtn.setText(model.getState()!= GameState.RUNNING ? "" :(gameLoop.isRunning() ? "Pause" : "Resume"));
    }

    public SnakeModel getModel() {
        return model;
    }

    public long getStepInterval() {
        return stepInterval;
    }

    public long getLastUpdate() {
        return lastUpdate;
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
        model.init();


        if (gameLoop.isRunning()) {
            gameLoop.stop();
        }
        gameLoop.reset();
        gameLoop.start();

        rootPane.requestFocus();
        updateUI();
        view.render(gameCanvas, model);
    }

    private void togglePause() {
        if (gameLoop.isRunning()) {
            gameLoop.stop();
            statusLabel.setText("Pause");
        } else {
            if (model.getState() == GameState.RUNNING) {
                gameLoop.start();
                statusLabel.setText("Playing");
            }
        }
    }

}