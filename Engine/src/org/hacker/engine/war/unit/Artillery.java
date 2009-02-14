package org.hacker.engine.war.unit;

import org.hacker.engine.war.Terrain;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.unit.Weapon.DamageType;

/**
 * has splash damage
 */
public class Artillery extends Ambulatory {
    private final Weapon weapon;
    
    private class SplashWeapon extends BasicWeapon implements SplashDamage {
        public SplashWeapon(final double rechargeTime, final double range, final double damage) {
                super(rechargeTime, range, damage, DamageType.SHELL);
        }
        
                public double getSplash() {
                        return 2;
                }

                public double getSplashDamage() {
                        return 0.5 * getDamage();
                }
    }

    public Artillery(int id, double x, double y) {
        super(id, x, y, 1);
        
        weapon = new SplashWeapon(1.2, 5, 0.2);
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

        @Override
        protected double getForestSpeed() {
                return 0.1;
        }

        @Override
        protected double getGrassSpeed() {
                return 0.8;
        }

        @Override
        protected double getSwampSpeed() {
                return 0.05;
        }

        public int getUnitType() {
                return UnitTypes.ARTIL;
        }
        
        @Override
        public void impact(double damage, Unit enemy, VisualsListener vis, int terrain, DamageType damType) {
                switch (terrain) {
                case Terrain.SWAMP:
                        damage *= 2;
                        break;
                case Terrain.FOREST:
                        damage *= 0.5;
                        break;
                }
                if (damType == DamageType.FIRE)
                        damage *= 2.5;
                super.impact(damage, enemy, vis, terrain, damType);
        }
}