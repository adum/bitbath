package org.hacker.engine.war;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hacker.engine.GameModel;
import org.hacker.engine.Viewer;
import org.hacker.engine.war.unit.Ambulatory;
import org.hacker.engine.war.unit.Artillery;
import org.hacker.engine.war.unit.City;
import org.hacker.engine.war.unit.Grunt;
import org.hacker.engine.war.unit.Hovercraft;
import org.hacker.engine.war.unit.Unit;
import org.hacker.engine.war.vis.Effect;
import org.hacker.engine.war.vis.FlipBook;
import org.hacker.engine.war.vis.Laser;
import org.hacker.engine.war.vis.Pix;
import org.hacker.engine.war.vis.Projectile;

/**
 * visual representation of a BitBath game, designed to work with Java 2D
 */
public class WarViewer implements Viewer, VisualsListener {
    private Color[] factionCols = { new Color(0xA7341D), new Color(0x40760C), new Color(0xC8DE38), new Color(0xC672F6) };
    private double lastInitScale = 0;
    private BufferedImage bgImg;
    private List<Effect> effects = new LinkedList<Effect>();
    Random r = new Random();

        private int[][] tval;
        private Bubble bubble;
        
        /** swamp bubble */
        class Bubble {
                int x, y;
                int stage;
        }
        
        int frame = 0;

    public void draw(GameModel model, Graphics2D g, int dx, int dy, boolean showInfo) {
        WarModel tm = (WarModel) model;
        Terra terra = tm.terra;
        // chop off slop
        dx -= dx % Pix.SZ;
        dy -= dy % Pix.SZ;
        
        double scale = Math.min(dx / terra.dimX, dy / terra.dimY);

        frame++;
        
        initGfx(g, terra, scale, (int) (terra.dimX * scale), (int) (terra.dimY * scale));
        
        if (bubble == null) {
                bubble = new Bubble();
                bubble.x = r.nextInt(tval.length);
                bubble.y = r.nextInt(tval[0].length);
                if (tval[bubble.x][bubble.y] != Terrain.SWAMP)
                        bubble = null;
        }

        g.drawImage(bgImg, 0, 0, null);
        
        if (bubble != null) {
                if (bubble.stage == 21) {
                        bubble = null;
                }
                else {
                        drawBubble(g, bubble);
                        if (frame % 8 == 0)
                                bubble.stage++;
                }
        }

        synchronized (terra) {
            drawUnits(terra, terra.units, scale, g, showInfo);
        }
        
        drawEffects(g, scale);
    }

    private void drawBubble(Graphics2D g, Bubble b) {
        int imx = b.stage % 7;
        int imy = b.stage / 7;
        BufferedImage im = Pix.getSlicedImage(new Point(imx, imy), Pix.imSwampBubble, 1, 2, 1, 0);
        g.drawImage(im, bubble.x * Pix.SZ, (bubble.y - 1) * Pix.SZ, null);
    }

        private void drawEffects(Graphics2D g, double scale) {
        synchronized (effects) {
            Iterator<Effect> it = effects.iterator();
            while (it.hasNext()) {
                Effect effect = it.next();
                effect.draw(g, scale);
                if (effect.isDone())
                    it.remove();
            }
        }
    }
        
        class Anim {
                public Anim(int frame) {
                        frameStart = frame;
                }

                int frameStart;
        }
        
        Map<City, Anim> cityFlashAnim = new HashMap<City, Anim>();

