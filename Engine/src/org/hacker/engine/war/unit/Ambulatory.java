package org.hacker.engine.war.unit;

import org.hacker.engine.war.Terra;
import org.hacker.engine.war.Terrain;

/**
 * a unit that can move
 */
public abstract class Ambulatory extends Unit {
    protected double destX, destY;
    protected double startX, startY;
    protected boolean isMoving = false;
    private double moveAngle;

    public Ambulatory(int id, double x, double y, double maxHP) {
        super(id, x, y, maxHP);
    }
    
    public void moveTo(double destX, double destY) {
        isMoving = true;
        this.destX = destX;
        this.destY = destY;
        startX = x;
        startY = y;
        
        moveAngle = Math.atan2(destY - y, destX - x);
    }
    
    protected double getSpeed(int terrainType) {
        double v = 0.1;
        switch (terrainType) {
        case Terrain.GRASS: default:
                v *= getGrassSpeed();
                break;
        case Terrain.FOREST:
                v *= getForestSpeed();
                break;
        case Terrain.SWAMP:
                v *= getSwampSpeed();
                break;
        }
                return v * 0.3;
    }
    
    protected abstract double getSwampSpeed();

    protected abstract double getForestSpeed();

    protected abstract double getGrassSpeed();

        public boolean isMoving() {
        return isMoving;
    }
        
        public void stopMoving() {
                isMoving = false;
        }

    public double getMoveAngle() {
        return moveAngle;
    }

    @Override
    public void idle(Terra terra) {
        if (isMoving) {
                double v = getSpeed(terra.getTerrain(x, y));
            x += Math.cos(moveAngle) * v;
            y += Math.sin(moveAngle) * v;

            // are we done?
            boolean xgood = false;
            if (destX > startX) {
                if (x >= destX)
                    xgood = true;
            }
            else {
                if (x <= destX)
                    xgood = true;
            }
            boolean ygood = false;
            if (destY > startY) {
                if (y >= destY)
                    ygood = true;
            }
            else {
                if (y <= destY)
                    ygood = true;
            }
            if (xgood && ygood)
                isMoving = false;
        }
    }
}
