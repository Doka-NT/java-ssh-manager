package com.skobka.sshmanager.controller;

import com.skobka.sshmanager.CustomAlert;
import com.skobka.sshmanager.config.Config;
import com.skobka.sshmanager.config.components.Connection;
import com.skobka.sshmanager.config.components.Window;
import com.skobka.sshmanager.exceptions.NoConfigException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

public class JavaFxController extends AbstractController {

    public ListView<Connection> lvConnections;
    public AnchorPane root;
    public Button btnSettings;
    public Button btnReload;

    private Config config;

    private void setConfig(Config config) {
        this.config = config;
    }

    private void updateBounds() throws NoConfigException {
        Window window = getConfig().window;

        root.setPrefWidth(window.width);
        root.setPrefHeight(window.height);
    }

    private void updateConnectionList() throws NoConfigException {
        ObservableList<Connection> list = FXCollections.observableList(Arrays.asList(getConfig().connections));

        lvConnections.setItems(list);
        lvConnections.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private Config getConfig() throws NoConfigException {
        if (this.config == null) {
            throw new NoConfigException();
        }

        return this.config;
    }

    private void reloadConfig() throws IOException, NoConfigException {
        config = config.getLoader().load();
        updateConnectionList();
    }

    @Override
    public void initialize(Config config) throws NoConfigException {
        this.setConfig(config);
        this.updateConnectionList();
        this.updateBounds();

        this.btnSettings.setOnAction(event -> openSettings(this.config.getConfigFile()));

        this.btnReload.setOnAction(event -> {
            try {
                reloadConfig();
            } catch (IOException|NoConfigException e) {
                CustomAlert.show(
                        Alert.AlertType.WARNING,
                        "Could not reload config",
                        "Could not reload config file with default editor. \n" + config.getConfigFile()
                );
            }
        });

        this.lvConnections.setOnMouseClicked(event -> {
            if (event.getClickCount() != 2) {
                return;
            }

            Connection connection = lvConnections.getSelectionModel().getSelectedItem();
            try {
                connection.connect(config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void show() {

    }
}
