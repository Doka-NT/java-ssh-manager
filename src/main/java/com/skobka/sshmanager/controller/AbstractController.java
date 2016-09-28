package com.skobka.sshmanager.controller;

import com.skobka.sshmanager.app.AbstractApplication;
import com.skobka.sshmanager.app.AppInterface;

import java.io.File;
import java.io.IOException;

abstract class AbstractController implements ControllerInterface {
    AppInterface app;

    @Override
    public void setApplication(AppInterface app) {
        this.app = app;
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        app.showError("Cannot open config file with default editor. \n" + filePath);
    }

    void openSettings(String configFile) {
        String dir = AbstractApplication.getHomeDir();
        File file = new File(dir + configFile);

        this.openFile(file.getPath());
    }
}
