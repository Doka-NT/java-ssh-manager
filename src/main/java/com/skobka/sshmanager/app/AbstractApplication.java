package com.skobka.sshmanager.app;


import com.skobka.sshmanager.config.Config;
import com.skobka.sshmanager.config.Loader;

import java.io.IOException;

abstract public class AbstractApplication implements AppInterface {
    private Loader configLoader = new Loader();

    public Config getConfig() throws IOException {
        return configLoader.load();
    }

    @Override
    public String getConfigFile() {
        return configLoader.getConfigFile();
    }

    public static String getHomeDir() {
        return System.getProperty("user.home");
    }
}
