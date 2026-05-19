package ru.nsu.pisarev.view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import ru.nsu.pisarev.controller.SnakeController;
import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.Point;

import java.util.List;
import java.util.Set;

public class MainWindow {
    public final long STEP_INTERVAL = 120_000_000;

    private final SnakeView view;//final field, specific constructor for application
    private SnakeController snakeController;//final field, specific constructor for application
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


    private boolean initialized = false;

    public MainWindow() {
        this.view = new SnakeView();
    }

    public void injectDependencies(SnakeController snakeController) {
        this.snakeController = snakeController;
        if (initialized) {
            finishInitialization();
        }
    }

    @FXML
    public void initialize() {
        initialized = true;
        if (snakeController == null) { //do it later
            return;
        }
        finishInitialization();
    }

    public void finishInitialization() {
        setupCanvasLayout();
        setupInputHandling();
        setupButtonActions();
        setupResizeListeners();
    }

    private void setupCanvasLayout() {
        gameCanvas.widthProperty().bind(rootPane.widthProperty());
        gameCanvas.heightProperty().bind(Bindings.subtract(rootPane.heightProperty(), 65)); // 65px под тулбар
    }

    private void setupInputHandling() {
        rootPane.setFocusTraversable(true);
        rootPane.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        gameCanvas.setOnMouseClicked(e -> rootPane.requestFocus());
    }


    private void setupButtonActions() {
        startBtn.setOnAction(e -> snakeController.startGame());
        pauseBtn.setOnAction(e -> snakeController.togglePause());
        nextLevelBtn.setDisable(true);
        nextLevelBtn.setOnAction(e -> snakeController.nextLevel());
    }

    private void setupResizeListeners() {
        gameCanvas.widthProperty().addListener((obs, old, val) -> snakeController.render());
        gameCanvas.heightProperty().addListener((obs, old, val) -> snakeController.render());
    }


    public void render(
            List<Point> snake, Set<Point> obstacles,
            List<Point> food, GameState state) {
        view.render(gameCanvas, snake, obstacles, food, state);
    }


    public void updateUI(int score, GameState state, boolean isRunning) {
        scoreLabel.setText("Length: " + score + " / 10");
        statusLabel.setText(switch (state) {
            case READY -> "Ready to play";
            case RUNNING -> "Playing";
            case WON -> "You win!";
            case LOST -> "You lose";
            default -> "";
        });
        nextLevelBtn.setDisable(state != GameState.WON);
        startBtn.setText(state == GameState.RUNNING ? "Restart" : "Start");
        pauseBtn.setDisable(state != GameState.RUNNING);
        pauseBtn.setText(state != GameState.RUNNING ? "" : (isRunning ? "Pause" : "Resume"));
    }

    private void handleKeyPress(KeyEvent event) {
        boolean moved = snakeController.handleKey(event);

        if (moved) {
            event.consume();
        }
    }

    public void disableNextLevelBtn() {
        nextLevelBtn.setDisable(true);
    }

    public void setLevel(int levelCounter) {
        levelLabel.setText("Level " + levelCounter);
    }

    public void setPauseLabel() {
        statusLabel.setText("⏸ Pause");
    }

    public void setPlayingLabel() {
        statusLabel.setText("Playing");
    }

    public void requestFocus() {
        rootPane.requestFocus();
    }

}