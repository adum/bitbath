import java.util.Random;
public class Nero {
    public int orderType = 1; // always a move order
    public double destX, destY; // the move order will use these fields to communicate a destination
    Random r = new Random(); // help us randomize our actions
    
    public Object think(double dx, double dy, double x, double y, boolean moving, int terrain,
                int ourID, int ourType, double hp, double maxHP, double range, double time,
                double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] incomingRadio) {
        if (moving) return null; // no new orders until we arrive
        destX = r.nextDouble() * (dx); // choose random destination
        destY = r.nextDouble() * (dy);
        return this;
    }
    
    public int build(double dx, double dy, double x, double y, int terrain, int id, int buildItem,
                double hp, double maxHP, double time,
                double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] incomingRadio) {
                if (buildItem != 0) return 0; // if we're currently building, keep at it
        return 1 + r.nextInt(3); // choose randomly
    }
}
