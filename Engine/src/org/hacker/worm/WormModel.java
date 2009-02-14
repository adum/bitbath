package org.hacker.worm;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.hacker.engine.ArmyOfOneModel;
import org.hacker.engine.Bot;

public class WormModel extends ArmyOfOneModel {
    public static int[][] offsets = {{1, 0},{0, -1},{-1, 0},{0, 1}};
    public static char[] offNames = {'R', 'U', 'L', 'D'};

    public final int dx, dy;
    public int[][] board;
    public Path[] paths;
    
    public class Path {
        public List<Point> path = new ArrayList<Point>();
    }
    
    public WormModel(List<Bot> bots, long seed, BotPlacer placer) {
        super(bots, seed);
        dx = 64;
        dy = 38;
        board = new int[dx][dy];
        replay.append(dx).append(",").append(dy);
        
        // fill in borders
        for (int x = 0; x < dx; x++) {
            board[x][0] = 1;
            board[x][dy - 1] = 1;
        }
        for (int y = 0; y < dy; y++) {
            board[0][y] = 1;
            board[dx - 1][y] = 1;
        }

        paths = new Path[bots.size()];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = new Path();
        }

//        for (Bot bot : bots) {
        boolean randomStarts = bots.size() > 2;
        for (int bindex = 0; bindex < bots.size(); bindex++) {
            WormBot tb = (WormBot) bots.get(bindex);
            // different methods of placing a bot
            if (placer != null) {
                Point p = placer.placeBot(bindex);
                tb.x = p.x;
                tb.y = p.y;
            }
            else if (randomStarts) {
                int spc = 4;
                placeLoop: while (true) {
                    tb.x = spc + r.nextInt(dx - spc * 2);
                    tb.y = spc + r.nextInt(dy - spc * 2);
                    for (int cind = 0; cind < bindex; cind++) {
                        WormBot otherBot = (WormBot) bots.get(cind);
                        if (Math.abs(otherBot.x - tb.x) < spc || Math.abs(otherBot.y - tb.y) < spc)
                            continue placeLoop;
                    }
                    break;
                }
            }
            else {
                tb.x = dx / 2;
                tb.y = dy / 2 - dy / 4 + ((bindex == 0) ? 0 : dy / 2);
                tb.dir = (bindex == 0) ? 3 : 1;
            }
            // set it down
            board[tb.x][tb.y] = bindex + 2;
            paths[bindex].path.add(new Point(tb.x, tb.y));
            replay.append(",").append(tb.x).append(",").append(tb.y);
        }
        replay.append(",");
    }

    @Override
    protected void moveBot(Bot bot, int index) {
        WormBot tb = (WormBot) bot;
        int[][] enemies = createEnemyList(index);
        tb.think(dx, dy, board, enemies);
        replay.append(offNames[tb.dir]);
        paths[index].path.add(new Point(tb.x, tb.y));
        if (board[tb.x][tb.y] != 0) {
            tb.seppuku();
            System.out.println("bot dead: " + tb);
        }
        else
            board[tb.x][tb.y] = index + 2;
    }

    /**
     * returns array of locations of enemy bots (excludes self)
     */
    private int[][] createEnemyList(int index) {
        int i = index;
        
        // create list of enemies
        List<WormBot> enemies = new ArrayList<WormBot>();
        while (true) {
            i = (i + 1) % bots.size();
            if (i == index) break;
            WormBot tb = (WormBot) bots.get(i);
            if (tb.isAlive())
                enemies.add(tb);
        }
        
        // convert to array
        int[][] positions = new int[enemies.size()][2];
        for (int j = 0; j < enemies.size(); j++) {
            WormBot tb = enemies.get(j);
            positions[j][0] = tb.x;
            positions[j][1] = tb.y;
        }
        
        return positions;
    }

    @Override
    public String toString() {
        String s = "";
        for (int y = 0; y < dy; y++) {
            for (int x = 0; x < dx; x++) {
                if (board[x][y] == 0)
                    s += ".";
                else if (board[x][y] == 1)
                    s += "#";
                else
                    s += Integer.toString(board[x][y]);
            }
            s += "\n";
        }
        return s;
    }
}
