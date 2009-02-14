package org.hacker.worm;

import java.lang.reflect.Method;
import java.util.Properties;

public class NativeWormSpinalCord implements WormSpinalCord {
    private final Object brain;
    private Method thinkMethod;
	private String className;

    public NativeWormSpinalCord(final Object brain, String className) {
        super();
        this.brain = brain;
        this.className = className;
        
        // find the method -- just go by name
        Method[] methods = brain.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().equals("think")) {
                thinkMethod = m;
                break;
            }
        }
        if (thinkMethod == null)
            throw new IllegalArgumentException("no 'think' method found");
    }

    @Override
	public String toString() {
		return className;
	}

	public int think(int dx, int dy, int[][] board, int x, int y, int dir, int[][] enemies) {
        int[][] board2 = new int[dx][dy];
        for (int a = 0; a < dx; a++)
            for (int b = 0; b < dy; b++)
                board2[a][b] = board[a][b];
        Object[] args = { dx, dy, board2, x, y, dir, enemies };
        try {
            Object object = thinkMethod.invoke(brain, args);
            return (Integer) object;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addInfoProperties(Properties p) {
    }
}
