package org.hacker.engine.war;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import org.hacker.engine.war.order.BuildOrder;
import org.hacker.engine.war.order.Order;
import org.hacker.engine.war.order.StopOrder;
import org.hacker.engine.war.replay.ReplayOutputStream;
import org.hacker.engine.war.unit.Ambulatory;
import org.hacker.engine.war.unit.City;
import org.hacker.engine.war.unit.Unit;

/**
 * one side of a war -- a faction can win.
 * a faction manages thinking for all bots it owns.
 */
public class Faction {
    private WarSpinalCordFactory spinalCordFactory;
    public final int index;
    Queue<Unit> units = new LinkedList<Unit>();
    private int instructionSupply, insUsed;
        private ReplayOutputStream dos;
        private double lastTime = 0, lastCaptureCity = 0, lastGiveOrder = 0;
        private int numUnitsWhoThought = 0, numUnitsInLastThoughtList = 0;

    public Faction(int index, WarSpinalCordFactory partisan, ReplayOutputStream dos) {
        this.spinalCordFactory = partisan;
        this.index = index;
        this.dos = dos;
    }

    public WarSpinalCordFactory getSpinalCordFactory() {
                return spinalCordFactory;
        }
    
        public double getLastCaptureCity() {
                return lastCaptureCity;
        }

        public double getLastGiveOrder() {
                return lastGiveOrder;
        }

