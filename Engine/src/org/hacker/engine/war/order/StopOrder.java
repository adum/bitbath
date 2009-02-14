package org.hacker.engine.war.order;

import org.hacker.engine.war.Terra;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.unit.Ambulatory;
import org.hacker.engine.war.unit.Unit;

public class StopOrder extends Order {
	public StopOrder() {
		execTime = 0;
	}
	
	@Override
	public void execute(Unit unit, Terra terra, VisualsListener vis) {
        Ambulatory ambi = (Ambulatory) unit;
        ambi.stopMoving();
	}

	@Override
	public int getOrderType() {
		return STOP;
	}
}
