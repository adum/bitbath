package org.hacker.engine.war.unit;

import org.hacker.engine.war.Terrain;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.unit.Weapon.DamageType;

/**
 * basic fighting unit that likes grass, hovercraft, doesn't like artil
 */
public class Grunt extends Ambulatory {
    private final Weapon weapon;

    public Grunt(int id, double x, double y) {
        super(id, x, y, 1);
        
        weapon = new BasicWeapon(1, 4.5, 0.2, DamageType.LASER);
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

	public int getUnitType() {
		return UnitTypes.GRUNT;
	}

    protected double getSwampSpeed() {
		return 0.1;
	}

    protected double getForestSpeed() {
		return 0.7;
	}

    protected double getGrassSpeed() {
		return 1.0;
	}

	@Override
	public void impact(double damage, Unit enemy, VisualsListener vis, int terrain, DamageType damType) {
		switch (terrain) {
		case Terrain.SWAMP:
			damage *= 2;
			break;
		case Terrain.FOREST:
			damage *= 0.7;
			break;
		}
		if (damType == DamageType.SHELL)
			damage *= 2.5;
		if (damType == DamageType.FIRE)
			damage *= 0.4;
		super.impact(damage, enemy, vis, terrain, damType);
	}
}
