package com.skobka.sshmanager.config.components;

import com.skobka.sshmanager.config.Config;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

public class Connection {
    @SuppressWarnings("WeakerAccess")
    public String host;
    @SuppressWarnings("WeakerAccess")
    public String label;
    @SuppressWarnings("WeakerAccess")
    public int port;
    @SuppressWarnings("WeakerAccess")
    public String[] args;

    @Override
    public String toString() {
        return this.label != null ? this.label : super.toString();
    }

    private String getConnectionCommand(Config config) {
        String host = this.host != null ? this.host : "localhost";
        String port = this.port > 1 ? String.valueOf(this.port) : "22";
        String args = this.args != null ? String.join(" ", Arrays.asList(this.args)) : "";

        String cmd = String.join(" ", Arrays.asList(config.command.ssh));
        String[] pipeline = {
                cmd, host, "-p", port, args
        };

        return String.join(" ", Arrays.asList(pipeline));
    }

    public void connect(Config config) throws IOException {
        Runtime.getRuntime().exec(getConnectionCommand(config));
    }
}
