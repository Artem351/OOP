module TaskSnake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    opens ru.nsu.pisarev to javafx.fxml;
    exports ru.nsu.pisarev;
}