        public void addUnit(Unit unit) {
        try {
            unit.setSpinalCord(spinalCordFactory.createSpinalCord());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        units.add(unit);
        unit.setFaction(this);
        if (unit instanceof City)
                lastCaptureCity = lastTime;
    }

        /**
         * 
         * @param thinkInstructions how many cycles this faction is given to think -- when they run out, no more cerebral activity
         */
    public void think(int thinkInstructions, Terra terra, double time) {
        numUnitsWhoThought = 0;
        numUnitsInLastThoughtList = units.size();
        insUsed = 0;
        if (units.size() == 0) return;
        lastTime = time;
        instructionSupply = Math.min(instructionSupply, 0); // can't run a surplus
        instructionSupply += thinkInstructions;
        if (instructionSupply < 0) return; // no chance this round, buddy
        Unit firstUp = units.peek();
        boolean doneOne = false;
        while (true) {
                // cycle the units in the queue -- we stop when we reach where we started (or run out of instructions)
                Unit unit = units.peek();
                if (doneOne && unit == firstUp) break;
                doneOne = true;
                units.add(units.remove()); // send them to end of line
                numUnitsWhoThought++;
            if (unit.pendingOrder != null && !( unit instanceof City ))
                continue;
            WarSpinalCord cord = unit.getCord();
            if (cord == null)
                continue;

            int terrainType = terra.getTerrain(unit.x, unit.y);
            double vis = unit.getVision(terrainType);
            // who do we see?
            ArrayList<Unit> seen = new ArrayList<Unit>();
            for (Unit other_unit : terra.units) {
                if (other_unit == unit) continue;
                    if (!unit.inRange(other_unit, vis)) continue;
                seen.add(other_unit);
            }
            // prepare to store some information about whom we see
            int sz = seen.size();
            double[] objX = new double[sz];
            double[] objY = new double[sz];
            int[] objFaction = new int[sz];
            int[] objType = new int[sz];
            int[] objID = new int[sz];
            // prepare arrays for sending to bot
            ArrayList<int[]> msgs = createOthersList(unit, seen, sz, objX, objY, objFaction, objType, objID);
            // prepare radio array
            int[][] radioOut = new int[msgs.size()][];
            for (int i = 0; i < msgs.size(); i++) {
                radioOut[i] = msgs.get(i);
            }
            
            if (unit instanceof City) {
                City city = (City) unit;
                cityThink(terra, time, unit, cord, terrainType, objX, objY, objFaction, objType, objID, radioOut, city);
            }
            else {
                unitThink(terra, time, unit, cord, terrainType, objX, objY, objFaction, objType, objID, radioOut);
            }
            
            // over our instruction limit?
//            if (instructionSupply <= 0)
//              break;
        }
    }

        private ArrayList<int[]> createOthersList(Unit unit, ArrayList<Unit> seen, int sz, double[] objX, double[] objY, int[] objFaction,
                        int[] objType, int[] objID) {
                ArrayList<int[]> msgs = new ArrayList<int[]>();
                for (int i = 0; i < sz; i++) {
                        Unit other = seen.get(i);
                        objX[i] = other.x;
                        objY[i] = other.y;
                        objID[i] = other.id;
                        Faction ofac = other.getFaction();
                        if (ofac == null) 
                                objFaction[i] = -1;
                        else {
                                objFaction[i] = ofac.index + 1; // increment since the indices are 0-based
                                if (ofac == unit.getFaction()) {
                                        objFaction[i] = 0; // means same as us
                                        // only broadcast if not all zero
                                        if (other.radio != null && other.radio.length == 4 &&
                                                        (other.radio[0] != 0 || other.radio[1] != 0 || other.radio[2] != 0 || other.radio[3] != 0)) {
                                                int[] ro = new int[5];
                                                ro[0] = other.id;
                                                System.arraycopy(other.radio, 0, ro, 1, 4);
                                                msgs.add(ro);
//                                      System.out.println(ro[1]);
                                        }
                                }
                                else {
                                        int ix = 0;
                                        ix++;
                                }
                        }
                        objType[i] = other.getUnitType();
                }
                return msgs;
        }

        private void cityThink(Terra terra, double time, Unit unit, WarSpinalCord cord, int terrainType, double[] objX, double[] objY,
                        int[] objFaction, int[] objType, int[] objID, int[][] radioOut, City city) {
                int oldBuildItem = 0;
                if (unit.pendingOrder != null) {
                        BuildOrder bo = (BuildOrder) unit.pendingOrder;
                        oldBuildItem = bo.buildType;
                }
                int buildItem = cord.build(terra.dimX, terra.dimY, unit.x, unit.y, terrainType, unit.id, oldBuildItem, unit.getHp(), unit.maxHP, time,
                                objX, objY, objID, objFaction, objType, radioOut);
                instructionSupply -= cord.getLastInstructionCount();
                insUsed += cord.getLastInstructionCount();
                if (buildItem > 0) {
                    BuildOrder bo = BuildOrder.fromType(buildItem);
                    city.pendingOrder = bo;
                    // save 2 replay
                    try {
                        dos.writeFrame(time, unit.id, bo.getOrderType());
                                dos.writeByte(buildItem);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }

        private void unitThink(Terra terra, double time, Unit unit, WarSpinalCord cord, int terrainType, double[] objX, double[] objY,
                        int[] objFaction, int[] objType, int[] objID, int[][] radioOut) {
                // not city
                boolean isMoving = false;
                if (unit instanceof Ambulatory)
                    isMoving = ((Ambulatory) unit).isMoving();
                int[][] radio = new int[1][];
                radio[0] = null;
                Order order = cord.think(radio, terra.dimX, terra.dimY, unit.x, unit.y, isMoving, 
                                terrainType, unit.id, unit.getUnitType(), unit.getHp(), unit.maxHP, unit.getWeapon().getRange(), time,
                                objX, objY, objID, objFaction, objType, radioOut);
                instructionSupply -= cord.getLastInstructionCount();
                insUsed += cord.getLastInstructionCount();
                unit.radio = radio[0];
                // don't do a stop order if already stopped
                if ((order instanceof StopOrder) && !isMoving)
                        order = null;
                if (order != null) {
                        lastGiveOrder = lastTime;
                    unit.pendingOrder = order;
                    if (isMoving)
                        ((Ambulatory) unit).stopMoving();
                    // save 2 replay
                    try {
                        dos.writeFrame(time, unit.id, order.getOrderType());
                                order.writeOut(dos);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }

    public void removeUnit(Unit unit) {
        if (!units.remove(unit))
            System.err.println("no remove " + unit.id);
    }

        public Properties infoAsProperties() {
        Properties p = new Properties();
        p.setProperty("Instructions", insUsed + "");
        if (instructionSupply < 0)
            p.setProperty("Instruction Debt", instructionSupply + "");
        int n = units.size();
        if (numUnitsInLastThoughtList != numUnitsWhoThought)
                p.setProperty("Thought", numUnitsWhoThought + " / " + numUnitsInLastThoughtList);

        p.setProperty("Units", n + "");
        return p;
        }

        public double score() {
                double x = 0;
        for (Unit unit : units) {
                if (unit instanceof City)
                        x += 100;
                else
                        x++;
        }
        return x;
        }

        @Override
        public String toString() {
                return spinalCordFactory.getName();
        }

        /**
         * @return we're still kicking?
         */
        public boolean alive() {
        for (Unit unit : units) {
                // only a city counts
                if (unit instanceof City) {
                        return true;
                }
        }
        return false;
        }

        /**
         * @return how many cities we control
         */
        public int numCities() {
                int cnt = 0;
        for (Unit unit : units) {
                // only a city counts
                if (unit instanceof City) {
                        cnt++;
                }
        }
        return cnt;
        }
}
