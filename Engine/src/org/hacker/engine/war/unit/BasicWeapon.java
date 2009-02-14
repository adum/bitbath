package org.hacker.engine.war.unit;

/**
 * simple params describe this weapon
 */
public class BasicWeapon implements Weapon {
    private final double rechargeTime;
    private final double range;
    private final double damage;
    private double recharge = 0;
    
    private final DamageType damageType;
    
    public BasicWeapon(final double rechargeTime, final double range, final double damage, final DamageType damageType) {
        this.rechargeTime = rechargeTime;
        this.range = range;
        this.damage = damage;
        this.damageType = damageType;
    }

    public boolean canFire() {
        return recharge >= rechargeTime;
    }

    public void elapseTime(double tm) {
        recharge += tm;
    }

    public void fire() {
        recharge = 0;
    }

    public double getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

	public DamageType getDamageType() {
		return damageType;
	}
}
