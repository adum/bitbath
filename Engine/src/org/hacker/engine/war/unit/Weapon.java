package org.hacker.engine.war.unit;

/**
 * describes a weapon's properties
 */
public interface Weapon {
    public boolean canFire();
    public void elapseTime(double tm);
    public void fire();
    public double getRange();
    public double getDamage();

    public enum DamageType { LASER, FIRE, SHELL }
    public DamageType getDamageType();
}
