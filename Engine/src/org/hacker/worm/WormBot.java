package org.hacker.worm;

import java.util.Properties;

import org.hacker.engine.Bot;

public class WormBot extends Bot {
    public int x, y;
    public int dir;
    public String name;
    
    private WormSpinalCord spinalCord;

    public WormBot(WormSpinalCord spinalCord) {
        super();
        this.spinalCord = spinalCord;
        name = spinalCord.toString();
    }

    public void think(int dx, int dy, int[][] board, int[][] enemies) {
        try {
            dir = spinalCord.think(dx, dy, board, x, y, dir, enemies);
            dir = (Math.abs(dir) % 4); // sanity check
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            System.err.println("no thinking for this bot, oh well... " + this);
        }
        x += WormModel.offsets[dir][0];
        y += WormModel.offsets[dir][1];
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + "} dir: " + dir + " " + ((name == null) ? super.toString() : name);
    }

    @Override
    public Properties infoAsProperties() {
        Properties p = new Properties();
        p.setProperty("Loc", "{" + x + ", " + y + "}");
        spinalCord.addInfoProperties(p);
        return p;
    }
}
