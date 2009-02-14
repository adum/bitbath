package org.hacker.engine.war.vis;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Projectile extends Effect {
    private final Image srcImage;
    protected double destX, destY;
    protected double startX, startY;
    protected boolean isMoving = false;
    private double moveAngle;
    private double vel;

    public Projectile(double x, double y, Image srcImage, double destX, double destY, double vel) {
        super(x, y);

        this.srcImage = srcImage;
        this.destX = destX;
        this.destY = destY;
        this.vel = vel;
        startX = x;
        startY = y;
        
        isMoving = true;
        
        moveAngle = Math.atan2(destY - y, destX - x);
    }

    @Override
    public void draw(Graphics2D g, double scale) {
        x += Math.cos(moveAngle) * vel;
        y += Math.sin(moveAngle) * vel;

        // are we done?
        boolean xgood = false;
        if (destX > startX) {
            if (x >= destX)
                xgood = true;
        }
        else {
            if (x <= destX)
                xgood = true;
        }
        boolean ygood = false;
        if (destY > startY) {
            if (y >= destY)
                ygood = true;
        }
        else {
            if (y <= destY)
                ygood = true;
        }
        if (xgood && ygood)
            isMoving = false;
        
        double xx = x * scale;
        double yy = y * scale;

        AffineTransform xform = new AffineTransform();
        xform.rotate(moveAngle, xx + 9 / 2, yy + 9 / 2);
        xform.translate(xx, yy);
        g.drawImage(srcImage, xform, null);
//        g.drawImage(srcImage, (int) xx, (int) yy, null);
    }

    @Override
    public boolean isDone() {
        return !isMoving;
    }

}
