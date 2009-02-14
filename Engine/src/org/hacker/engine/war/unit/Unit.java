package org.hacker.engine.war.unit;

import java.awt.Color;

import org.hacker.engine.war.Faction;
import org.hacker.engine.war.Terra;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.WarSpinalCord;
import org.hacker.engine.war.order.Order;
import org.hacker.engine.war.unit.Weapon.DamageType;

/**
 * a thing that moves around the world
 */
public abstract class Unit {
    public final int id;
    public double x, y;
    private WarSpinalCord cord;
    public Order pendingOrder;
    protected double hp = 1.0;
    public final double maxHP;
    protected Faction faction;
    public double heal = 0.001;
        public int[] radio;

    public Unit(final int id, double x, double y, double maxHP) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.hp = this.maxHP = maxHP;
    }

    public void setSpinalCord(WarSpinalCord cord) {
        this.cord = cord;
    }

    public WarSpinalCord getCord() {
        return cord;
    }

    public void idle(Terra terra) {
    }
    
    public Weapon getWeapon() {
        return null;
    }

    /**
     * is other in range?
     */
        public boolean inRange(Unit other, double range) {
                double d = (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y);
                return d <= range * range;
        }

    public double distance(Unit other) {
        return Math.hypot(x - other.x, y - other.y);
    }

    public boolean isDead() {
        return hp <= 0;
    }
    
    /**
     * incoming damage
     */
    public void impact(double damage, Unit enemy, VisualsListener vis, int terrain, DamageType damType) {
        hp -= damage;
        if (hp <= 0) {
            if (vis != null) {
                vis.newExplosion(x, y);
                vis.newWreck(x, y);
            }
        }
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public Faction getFaction() {
        return faction;
    }

    /**
     * moves according to existing or pending orders. or just sits there.
     * fire on nearby enemies if ready.
     */
    public void advance(double stepDistance, Terra terra, VisualsListener vis) {
        if (pendingOrder != null) {
            Order order = pendingOrder;
            order.execTime -= stepDistance;
            if (order.execTime <= 0) {
                // do it
                order.execute(this, terra, vis);
                pendingOrder = null; // seeya
            }
        }
        idle(terra);
        
        hp += heal;
        hp = Math.min(maxHP, hp);

        Weapon weapon = getWeapon();
        if (weapon != null) {
            weapon.elapseTime(stepDistance);
            if (weapon.canFire()) {
                fireCheck(terra, vis, weapon);
            }
        }
    }

        private void fireCheck(Terra terra, VisualsListener vis, Weapon weapon) {
                double range = weapon.getRange();
                // look for baddies
                for (Unit enemy : terra.units) {
                    if (enemy.faction == faction)
                        continue;
                    if (!inRange(enemy, range))
                        continue;
                    // okay, blast them away
                    weapon.fire();
                    if (vis != null) {
                        vis.newLaser(x, y, enemy.x, enemy.y, Color.WHITE);
                        vis.newExplosionSmall(enemy.x, enemy.y);
                    }
                    int terrain = terra.getTerrain(enemy.x, enemy.y);
                    enemy.impact(weapon.getDamage(), this, vis, terrain, weapon.getDamageType());
                    
                    // splash?
                    if (weapon instanceof SplashDamage) {
                        double splashRange = ((SplashDamage)weapon).getSplash();
                        double splashDamage = ((SplashDamage)weapon).getSplashDamage();
                        for (Unit e2 : terra.units) {
                                if (e2 == enemy) continue;
                            if (e2.faction == faction)
                                continue;
                            double dist2 = enemy.distance(e2);
                            if (dist2 > splashRange)
                                continue;
                            // okay, blast them away
                            if (vis != null) {
                                vis.newLaser(enemy.x, enemy.y, e2.x, e2.y, Color.CYAN);
                                vis.newExplosionSmall(e2.x, e2.y);
                            }
                            e2.impact(splashDamage, this, vis, terra.getTerrain(e2.x, e2.y), weapon.getDamageType());
                        }                       
                    }
                    break;
                }
        }

        /**
         * how far we can see
         */
        public double getVision(int terrainType) {
                return 12;
        }
        
        public double getHp() {
                return hp;
        }

        public abstract int getUnitType();
}
