package org.hacker.engine.war;

import java.awt.Color;

import org.hacker.engine.war.unit.City;
import org.hacker.engine.war.unit.Unit;

/**
 * listen to visual events emitted from the game model 
 */
public interface VisualsListener {
    public void newWreck(double x, double y);
    public void newExplosion(double x, double y);
    public void newExplosionSmall(double x, double y);
    public void newProjectile(double startX, double startY, double destX, double destY);
    public void newLaser(double startX, double startY, double destX, double destY, Color color);
    public void newCityBuild(City city);
	public void deadUnit(Unit unit);
    public void changedAllegiance(City city);
}
