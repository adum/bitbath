package org.hacker.engine.war;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.hacker.engine.GameModel;
import org.hacker.engine.war.replay.ReplayOutputStream;
import org.hacker.engine.war.unit.City;
import org.hacker.engine.war.unit.Grunt;
import org.hacker.engine.war.unit.Unit;

public class WarModel extends GameModel {
        /** who's fighting */
    protected List<Faction> factions = new LinkedList<Faction>();
    
    /** current game time */
    protected double time = 0;
    
    /** instructions a faction can think for each round */
    private int thinkInstructions = 100000;
    /** time progresses this much each step */
    private double stepDistance = 0.1;

    /** when games must end */
    private final double END_OF_TIME = 6000;
    /** if no city captured in this window, game ends */
    private final double END_WITHOUT_CAPTURE = 2000;
    /** if no order given in this window, game ends */
    private final double END_WITHOUT_ORDER = 1000;

    /** we move this many steps in our model for each time we let the bots think */
    public static final int THINK_EVERY = 4;
    /** where we are in think_every */
    private int thinkStage = 0;
    /** the world */
    public final Terra terra;
    /** for the visual display, listens to visual events */
    private VisualsListener visListener;
    /** we write out to this our replay */
        private ReplayOutputStream dos;

    public WarModel(long seed, List<WarSpinalCordFactory> partisans, VisualsListener visListener, DataOutputStream dos) throws Exception {
        super(seed);
        
        this.dos = new ReplayOutputStream(dos);
        dos.writeInt(0xDEAF1337);
        dos.writeLong(seed);
        this.visListener = visListener;
        terra = new Terra(50, 50, 7, r, factions, visListener);
        
        genFactions(partisans);
    }

    private void genFactions(List<WarSpinalCordFactory> partisans) {
        for (int i = 0; i < partisans.size(); i++) {
            Faction fac = new Faction(i, partisans.get(i), dos);
            factions.add(fac);
            
            // give them a starting city
            City city = (City) terra.units.get(i);
            fac.addUnit(city);
            
            // give them a starting unit too. how bout a grunt?
            Grunt grunt = new Grunt(terra.getNextID(), city.x, city.y);
            fac.addUnit(grunt);
            terra.addUnit(grunt);
        }
    }

    @Override
    public void step() {
        synchronized (terra) {
            terra.addWombUnits();
            
            // give factions a chance to think
            if (thinkStage == 0) {
                    for (Faction fac : factions) {
                        fac.think(thinkInstructions, terra, time);
                    }
            }
            thinkStage = (thinkStage + 1) % THINK_EVERY;

            // move things around etc
            Iterator<Unit> it = terra.units.iterator();
            while (it.hasNext()) {
                Unit unit = it.next();
                unit.advance(stepDistance, terra, visListener);
            }

            // a chance for the dead to leave
            terra.bringOutYourDead();
        }
        time = (time * 10.0 + 1.0) / 10.0; // weird math to avoid weird floating point rounding errors, which hoses replays
//        double dd = 0.3;
//        System.out.println("dd: " + dd);
//        System.out.println("tm: " + time);
    }

    @Override
    public Object findWinner() {
        int live = 0;
        Object winner = null;
        for (Faction fac : factions) {
                if (fac.alive()) {
                        live++;
                        winner = fac.getSpinalCordFactory();
                }
                else {
                        if (elim_list.size() == 0)
                                elim_list.add(fac.getSpinalCordFactory()); //FIXME for > 2 players
                }
        }
        if (live > 1) {
                // maybe a winner through other means?
                boolean preemie = false;
                if (time > END_OF_TIME) {
                        System.out.println("end of universe at " + END_OF_TIME);
                        preemie = true;
                }
            for (Faction fac : factions) {
                if (time - fac.getLastCaptureCity() > END_WITHOUT_CAPTURE) {
                        System.out.println("end on no city capture at " + time + ", last at " + fac.getLastCaptureCity());
                        preemie = true;
                }
                }
            for (Faction fac : factions) {
                if (time - fac.getLastGiveOrder() > END_WITHOUT_ORDER) {
                        System.out.println("end on no order at " + time + ", last at " + fac.getLastGiveOrder());
                        preemie = true;
                }
                }
                if (preemie) {
                        System.out.println("picking winner from score");
                        // end of universe
                        Collections.sort(factions, new Comparator<Faction>() {
                                        public int compare(Faction o1, Faction o2) {
                                                double f1 = o1.score();
                                                double f2 = o2.score();
                                                if (f1 == f2) return 0;
                                                return (f1 > f2) ? -1 : 1;
                                        }
                        });
                for (Faction fac : factions) {
                        elim_list.add(fac.getSpinalCordFactory());
                }
                        Collections.reverse(elim_list); // winner last
                        try {
                                        dos.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        return factions.get(0).getSpinalCordFactory();
                }
            return null;
        }
        // special case: add winner to end of list
        elim_list.add(winner);
                try {
                        dos.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        return winner;
    }

    @Override
    public Properties[] getBotTextInfo() {
        List<Properties> props = new ArrayList<Properties>();
        
        Properties prp = new Properties();
        prp.put("Time", time);
        props.add(prp);
        
        for (Faction fac : factions) {
            Properties p = fac.infoAsProperties();
            if (p != null)
                props.add(p);
        }
        
        return props.toArray(new Properties[0]);
    }

        public float getTime() {
                return (float) time;
        }

        public String getStats() {
                StringBuilder sb = new StringBuilder();
                sb.append("time: ").append(((int)(time * 10)) / 10.0);
        for (Faction fac : factions) {
                sb.append(", ").append(fac.units.size()).append('/').append(fac.numCities()).append(" last cap: ").append(fac.getLastCaptureCity());
        }
                return sb.toString();
        }
}