    private void drawUnits(Terra terra, List<Unit> units, double scale, Graphics2D g, boolean showInfo) {
        for (Unit unit : units) {
            Composite origComposite = g.getComposite();

            double x = unit.x * scale;
            double y = unit.y * scale;
            // choose color
            Faction faction = unit.getFaction();
            if (faction == null)
                g.setColor(Color.LIGHT_GRAY);
            else {
                g.setColor(factionCols[faction.index]);
            }
            int hp_off = Pix.SZ / 2 + 1;
            if (unit instanceof City) {
                City city = (City) unit;
                BufferedImage im;
                if (faction == null) {
                    im = Pix.getSlicedImage(new Point(0, 0), Pix.imSrcBaseNeutral, 3, 3, 0, 1);
                }
                else {
                        int stg = 0;
                        if (cityFlashAnim.containsKey(city)) {
                                Anim anim = cityFlashAnim.get(city);
                                stg = (frame - anim.frameStart) / 2;
                                if (stg >= 7) {
                                        // over
                                        stg = 0;
                                        cityFlashAnim.remove(city);
                                }
                        }
                        if (city.getHp() < city.maxHP / 10.0)
                                stg = 4;
                        int imx = stg % 4;
                        int imy = stg / 4;
                    im = Pix.getSlicedImage(new Point(imx, imy), Pix.imSrcBase[faction.index], 3, 3, 0, 1);
                }
                g.drawImage(im, (int) x - im.getWidth() / 2, (int) y - Pix.SZ * 3 / 2, null);
                hp_off = Pix.SZ;
            }
            else {
                double ang = 0;
                if (unit instanceof Ambulatory)
                    ang = ((Ambulatory) unit).getMoveAngle();
                ang = (ang + Math.PI * 2.0) % (Math.PI * 2.0);
                ang -= Math.PI / 2.0;
                Image im = null;
                if (unit instanceof Grunt)
                        im = Pix.imSrcTank[faction.index];
                else if (unit instanceof Hovercraft)
                        im = Pix.imSrcBoat[faction.index];
                else if (unit instanceof Artillery)
                        im = Pix.imSrcArtil[faction.index];
                AffineTransform xform = new AffineTransform();
                double px = x - 20 / 2;
                double py = y - 20 / 2;
                xform.rotate(ang, px + Pix.SZ / 2, py + Pix.SZ / 2);
                xform.translate(px, py);
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                g.drawImage(im, xform, null);
            }
            
            // draw hp bar
            if (unit.getHp() < unit.maxHP) {
                double frac = unit.getHp() / unit.maxHP;
                int hp_sz = 16;
                AlphaComposite alphaLines = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
                g.setComposite(alphaLines);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect((int) x - hp_sz / 2, (int) y + hp_off, hp_sz, 2);

                g.setColor(Color.GREEN);
                g.fillRect((int) x - hp_sz / 2, (int) y + hp_off, (int) (hp_sz * frac), 2);
            }
            
            g.setComposite(origComposite);
            if (showInfo) {
                                double vision = unit.getVision(terra.getTerrain(unit.x, unit.y));
                                int visRad = (int) (vision * scale);
                g.drawImage(Pix.imCircle, (int) (unit.x * scale - visRad), (int) (unit.y * scale - visRad), visRad * 2, visRad * 2, null);
                if (true) {
                                        if (unit.getWeapon() != null) {
                                                g.setColor(Color.YELLOW);
                                                double range = unit.getWeapon().getRange();
                                                int rangeRad = (int) (range * scale);
                                                g.drawOval((int) (unit.x * scale - rangeRad), (int) (unit.y * scale - rangeRad), rangeRad * 2, rangeRad * 2);
                                        }
                                        g.setComposite(origComposite);
                }
                        }            
        }
    }

    private void initGfx(Graphics2D gmain, Terra terra, double scale, int dx, int dy) {
        if (lastInitScale == scale) return;
        lastInitScale = scale;

        bgImg = gmain.getDeviceConfiguration().createCompatibleImage(dx, dy);
        Graphics2D g = (Graphics2D) bgImg.getGraphics();
        
        // gradient background
        GradientPaint gp = new GradientPaint(50.0f, 0.0f, new Color(0x33dd33)
                , 50.0f, dy, new Color(0x33bb33));
        g.setPaint(gp);
        g.fillRect(0, 0, dx, dy);
        
        Image grass = Pix.getSlicedImage(new Point(1, 1), Pix.imSrcGrass, 1, 1, 1, 1);
        int sx = dx / Pix.SZ;
        int sy = dy / Pix.SZ;
        
        tval = new int[sx][sy];
                for (int x = 0; x < sx; x++) {
                        for (int y = 0; y < sy; y++) {
                                tval[x][y] = Terrain.GRASS;
                        }
        }
        // apply terrain from terra
        for (int i = 0; i < terra.terrain.size(); i++) {
            Terrain terrain = terra.terrain.get(i);
            Double rec = terrain.bounds;
            int ax = (int) ((rec.x) / terra.dimX * sx + 0.5);
            int ay = (int) ((rec.y) / terra.dimY * sy + 0.5);
            int bx = (int) ((rec.x + rec.width) / terra.dimX * sx + 0.5);
            int by = (int) ((rec.y + rec.height) / terra.dimY * sy + 0.5);
            for (int x = ax; x < bx & x < sx; x++) {
                for (int y = ay; y < by && y < sy; y++) {
                        tval[x][y] = terrain.index;
                }
            }
        }
        
        // make mountainous border
        for (int x = 0; x < sx; x++) {
                tval[x][0] = Terrain.MOUNTAIN;
                tval[x][sy - 1] = Terrain.MOUNTAIN;
        }
        for (int y = 0; y < sy; y++) {
                tval[0][y] = Terrain.MOUNTAIN;
                tval[sx - 1][y] = Terrain.MOUNTAIN;
        }
        
        // draw terrain
        for (int x = 0; x < sx; x++) {
                        for (int y = 0; y < sy; y++) {
                                boolean chk = (x != 0 && y != 0 && x != sx - 1 && y != sy - 1);
                                Image im;
                                switch (tval[x][y]) {
                                        case Terrain.GRASS: default:
                                                im = grass;
                                if (r.nextInt(27) == 0) {
                                    im = Pix.getSlicedImage(new Point(r.nextInt(3), 0), Pix.imSrcGrassCrater, 1, 1, 1, 1);
                                }
                                if (chk) {
                                        im = checkBorders(x, y, im, Terrain.FOREST, Pix.imSrcTree2Grass);
                                        im = checkBorders(x, y, im, Terrain.SWAMP, Pix.imSrcSwamp2Grass);
                                }
                                                break;
                                        case Terrain.FOREST:
                                                im = Pix.getSlicedImage(new Point(1, 1), Pix.imSrcTree2Grass, 1, 1, 1, 1);
                                                break;
                                        case Terrain.SWAMP:
                                                im = Pix.getSlicedImage(new Point(1, 1), Pix.imSrcSwamp2Grass, 1, 1, 1, 1);
                                if (r.nextInt(37) == 0) {
                                    im = Pix.imSwampPlant;
                                }
                                                break;
                                        case Terrain.MOUNTAIN:
                                                im = checkMountain(x, y, sx, sy);
                                                break;
                                }
                g.drawImage(im, x * Pix.SZ, y * Pix.SZ, null);
            }
        }
    }

