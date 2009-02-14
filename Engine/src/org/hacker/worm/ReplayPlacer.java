package org.hacker.worm;

import java.awt.Point;

/**
 * how to get bots on the board when we have a replay string
 * @author amiller
 */
public class ReplayPlacer implements BotPlacer {
    private String[] strings;
    private int sindex = 2;

    public ReplayPlacer(String[] strings) {
        this.strings = strings;
    }

    public Point placeBot(int index) {
        Point p = new Point();
        p.x = Integer.parseInt(strings[sindex++]);
        p.y = Integer.parseInt(strings[sindex++]);
        return p;
    }

}
