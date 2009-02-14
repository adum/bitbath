package org.hacker.engine.war.replay;

import java.io.DataOutputStream;
import java.io.IOException;

import org.hacker.engine.war.WarModel;

/**
 * for writing replays.
 * one bit off and all is lost!
 */
public class ReplayOutputStream extends DataOutputStream {
	public static final int OVERFLOW_MARKER = 255;
	int lastTime;
	private int bytesInId = 1;
	
	public ReplayOutputStream(DataOutputStream dout) {
		super(dout);
	}
	
	/**
	 * uses a differential to save space
	 * @param t time
	 */
	public void writeTime(double t) throws IOException {
		int tmi = (int) (t * 10.0);
		int diff = tmi - lastTime;
		lastTime = tmi;
		diff /= WarModel.THINK_EVERY;
		if (diff > 255)
			System.err.println("too long! " + diff);
		writeByte(diff);
	}

	public void writeFrame(double time, int id, int orderType) throws IOException {
		if (id >= (1 << (bytesInId * 8))) {
			writeByte(OVERFLOW_MARKER);
			bytesInId++;
		}
		writeByte(orderType);
		writeTime(time);
		for (int i = bytesInId - 1; i >= 0; i--) {
			int b = id >> (8 * i);
			writeByte(b & 0xff);
		}
	}
}
