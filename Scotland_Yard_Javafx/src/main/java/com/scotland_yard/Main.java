package com.scotland_yard;

import com.scotland_yard.controllers.GameScreen;
import com.scotland_yard.controllers.MainScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Stage primaryStage;
    public static MainScreen mainController;
    public static GameScreen gameController;
    private static Scene mainScene;
    private static Scene gameScreen;

    private static void setupPrimaryStage(Stage stage) throws IOException {
        // Sets up the primary stage with the main scene
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/UI/main-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 540);
        stage.setTitle("Scotland Yard");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        mainController = fxmlLoader.getController();
        primaryStage = stage;
        mainScene = scene;

        primaryStage.setOnCloseRequest(windowEvent -> {         // so all used threads terminate
            System.exit(0);
        });
    }

    private static void setupGameScreen() throws IOException {
        // Loads game scene
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/UI/game-screen.fxml"));
        gameScreen = new Scene(fxmlLoader.load(), 960, 540);
        gameController = fxmlLoader.getController();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void quit() {
        primaryStage.close();
    }

    public static void setMainScene() {
        primaryStage.setScene(mainScene);
    }

    public static void setGameScene() {
        primaryStage.setScene(gameScreen);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Sets up the stage and scenes
        setupPrimaryStage(stage);
        setupGameScreen();
    }
}