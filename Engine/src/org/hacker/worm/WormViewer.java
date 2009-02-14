
package org.hacker.worm;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.List;

import org.hacker.engine.Bot;
import org.hacker.engine.GameModel;
import org.hacker.engine.Viewer;
import org.hacker.worm.WormModel.Path;

public class WormViewer implements Viewer {
    private Color[] wormCols = { new Color(0xA7341D), new Color(0x40760C), new Color(0xC8DE38), new Color(0xC672F6) };
    private Color[] wormEdgeCols;
    {
        wormEdgeCols = new Color[wormCols.length];
        for (int i = 0; i < wormCols.length; i++)
            wormEdgeCols[i] = wormCols[i].brighter();
    }
    private int nbots;
    
    public WormViewer(int nbots) {
        this.nbots = nbots;
    }

    public void draw(GameModel model, Graphics2D g, int dx, int dy, boolean showInfo) {
        WormModel tm = (WormModel) model;
        int sz = 8;
        
        sz = Math.min(dx / tm.dx, dy / tm.dy);
        initGfx(g, sz, tm.dx * sz, tm.dy * sz);
        
        g.drawImage(bgImg, 0, 0, null);

        for (int x = 0; x < tm.dx; x++)
            for (int y = 0; y < tm.dy; y++) {
                int v = tm.board[x][y];
                if (v == 0)
                    continue;
                else if (v == 1) {
                    continue;
                }
                else {
                    g.setColor(wormCols[v - 2]);
                }
                g.fillRect(x * sz, y * sz, sz, sz);
            }
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        List<Bot> bots = tm.getBots();
        for (Bot bot : bots) {
            WormBot tb = (WormBot) bot;
            if (!bot.isAlive())
                g.setColor(Color.DARK_GRAY);
            else
                g.setColor(wormEdgeCols[tm.board[tb.x][tb.y] - 2]);
            g.fillOval(tb.x * sz, tb.y * sz, sz, sz);
        }
        
        // paths
        Path[] paths = tm.paths;
//        int h = sz / 2;
        for (Path path : paths) {
            List<Point> moves = path.path;
            for (int i = 0; i < moves.size() - 1; i++) {
                boolean[] blk = new boolean[4];
                Point a = moves.get(i);
                Point b = moves.get(i + 1);
                unblock(blk, a, b);
                if (i > 0) {
                    Point pre = moves.get(i - 1);
                    unblock(blk, a, pre);
                }
                int v = tm.board[a.x][a.y];
                g.setColor(wormEdgeCols[v - 2]);
                if (!blk[1])
                    g.drawLine(a.x * sz, a.y * sz, a.x * sz + sz, a.y * sz);
                if (!blk[2])
                    g.drawLine(a.x * sz, a.y * sz, a.x * sz, a.y * sz + sz);
//                g.setColor(wormCols[v - 2].darker());
                if (!blk[0])
                    g.drawLine(a.x * sz + sz, a.y * sz, a.x * sz + sz, a.y * sz + sz);
                if (!blk[3])
                    g.drawLine(a.x * sz, a.y * sz + sz, a.x * sz + sz, a.y * sz + sz);
            }
        }
    }

    private void unblock(boolean[] blk, Point a, Point b) {
        int dx = b.x - a.x;
        int dy = b.y - a.y;
        if (dx == 1) blk[0] = true;
        else if (dx == -1) blk[2] = true;
        else if (dy == 1) blk[3] = true;
        else if (dy == -1) blk[1] = true;
    }

    private int lastInitSz = 0;
//    private BufferedImage bgpat;
    private BufferedImage bgImg;

