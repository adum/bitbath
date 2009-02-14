package org.hacker.engine;

import java.awt.Graphics2D;

/**
 * someone who views a game in 2d
 */
public interface Viewer {
    public void draw(GameModel model, Graphics2D g, int dx, int dy, boolean showInfo);
}
