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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

public class MainController {

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

    public void initialize(Config config) throws NoConfigException {
        this.setConfig(config);
        this.updateConnectionList();
        this.updateBounds();

        this.btnSettings.setOnAction(event -> {
            String dir = System.getProperty("user.dir");
            File file = new File(dir + this.config.getConfigFile());

            openFile(file.getPath());
        });

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

            String host = connection.host != null ? connection.host : "localhost";
            String port = connection.port > 1 ? String.valueOf(connection.port) : "22";
            String args = connection.args != null ? String.join(" ", Arrays.asList(connection.args)) : "";

            String cmd = String.join(" ", Arrays.asList(config.command.ssh));
            String[] pipeline = {
                    cmd, host, "-p", port, args
            };

            String fullCommand = String.join(" ", Arrays.asList(pipeline));
            System.out.println(fullCommand);
            try {
                Runtime.getRuntime().exec(fullCommand);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Open file in default program
     *
     * @param filePath file
     */
    private void openFile(String filePath) {
        String[] run = {
                "xdg-open",
                "rundll32 url.dll,FileProtocolHandler",
                "open"
        };

        for (String cmd : run) {
            try {
                Runtime.getRuntime().exec(cmd + " " + filePath);
                return;
            } catch (IOException ignored) {
            }
        }

        CustomAlert.show(
                Alert.AlertType.WARNING,
                "Cannot open file",
                "Cannot open config file with default editor. \n" + filePath
        );
    }
}
