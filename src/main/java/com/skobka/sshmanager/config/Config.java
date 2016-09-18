package com.skobka.sshmanager.config;

import com.skobka.sshmanager.config.components.Command;
import com.skobka.sshmanager.config.components.Connection;
import com.skobka.sshmanager.config.components.Window;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

public class Config {
    public Command command;
    public Window window;
    public Connection[] connections;

    private String configFile;
    private Loader loader;

    public String getConfigFile() {
        return configFile;
    }

    void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public Loader getLoader() {
        return loader;
    }

    void setLoader(Loader loader) {
        this.loader = loader;
    }
}