    private void initGfx(Graphics2D gmain, int sz, int dx, int dy) {
        if (lastInitSz == sz) return;
        lastInitSz = sz;
        int h = sz / 2;
        h = 0;

        bgImg = gmain.getDeviceConfiguration().createCompatibleImage(dx, dy);
        Graphics2D g = (Graphics2D) bgImg.getGraphics();
        
        // gradient background
        GradientPaint gp = new GradientPaint(50.0f, 0.0f, new Color(getTopGradientCol())
                , 50.0f, dy, new Color(getBotGradientCol()));
        g.setPaint(gp);
        g.fillRect(0, 0, dx, dy);
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.04f));
        drawCurve(dx, dy, g);
        
        // grid lines
        AlphaComposite ac =
           AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.12f);
        g.setComposite(ac);
        g.setColor(new Color(0xE7E2FF));
        for (int x = 0; x < dx / sz; x++) {
            g.drawLine(x * sz + h, 0, x * sz + h, dy);
        }
        for (int y = 0; y < dy / sz; y++) {
            g.drawLine(0, y * sz + h, dx, y * sz + h);
        }
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
        Color c0 = new Color(0x222222);
        Color c1 = new Color(0x555555);

        // corners
        g.setColor(c0);
        g.fillRect(0, 0, sz, sz);
        g.fillRect(dx - sz, 0, sz, sz);
        g.fillRect(dx - sz, dy - sz, sz, sz);
        g.fillRect(0, dy - sz, sz, sz);
        // walls
        // vert top
        gp = new GradientPaint(0.0f, sz, c0, 0.0f, dy / 2, c1);
        g.setPaint(gp);
        g.fillRect(0, sz, sz, dy / 2 - sz);
        g.fillRect(dx - sz, sz, sz, dy / 2 - sz);
        // vert bot
        gp = new GradientPaint(0.0f, dy / 2, c1, 0.0f, dy - sz, c0);
        g.setPaint(gp);
        g.fillRect(0, dy / 2, sz, dy / 2 - sz);
        g.fillRect(dx - sz, dy / 2, sz, dy / 2 - sz);
        // horiz left
        gp = new GradientPaint(sz, 0.0f, c0, dx / 2, 0.0f, c1);
        g.setPaint(gp);
        g.fillRect(sz, 0, dx / 2 - sz, sz);
        g.fillRect(sz, dy - sz, dx / 2 - sz, sz);
        // horiz right
        gp = new GradientPaint(dx / 2, 0.0f, c1, dx - sz, 0.0f, c0);
        g.setPaint(gp);
        g.fillRect(dx / 2, 0, dx / 2 - sz, sz);
        g.fillRect(dx / 2, dy - sz, dx / 2 - sz, sz);
    }
    
    protected int getBotGradientCol() {
        if (nbots == 4)
            return 0x8924F0;
        return 0x212ADB;
    }

    protected int getTopGradientCol() {
        if (nbots == 4)
            return 0x309ED7;
        return 0x7B80DB;
    }

    protected void drawCurve(int w, int h, Graphics2D g2) {
        {
            GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
            // p.moveTo(w*.2f, h*.25f);
            //
            // // adds a cubic curve to the path
            // p.curveTo(w*.4f, h*.5f, w*.6f, 0.0f, w*.8f, h*.25f);
            //
            // p.moveTo(w*.2f, h*.6f);
            //
            // // adds a quad curve to the path
            // p.quadTo(w*.5f, h*1.0f, w*.8f, h*.6f);

            p.moveTo(0, -0.2f);
            p.quadTo(w * .5f, h * 0.3f, w, h * 0.2f);
            p.lineTo(w, h * 0.6f);
            p.quadTo(w * .1f, h * -0.2f, 0, h * 0.7f);
            //        p.lineTo(0, h*0.7f);
            p.lineTo(0, -0.2f);

            g2.setColor(Color.WHITE);
            g2.fill(p);
        }
        
        if (nbots == 4) {
            GeneralPath p = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
            p.moveTo(0, 0.3f*h);
            p.quadTo(w*.5f, h*0.1f, w, h*0.2f);
            p.lineTo(w, h*0.9f);
            p.quadTo(w*.7f, h*0.1f, 0, h*0.4f);
            p.lineTo(0, 0.3f*h);

            g2.setColor(Color.WHITE);
            g2.fill(p);
        }
    }

}
