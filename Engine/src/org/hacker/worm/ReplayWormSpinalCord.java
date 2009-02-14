package org.hacker.worm;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class ReplayWormSpinalCord implements WormSpinalCord {
    private Reader inp;

    public ReplayWormSpinalCord(Reader inp) {
        this.inp = inp;
    }

    public void addInfoProperties(Properties p) {
    }

    public int think(int dx, int dy, int[][] board, int x, int y, int dir, int[][] enemies) {
        try {
            int m = inp.read();
            switch (m) {
                case 'R': return 0;
                case 'U': return 1;
                case 'L': return 2;
                default: return 3;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
