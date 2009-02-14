package org.hacker.engine.war.vis;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Laser extends Effect {
    protected double destX, destY;
    private Color color;
    protected int numFrames = 5;
    protected int frame = 0;

    public Laser(double x, double y, double destX, double destY, Color color) {
        super(x, y);

        this.color = color;
        this.destX = destX;
        this.destY = destY;
    }

    @Override
    public void draw(Graphics2D g, double scale) {
        g.setColor(color);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Composite origComposite = g.getComposite();
        AlphaComposite alphaLines = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - (float) frame / (float) numFrames);
        g.setComposite(alphaLines);
        g.drawLine((int) (x * scale), (int) (y * scale), (int) (destX * scale), (int) (destY * scale));
        g.setComposite(origComposite);
        frame++;
    }

    @Override
    public boolean isDone() {
        return frame >= numFrames;
    }
}
