
package org.hacker.worm;

import java.util.Properties;

public interface WormSpinalCord {

    public int think(int dx, int dy, int[][] board, int x, int y, int dir, int[][] enemies);

    public void addInfoProperties(Properties p);

}
