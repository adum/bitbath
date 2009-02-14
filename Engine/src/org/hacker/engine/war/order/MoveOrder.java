package org.hacker.engine.war.order;

import java.io.DataOutputStream;
import java.io.IOException;

import org.hacker.engine.war.Terra;
import org.hacker.engine.war.VisualsListener;
import org.hacker.engine.war.unit.Ambulatory;
import org.hacker.engine.war.unit.Unit;

public class MoveOrder extends Order {
    public final float destX, destY;
    public final short sdestX, sdestY;

    public MoveOrder(final double destX, final double destY) {
        float dx = (float) destX;
        float dy = (float) destY;
        sdestX = (short) (dx * 10.0f);
        sdestY = (short) (dy * 10.0f);
        this.destX = sdestX / 10.0f;
        this.destY = sdestY / 10.0f;
    }

    @Override
    public void execute(Unit unit, Terra terra, VisualsListener vis) {
        Ambulatory ambi = (Ambulatory) unit;
        // randomize dest slightly
        double x = destX + terra.r.nextDouble() - 0.5;
		double y = destY + terra.r.nextDouble() - 0.5;
		x = Math.max(0, x);
		y = Math.max(0, y);
		x = Math.min(terra.dimX, x);
		y = Math.min(terra.dimY, y);
		ambi.moveTo(x, y);
//		System.out.println("move " + x + ", " + y);
    }

	@Override
	public void writeOut(DataOutputStream dos) {
		try {
			dos.writeShort(sdestX);
			dos.writeShort(sdestY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getOrderType() {
		return MOVE;
	}
}
