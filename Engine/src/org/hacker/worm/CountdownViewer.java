package org.hacker.worm;

import java.awt.Font;
import java.awt.Graphics2D;

import org.hacker.engine.GameModel;

public class CountdownViewer extends WormViewer {
    private long startTime = System.currentTimeMillis();
    Font font = new Font("Sans Serif", Font.BOLD, 48);

    public CountdownViewer(int nbots) {
        super(nbots);
    }

    @Override
    public void draw(GameModel model, Graphics2D g, int dx, int dy, boolean showInfo) {
        super.draw(model, g, dx, dy, showInfo);
        long t = countdown();
        if (t < 2000) {
            int secs = 2 - ((int) (t / 1000));
            g.setFont(font);
            g.drawString(""+secs, dx / 2, dy / 2);
        }
    }

    private long countdown() {
        long t = System.currentTimeMillis() - startTime;
        return t;
    }

    public boolean finishedCountdown() {
        long t = countdown();
        return (t > 2000);
    }
}
