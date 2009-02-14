package org.hacker.engine.war.vis;

import java.awt.Graphics2D;

public abstract class Effect {
    public double x, y;
    
    public Effect(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(Graphics2D g, double scale);
    public abstract boolean isDone();
}
