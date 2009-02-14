import java.util.Random;

public class Herd {
    public int orderType = 1; // always a move order
    public double destX, destY; // the move order will use these fields to communicate a destination
    Random r = new Random(); // help us randomize our actions

    public int[] radio = new int[4]; // we'll use this to communicate with other bots

        public static final int CITY = 0;
        public static final int GRUNT = 1;
        public static final int HOVERCRAFT = 2;
        public static final int ARTIL = 3;

        public Object think(double dx, double dy, double x, double y, boolean moving, int terrain, int ourID, int ourType, double hp, double maxHP,
                        double range, double time, double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] incomingRadio) {
                if (moving) return null;
                orderType = 1;

                // look for guidance
                for (int i = 0; i < incomingRadio.length; i++) {
                        int[] ir = incomingRadio[i];
                        if (ir[1] == 1) {
                                destX = ir[2];
                                destY = ir[3];
                                return this;
                        }
                }

                // wait for friend
                // first, count how many friendlies we have in the neighborhood
                int cnt = 0;
        for (int i = 0; i < objX.length; i++) {
                if (objFaction[i] != 0) continue; // not on our team
                if (objType[i] == CITY) continue; // forget cities
                cnt++;
        }
        // until we have 6, we just hang out. accumulate a herd.
        if (cnt < 6) {
                orderType = 0; // so we don't try moving anywhere
                return this;
        }

        // okay, we have enough people nearby, let's start a herd stampede!
        // choose a random destination
                destX = r.nextDouble() * (dx);
                destY = r.nextDouble() * (dy);
                // we're going to tell people where we're going
                radio[0] = 1; // a little flag (read by other bots) to indicate that we're sending out a signal they should listen to
                // put the destination in there -- integer precision is good enough for this purpose
                radio[1] = (int) destX;
                radio[2] = (int) destY;
                
                return this;
        }

        public int build(double dx, double dy, double x, double y, int terrain, int id, int buildItem,
                double hp, double maxHP, double time,
                double[] objX, double[] objY, int[] objID, int[] objFaction, int[] objType, int[][] incomingRadio) {
                if (buildItem != 0) return 0;
                return 1 + r.nextInt(3);
        }
}
