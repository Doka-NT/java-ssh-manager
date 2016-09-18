package com.skobka.sshmanager;

import com.skobka.sshmanager.config.Config;
import com.skobka.sshmanager.config.Loader;
import com.skobka.sshmanager.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        primaryStage.setTitle("SshManager");

        Loader configLoader = new Loader();
        Config config;
        try {
            config = configLoader.load();
        } catch (IOException e) {
            CustomAlert.show(Alert.AlertType.ERROR, "Error",
                    "Cannot create config file\n"
                    +System.getProperty("user.home")+configLoader.getConfigFile());
            return;
        }

        primaryStage.setScene(new Scene(
                root,
                config.window.width,
                config.window.height
        ));
        primaryStage.setMinWidth(config.window.width);
        primaryStage.setMinHeight(config.window.height);
        primaryStage.setWidth(config.window.width);
        primaryStage.setHeight(config.window.height);
        primaryStage.setResizable(false);

        primaryStage.getIcons().add(new Image(getClass().getResource("/icon.png").toURI().toString()));

        controller.initialize(config);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
