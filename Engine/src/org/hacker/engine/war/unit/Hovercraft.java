package org.hacker.engine.war.unit;

import org.hacker.engine.war.Terrain;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.unit.Weapon.DamageType;

/**
 * floats over swamp. likes artil, hates grunts
 */
public class Hovercraft extends Ambulatory {
    private final Weapon weapon;

    public Hovercraft(int id, double x, double y) {
        super(id, x, y, 1);
        
        weapon = new BasicWeapon(1, 5.5, 0.1, DamageType.FIRE);
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
		return 0.7;
	}

	@Override
	protected double getSwampSpeed() {
		return 0.9;
	}
	
	public int getUnitType() {
		return UnitTypes.HOVERCRAFT;
	}
	
	@Override
	public void impact(double damage, Unit enemy, VisualsListener vis, int terrain, DamageType damType) {
		switch (terrain) {
		case Terrain.SWAMP:
			damage *= 0.5;
			break;
		case Terrain.FOREST:
			damage *= 2;
			break;
		}
		if (damType == DamageType.LASER)
			damage *= 2.5;
		super.impact(damage, enemy, vis, terrain, damType);
	}
}
