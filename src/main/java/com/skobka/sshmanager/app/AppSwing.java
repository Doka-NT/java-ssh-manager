package com.skobka.sshmanager.app;

import com.skobka.sshmanager.controller.ControllerInterface;
import com.skobka.sshmanager.controller.SwingController;
import com.skobka.sshmanager.exceptions.NoConfigException;

import javax.swing.*;
import java.io.IOException;

public class AppSwing extends AbstractApplication {

    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        ControllerInterface controller = new SwingController();
        controller.setApplication(this);
        try {
            controller.initialize(getConfig());
            controller.show();
        } catch (NoConfigException | IOException e) {
            this.showError("Cannot create config file\n"+System.getProperty("user.home")+this.getConfigFile());
        }
    }

    public void showError(String message) {
        System.out.println("[error] " + message);
    }
}
