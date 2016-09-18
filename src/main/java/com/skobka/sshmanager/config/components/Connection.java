package com.skobka.sshmanager.config.components;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

public class Connection {
    public String host;
    public String label;
    public int port;
    public String[] args;

    @Override
    public String toString() {
        return this.label != null ? this.label : super.toString();
    }
}
