package com.skobka.sshmanager.app;

import com.skobka.sshmanager.CustomAlert;
import com.skobka.sshmanager.config.Config;
import com.skobka.sshmanager.config.Loader;
import com.skobka.sshmanager.controller.JavaFxController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */
public class AppJavaFx extends Application implements AppInterface {
    private Loader configLoader = new Loader();

    public Config getConfig() throws IOException {
        return configLoader.load();
    }

    @Override
    public String getConfigFile() {
        return configLoader.getConfigFile();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL resource = getClass().getResource("/main.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();

        JavaFxController controller = loader.getController();
        primaryStage.setTitle("SshManager");

        Config config = getConfig();


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

    @Override
    public void run() {
        launch();
    }

    @Override
    public void showError(String message) {
        CustomAlert.show(Alert.AlertType.ERROR, "Error", message);
    }
}
