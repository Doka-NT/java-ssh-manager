package com.skobka.sshmanager.controller;


import com.skobka.sshmanager.app.AppInterface;
import com.skobka.sshmanager.config.Config;
import com.skobka.sshmanager.exceptions.NoConfigException;

public interface ControllerInterface {
    void initialize(Config config) throws NoConfigException;
    void show();
    void setApplication(AppInterface app);
}
