module TaskSnake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    opens ru.nsu.pisarev to javafx.fxml;
    exports ru.nsu.pisarev;
    exports ru.nsu.pisarev.controller;
    opens ru.nsu.pisarev.controller to javafx.fxml;
    exports ru.nsu.pisarev.model;
    opens ru.nsu.pisarev.model to javafx.fxml;
    exports ru.nsu.pisarev.view;
    opens ru.nsu.pisarev.view to javafx.fxml;
}