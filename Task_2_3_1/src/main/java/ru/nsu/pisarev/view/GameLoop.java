package ru.nsu.pisarev.view;

import javafx.animation.AnimationTimer;
import ru.nsu.pisarev.controller.SnakeController;

public class GameLoop extends AnimationTimer {
    private final SnakeController controller;

    public GameLoop(SnakeController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(long now) {
        if (controller != null) {
            controller.gameLoopTick(now);
        }
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}