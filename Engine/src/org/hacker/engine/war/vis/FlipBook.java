package org.hacker.engine.war.vis;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * animated sequence of images
 */
public class FlipBook extends Effect {
    private int frame = 0;
    private final int numFrames;
    private final Image srcImage;

    public FlipBook(double x, double y, int numFrames, Image srcImage) {
        super(x, y);
        this.numFrames = numFrames;
        this.srcImage = srcImage;
    }

    @Override
    public void draw(Graphics2D g, double scale) {
        double xx = x * scale;
        double yy = y * scale;
        BufferedImage im = Pix.getSlicedImage(new Point(frame++, 0), srcImage, 1, 1, 0, 0);
        g.drawImage(im, (int) xx - im.getWidth() / 2, (int) yy - im.getHeight() / 2, null);
    }

    @Override
    public boolean isDone() {
        return frame >= numFrames;
    }
}
