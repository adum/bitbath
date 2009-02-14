package org.hacker.engine;

import java.util.Properties;

/**
 * controls a unit
 */
public class Bot {
    private boolean alive = true;

    public boolean isAlive() {
        return alive;
    }

    public void seppuku() {
        alive = false;
    }

    public Properties infoAsProperties() {
        return new Properties();
    }
}
