package corners;

import java.util.Random;

public class FourCorners {
	Random r = new Random();
	Dest[] corners;

	public Object think(double dx, double dy, double x, double y, boolean moving, int terrain, int ourID, double hp, double maxHP,
			double range, double time, double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] incomingRadio) {
		if (moving)
			return null;
		if (corners == null) {
			corners = new Dest[4];
			corners[0] = new Dest(2, 2);
			corners[1] = new Dest(dx - 2, 2);
			corners[2] = new Dest(2, dy - 2);
			corners[3] = new Dest(dx - 2, dy - 2);
		}
		return corners[r.nextInt(4)];
	}

	public int build(double dx, double dy, double x, double y, int terrain, int id, int buildItem, double hp, double maxHP, double time,
			double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] incomingRadio) {
		if (buildItem != 0)
			return 0;
		return 1 + r.nextInt(3);
	}
}
