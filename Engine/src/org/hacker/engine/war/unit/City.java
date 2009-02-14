package org.hacker.engine.war.unit;

import java.util.HashMap;
import java.util.Map;

import org.hacker.engine.war.Faction;
import org.hacker.engine.war.Terra;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.unit.Weapon.DamageType;

/**
 * where we build stuff
 */
public class City extends Unit {
    private boolean changingAllegiance = false;
    private Map<Faction, Double> facDamage = new HashMap<Faction, Double>();

    public City(int id, double x, double y) {
        super(id, x, y, 3);
    }

    @Override
    public void impact(double damage, Unit enemy, VisualsListener vis, int terrain, DamageType damType) {
        if (changingAllegiance)
            return; // no more hits this turn
        
        // handle neutral case specially
        Faction ef = enemy.faction;
                if (faction == null) {
                        double dam = 0;
                        if (facDamage.containsKey(ef))
                                dam = facDamage.get(ef);
                        dam += damage;
                facDamage.put(ef, dam);
                if (dam > maxHP)
                        changingAllegiance = true;
        }
                else {
                        // owned
                        super.impact(damage, enemy, vis, terrain, damType);
                if (hp <= 0)
                    changingAllegiance = true;
                }
        if (changingAllegiance) {
            // when we die, we change sides
            hp = maxHP;
            pendingOrder = null;
//            System.out.println("allegiance " + id + " to " + enemy.faction.index + " " + enemy.faction);
            Faction ourFac = faction;
            ef.addUnit(this);
            if (ourFac != null) ourFac.removeUnit(this);
            if (vis != null) vis.changedAllegiance(this);
        }
    }
    
    @Override
        public void advance(double stepDistance, Terra terra, VisualsListener vis) {
                super.advance(stepDistance, terra, vis);
                
                // heal faction dam
                if (faction == null) {
                        for (Map.Entry<Faction, Double> entry : facDamage.entrySet()) {
                                double dam = entry.getValue();
                        dam -= heal;
                        dam = Math.max(0, dam);
                        facDamage.put(entry.getKey(), dam);
                        }
                }
        }

        @Override
        public double getHp() {
                if (faction != null)
                        return super.getHp();
                double maxdam = 0;
                for (Map.Entry<Faction, Double> entry : facDamage.entrySet()) {
                        double dam = entry.getValue();
                        maxdam = Math.max(dam, maxdam);
                }
                return maxHP - maxdam;
        }

        @Override
    public void idle(Terra terra) {
        changingAllegiance = false;
    }
    
        public int getUnitType() {
                return UnitTypes.CITY;
        }
}
