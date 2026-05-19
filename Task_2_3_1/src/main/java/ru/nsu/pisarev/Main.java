package ru.nsu.pisarev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.pisarev.controller.SnakeController;
import ru.nsu.pisarev.view.MainWindow;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("snake.fxml"));
        Parent root = loader.load();

        MainWindow mainWindow = loader.getController();
        SnakeController snakeController = new SnakeController();

        mainWindow.injectDependencies(snakeController);
        snakeController.setMainWindow(mainWindow);

        mainWindow.finishInitialization();
        snakeController.initialize();

        primaryStage.setTitle("JavaFX Snake");
        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(850);
        primaryStage.setHeight(620);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(550);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

}