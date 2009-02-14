package org.hacker.engine.war.vis;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import org.hacker.engine.war.WarViewer;

public class Pix {
    public static final int SZ = 20;

    public static Image imSrcGrass = loadResourceImage("Grass.png");
    public static Image[] imSrcTank = { loadResourceImage("Tank5a.png") , loadResourceImage("Tank5b.png") };
    public static Image[] imSrcBoat = { loadResourceImage("Boat3a.png") , loadResourceImage("Boat3b.png") };
    public static Image[] imSrcArtil = { loadResourceImage("Boat2a.png") , loadResourceImage("Boat2b.png") };
    public static Image[] imSrcBase = { loadResourceImage("BaseA.png") , loadResourceImage("BaseB.png") };
    public static Image imSrcBaseNeutral = loadResourceImage("Base0.png");
    public static Image imSrcExplosion = loadResourceImage("Explosion.png");
    public static Image imSrcExplosionSmall = loadResourceImage("ExplosionSmall.png");
    public static Image imSrcGrassCrater = loadResourceImage("GrassCrtr.png");
    public static Image imSrcTree2Grass = loadResourceImage("TreeGrs.png");
    public static Image imSrcSwamp2Grass = loadResourceImage("Swamp.PNG");
    public static Image imSrcGrass2Mountain = loadResourceImage("Grass2Mnt.png");
    public static Image imBullet2 = loadResourceImage("Bullet2.png");
    public static Image imSwampPlant = loadResourceImage("SwampPlnt.PNG");
    public static Image imWreck = loadResourceImage("wreck.png");
    public static Image imSwampBubble = loadResourceImage("SwmpBubl.png");
    public static Image imCircle = loadResourceImage("circle.png");

    public static BufferedImage getSlicedImage(Point pt, Image src, int chunkX, int chunkY, int spacingX, int spacingY) {
        Map<Point, BufferedImage> mp = slicedImages.get(src);
        if (mp != null) {
            BufferedImage ima = mp.get(pt);
            if (ima != null)
                return ima;
        }
//        Image im = src.getScaledInstance(SZ, SZ, Image.SCALE_FAST);
        BufferedImage im = new BufferedImage(SZ * chunkX, SZ * chunkY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = im.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        int srcX = pt.x * (chunkX + spacingX) * SZ;
        int srcY = pt.y * (chunkY + spacingY) * SZ + 1;
        g2.drawImage(src, 
                0, 0, SZ * chunkX, SZ * chunkY, // destination corners
                srcX, srcY, srcX + chunkX * SZ, srcY + chunkY * SZ, // src corners
                null);
        if (mp == null)
            mp = new HashMap<Point, BufferedImage>();
        mp.put(pt, im);
        slicedImages.put(src, mp);
        return im;
    }

//    private Map<Image, Image> scaledImages = new HashMap<Image, Image>();
    private static Map<Image, Map<Point, BufferedImage>> slicedImages = new HashMap<Image, Map<Point,BufferedImage>>();
//
    private static Image loadResourceImage(String path) {
        String loc = "img/" + path;
        java.net.URL imgURL = WarViewer.class.getResource("/" + path);
        if (imgURL == null)
            imgURL = WarViewer.class.getResource("/" + loc);
        if (imgURL != null)
            return new ImageIcon(imgURL).getImage();
        else
            return new ImageIcon(path).getImage();
    }
    
    public static void center(Graphics2D g, BufferedImage im, double x, double y) {
        g.drawImage(im, (int) x + im.getWidth() / 2, (int) y + im.getHeight() / 2, null);
    }

//  private Image getScaledImage(Image image, int sz) {
//      Image im = scaledImages.get(image);
//      if (im != null && im.getHeight(null) == sz)
//          return im;
//      BufferedImage thumbImage = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_ARGB);
//      Graphics2D graphics2D = thumbImage.createGraphics();
//      graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//      graphics2D.drawImage(image, 0, 0, sz, sz, null);
//      scaledImages.put(image, thumbImage);
//      return thumbImage;
//  }
}
