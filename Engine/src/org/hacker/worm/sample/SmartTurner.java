
package org.hacker.worm.sample;

import java.util.Random;

public class SmartTurner {
    public static int[][] offsets = {{1, 0},{0, -1},{-1, 0},{0, 1}};
    Random r = new Random();

    public int think(int dx, int dy, int[][] board, int x, int y, int dir, int[][] enemies) {
        int nx = x + offsets[dir][0];
        int ny = y + offsets[dir][1];
        if (board[nx][ny] != 0) {
            if (r.nextBoolean())
                dir = (dir + 1) % 4;
            else
                dir = (dir + 3) % 4;
            int cnt = 0;
            while (++cnt < 4) {
                nx = x + offsets[dir][0];
                ny = y + offsets[dir][1];
                if (board[nx][ny] == 0) break;
                dir = (dir + 1) % 4;
            }
        }
        return dir;
    }
}
