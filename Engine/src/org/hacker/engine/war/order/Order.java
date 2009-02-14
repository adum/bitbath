package org.hacker.engine.war.order;

import java.io.DataOutputStream;

import org.hacker.engine.war.Terra;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.unit.Unit;

/**
 * order from a unit, can be executed
 */
public abstract class Order {
    public static final int BUILD = 0;
    public static final int MOVE = 1;
    public static final int STOP = 2;

    public double execTime = 2.5;

    public abstract void execute(Unit unit, Terra terra, VisualsListener vis);

    public abstract int getOrderType();

    /**
     * write description to stream
     */
	public void writeOut(DataOutputStream dos) {
	}
}
