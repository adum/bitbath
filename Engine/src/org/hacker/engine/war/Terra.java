package org.hacker.engine.war;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.hacker.engine.war.unit.City;
import org.hacker.engine.war.unit.Unit;

/**
 * this class represents the world environment: terrain, cities, units, etc
 */
public class Terra {
	/** size of our world */
    public final double dimX, dimY;
    /** all units for all factions, includes cities and neutrals */
    public List<Unit> units = new LinkedList<Unit>();
    public final Random r;
    protected int curID = 0;
    public List<Faction> factions;
    private List<Unit> womb = new LinkedList<Unit>();
    List<Terrain> terrain = new ArrayList<Terrain>(); // a series of boxes
	private VisualsListener visListener;

    public Terra(int dx, int dy, int numCities, Random r, List<Faction> factions, VisualsListener visListener) {
        this.dimX = dx;
        this.dimY = dy;
        this.r = r;
        this.factions = factions;
        this.visListener = visListener;

        // make a random world
        genCities(numCities);
        genTerrain(2, Terrain.FOREST);
        genTerrain(2, Terrain.SWAMP);
    }

    /**
     * create random terrain
     */
    private void genTerrain(int num, int terrainType) {
        for (int i = 0; i < num; i++) {
            double szx = dimX * 0.1 + r.nextDouble() * dimX * 0.3;
            double szy = dimY * 0.1 + r.nextDouble() * dimY * 0.3;
            double x = r.nextDouble() * (dimX - szx);
            double y = r.nextDouble() * (dimY - szy);
            terrain.add(new Terrain(terrainType, new Rectangle2D.Double(x, y, szx, szy)));
        }
    }

    /**
     * create random cities, not too close
     */
    private void genCities(int num) {
        double lim = 0.1;
        double nb = (1.0 / Math.sqrt(num)) * 0.3;
        List<City> oc = new ArrayList<City>();
        loop: for (int i = 0; i < num; i++) {
            double x = lim * dimX + r.nextDouble() * (dimX * (1.0 - lim * 2));
            double y = lim * dimY + r.nextDouble() * (dimY * (1.0 - lim * 2));
            // make sure not too close
            for (int j = 0; j < i; j++) {
                if (Math.hypot(x - oc.get(j).x, y - oc.get(j).y) < nb * dimX) {
                    i--;
                    continue loop;
                }
            }
            City city = new City(++curID, x, y);
            oc.add(city);
            units.add(city);
        }
    }
    
    /**
     * for creating unique ids
     */
    public int getNextID() {
        return ++curID;
    }

    /**
     * prepare to add a unit, but don't commit yet. must incubate.
     */
    public void addUnit(Unit nunit) {
        womb.add(nunit);
    }

    /**
     * emerge from womb
     */
    public void addWombUnits() {
        for (Unit unit : womb) {
            units.add(unit);
        }
        womb.clear();
    }

    /**
     * all dead units removed. removes from factions' lists too.
     */
    public void bringOutYourDead() {
        Iterator<Unit> it = units.iterator();
        while (it.hasNext()) {
            Unit unit = it.next();
            if (unit.isDead()) {
                it.remove();
                unit.getFaction().units.remove(unit);
                if (visListener != null) visListener.deadUnit(unit);
            }
        }
    }

    /**
     * what type of terrain at this loc?
     */
	public int getTerrain(double x, double y) {
		int t = Terrain.GRASS;
		// later boxes override earlier ones
		for (Terrain ter : terrain) {
			if (ter.contains(x, y))
				t = ter.index;
		}
		return t;
	}
}
