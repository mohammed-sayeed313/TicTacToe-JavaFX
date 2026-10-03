package com.sayeed.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        GameController controller = new GameController();

        Scene scene = new Scene(controller.createUI(), 900, 950);

        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        stage.setTitle("Tic-Tac-Toe | JavaFX");
        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
