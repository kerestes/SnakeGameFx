package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("snakegame.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Snake Game");
    }

    public static void main(String[] args) {
        launch();
    }

}