    private Image checkMountain(int x, int y, int sx, int sy) {
        Point p = null;
        if (x == 0) {
                if (y == 0)
                        p = new Point(0, 0);
                else if (y == sy - 1)
                        p = new Point(0, 2);
                else
                        p = new Point(0, 1);
        }
        else if (x == sx - 1) {
                if (y == 0)
                        p = new Point(2, 0);
                else if (y == sy - 1)
                        p = new Point(2, 2);
                else
                        p = new Point(2, 1);
        }
        else if (y == 0)
                p = new Point(1, 0);
        else
                p = new Point(1, 2);
                return Pix.getSlicedImage(p, Pix.imSrcGrass2Mountain, 1, 1, 1, 1);
        }

        // when tiles transition, we use special images
        private Image checkBorders(int x, int y, Image im, int t, Image src) {
                if (tval[x + 1][y] == t)
                        im = Pix.getSlicedImage(new Point(0, 1), src, 1, 1, 1, 1);
                else if (tval[x - 1][y] == t)
                        im = Pix.getSlicedImage(new Point(2, 1), src, 1, 1, 1, 1);
                else if (tval[x][y + 1] == t)
                        im = Pix.getSlicedImage(new Point(1, 0), src, 1, 1, 1, 1);
                else if (tval[x][y - 1] == t)
                        im = Pix.getSlicedImage(new Point(1, 2), src, 1, 1, 1, 1);

                else if (tval[x + 1][y + 1] == t)
                        im = Pix.getSlicedImage(new Point(0, 0), src, 1, 1, 1, 1);
                else if (tval[x - 1][y + 1] == t)
                        im = Pix.getSlicedImage(new Point(2, 0), src, 1, 1, 1, 1);
                else if (tval[x - 1][y - 1] == t)
                        im = Pix.getSlicedImage(new Point(2, 2), src, 1, 1, 1, 1);
                else if (tval[x + 1][y - 1] == t)
                        im = Pix.getSlicedImage(new Point(0, 2), src, 1, 1, 1, 1);
                return im;
        }

    public void newExplosion(double x, double y) {
        FlipBook expl = new FlipBook(x, y, 9, Pix.imSrcExplosion);
        synchronized (effects) {
            effects.add(expl);
        }
    }

    public void newProjectile(double startX, double startY, double destX, double destY) {
        Projectile proj = new Projectile(startX, startY, Pix.imBullet2, destX, destY, 0.4);
        synchronized (effects) {
            effects.add(proj);
        }
    }

    public void newLaser(double startX, double startY, double destX, double destY, Color color) {
        Laser laser = new Laser(startX, startY, destX, destY, color);
        synchronized (effects) {
            effects.add(laser);
        }
    }

    public void newExplosionSmall(double x, double y) {
        FlipBook expl = new FlipBook(x - 0.1 + r.nextDouble() * 0.2, y - 0.1 + r.nextDouble() * 0.2, 7, Pix.imSrcExplosionSmall);
        synchronized (effects) {
            effects.add(expl);
        }
    }

        public void newWreck(double x, double y) {
        Graphics2D g = (Graphics2D) bgImg.getGraphics();
        g.drawImage(Pix.imWreck, (int) (x * lastInitScale) - 8, (int) (y * lastInitScale) - 6, null);
        }

        public void newCityBuild(City city) {
                cityFlashAnim.put(city, new Anim(frame));
        }

        public void deadUnit(Unit unit) {
        }

    public void changedAllegiance(City city) {
    }
}
