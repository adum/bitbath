import java.util.Random;

public class Genghis {
    public int orderType = 1; // always a move order
    public double destX, destY; // the move order will use these fields to communicate a destination
    Random r = new Random(); // help us randomize our actions
    
    public Object think(double dx, double dy, double x, double y, boolean moving, int terrain,
                int ourID, int ourType, double hp, double maxHP, double range, double time,
                double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] incomingRadio) {
        if (moving) return null; // don't interrupt
        
        // look for closest enemy or neutral city
        double best = 100000000;
        boolean found = false;
        for (int i = 0; i < objX.length; i++) {
                if (objFaction[i] == 0)
                        continue; // our team -- not interesting
                double d = (x - objX[i]) * (x - objX[i]) + (y - objY[i]) * (y - objY[i]);
                if (d > best)
                        continue; // we've already seen something closer
                best = d;
                destX = objX[i];
                destY = objY[i];
                found = true;
        }
        if (found)
                return this;
        
        // we found nothing -- just choose a random destination
        destX = r.nextDouble() * (dx);
        destY = r.nextDouble() * (dy);
        return this;
    }
    
    public int build(double dx, double dy, double x, double y, int terrain, int id, int buildItem,
                double hp, double maxHP, double time,
                double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] incomingRadio) {
                if (buildItem != 0) return 0;
        return 1 + r.nextInt(3);
    }
}
