package ru.nsu.pisarev.view;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import ru.nsu.pisarev.controller.GameController;
import ru.nsu.pisarev.model.GameState;
import ru.nsu.pisarev.model.SnakeModel;

public class GameLoop extends AnimationTimer {
    private final GameController controller;

    public GameLoop(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(long now) {
        controller.handle(now);
    }

}