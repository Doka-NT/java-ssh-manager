package com.skobka.sshmanager.app;

import com.skobka.sshmanager.config.Config;

import java.io.IOException;

public interface AppInterface {
    void run();
    void showError(String message);
    Config getConfig() throws IOException;
    String getConfigFile();
}
