module TaskSnake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jdk.compiler;
    opens ru.nsu.pisarev to javafx.fxml;
    exports ru.nsu.pisarev;
    exports ru.nsu.pisarev.model;
    exports ru.nsu.pisarev.controller;
    opens ru.nsu.pisarev.model to javafx.fxml;
    exports ru.nsu.pisarev.view;
    opens ru.nsu.pisarev.view to javafx.fxml;
}