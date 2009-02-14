package org.hacker.engine.war;

import org.hacker.engine.war.order.Order;

/**
 * bridge between bots and brains
 */
public interface WarSpinalCord {
    /**
     * for normal units
     * @param radioOut 
     */
    public Order think(int[][] radio, double dx, double dy, double x, double y, boolean moving, int terrain, int id, int ourType,
                double hp, double maxHP, double range, double time,
                double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] radioOut);

    /**
     * for cities to think
     */
    public int build(double dx, double dy, double x, double y, int terrain, int id, int buildItem,
                double hp, double maxHP, double time,
                double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] radioOut);

    /**
     * @return how many Java bytecode instructions we used on our last think, if known
     */
        public int getLastInstructionCount();
}
