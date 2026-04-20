package ru.nsu.pisarev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("snake.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("JavaFX Snake");
        primaryStage.setScene(new Scene(root));

        primaryStage.setWidth(850);
        primaryStage.setHeight(620);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(550);

        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}