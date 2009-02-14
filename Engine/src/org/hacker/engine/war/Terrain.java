package org.hacker.engine.war;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

/**
 * represents a block of terrain of a given type
 */
public class Terrain {
    public static final int MOUNTAIN = -1; // not really used
    public static final int GRASS = 0;
    public static final int FOREST = 1;
    public static final int SWAMP = 2;
    
    public final int index;
    public final Rectangle2D.Double bounds;

    public Terrain(final int index, final Double bounds) {
        this.index = index;
        this.bounds = bounds;
    }

	public boolean contains(double x, double y) {
		return bounds.contains(x, y);
	}
}
