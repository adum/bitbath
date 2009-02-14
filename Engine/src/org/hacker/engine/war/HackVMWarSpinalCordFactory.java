package org.hacker.engine.war;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.Random;

/**
 * builds HackVMWarSpinalCord
 */
public class HackVMWarSpinalCordFactory implements WarSpinalCordFactory {
	
	private String cp;
	private String className;
	private Random r;
	private ByteArrayInputStream binp;
	private int insCap;
	private boolean isJar;
	private String parentDir;

	public HackVMWarSpinalCordFactory(String cp, String className, long seed, ByteArrayInputStream binp, int insCap, boolean isJar) {
		this.cp = cp;
		this.className = className;
		r = new Random(seed);
		this.binp = binp;
		this.insCap = insCap;
		this.isJar = isJar;
	}

	public HackVMWarSpinalCordFactory(String cp, String className, long seed, String parent, int insCap) {
		this.cp = cp;
		this.className = className;
		r = new Random(seed);
		this.insCap = insCap;
		this.isJar = false;
		this.parentDir = parent;
	}

	public WarSpinalCord createSpinalCord() throws Exception {
		HackVMWarSpinalCord spinal;
		if (binp != null) {
			binp.reset();
			spinal = new HackVMWarSpinalCord(cp, className, r.nextLong(), new DataInputStream(binp), insCap, isJar);
		}
		else {
			spinal = new HackVMWarSpinalCord(cp, className, r.nextLong(), parentDir, insCap);
		}
        return spinal;
	}

	public String getName() {
		return className;
	}

	@Override
	public String toString() {
		return className;
	}
}
