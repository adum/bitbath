package org.hacker.engine.war.replay;

import org.hacker.engine.war.WarSpinalCord;
import org.hacker.engine.war.order.BuildOrder;
import org.hacker.engine.war.order.Order;

/**
 * replays orders from saved replay
 */
public class ReplayWarSpinalCord implements WarSpinalCord {
	private ReplayReader replayReader;
	private boolean brokenWarning = false;

	public ReplayWarSpinalCord(ReplayReader replayReader) {
		this.replayReader = replayReader;
	}
	
	public Order think(int[][] radio, double dx, double dy, double x, double y, boolean moving, int terrain, int id, int ourType, double hp,
			double maxHP, double range, double time, double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType,
			int[][] radioOut) {
		if (time > replayReader.getTime() && replayReader.getTime() >= 0) {
			if (!brokenWarning) {
				System.out.println("reader broken at " + time + ", passed: " + replayReader.getTime());
				brokenWarning = true;
			}
		}
		if (!replayReader.hasUnitID(id)) return null;
		if (replayReader.getTime() == time) {
			// it's us
//			System.out.println("-> " + replayReader.getTime() + " - " +  time + ", unit: " + id);
			return replayReader.getOrder(id);
		}
		return null;
	}

	public int build(double dx, double dy, double x, double y, int terrain, int id, int buildItem, double hp, double maxHP, double time,
			double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] radioOut) {
		if (!replayReader.hasUnitID(id)) return -1;
//		System.out.println("#-> " + replayReader.getTime() + " - " +  time + ", unit: " + id);
		if (replayReader.getTime() == time) {
			// it's us
			return ((BuildOrder)replayReader.getOrder(id)).buildType;
		}
		return -1;
	}

	public int getLastInstructionCount() {
		// we don't care
		return 0;
	}
}
