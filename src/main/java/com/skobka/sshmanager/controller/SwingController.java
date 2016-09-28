package com.skobka.sshmanager.controller;

import com.skobka.sshmanager.config.Config;
import com.skobka.sshmanager.config.components.Connection;
import com.skobka.sshmanager.config.components.Window;
import com.skobka.sshmanager.exceptions.NoConfigException;
import com.skobka.sshmanager.view.MainWindow;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

public class SwingController extends AbstractController {
    private MainWindow view = new MainWindow();
    private Config config;

    @Override
    public void initialize(Config config) throws NoConfigException {
        this.config = config;
        this.updateBounds(config.window);
        this.updateConnectionList(config.connections);
        this.setListListeners();
        this.setBtnSettingsListeners();
        this.setBtnReloadListeners();
    }

    @Override
    public void show() {
        view.setTitle("SshManager");
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png").toURI().toString());
            icon.getImageLoadStatus();
            view.setIconImage(icon.getImage());
        } catch (URISyntaxException|NullPointerException e) {
            e.printStackTrace();
        }
        view.setVisible(true);
    }

    private void updateBounds(Window window) {
        view.setBounds(window.x, window.y, window.width, window.height);
        view.setResizable(false);
    }

    private void updateConnectionList(Connection[] connections) {
        DefaultListModel listModel = new DefaultListModel();
        for (Connection connection : connections) {
            //noinspection unchecked
            listModel.addElement(connection);
        }
        //noinspection unchecked
        view.lvConnections.setModel(listModel);
        view.lvConnections.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    }

    private void setListListeners() {
        view.lvConnections.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                if (mouseEvent.getClickCount() != 2) {
                    return;
                }

                Connection connection = (Connection) view.lvConnections.getSelectedValue();
                try {
                    connection.connect(config);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setBtnSettingsListeners() {
        view.btnSettings.addActionListener(actionEvent -> openSettings(config.getConfigFile()));
    }

    private void setBtnReloadListeners() {
        view.btnReload.addActionListener(actionEvent -> {
            try {
                config = config.getLoader().load();
                updateConnectionList(config.connections);
            } catch (IOException e) {
                app.showError("Cannot reload config");
            }
        });
    }
}
