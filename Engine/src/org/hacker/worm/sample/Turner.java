
package org.hacker.worm.sample;

import java.util.Random;

public class Turner {
    public static int[][] offsets = {{1, 0},{0, -1},{-1, 0},{0, 1}};
    Random r = new Random();

    public int think(int dx, int dy, int[][] board, int x, int y, int dir, int[][] enemies) {
//        int[][] offsets = {{1, 0},{0, -1},{-1, 0},{0, 1}};
        int nx = x + offsets[dir][0];
        int ny = y + offsets[dir][1];
        if (board[nx][ny] != 0) {
            if (r.nextBoolean())
                return (dir + 1) % 4;
            else
                return (dir + 3) % 4;
        }
        return dir;
    }

}
