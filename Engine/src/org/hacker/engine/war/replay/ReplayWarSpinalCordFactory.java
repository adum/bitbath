package org.hacker.engine.war.replay;

import org.hacker.engine.war.WarSpinalCord;
import org.hacker.engine.war.WarSpinalCordFactory;

/**
 * builds WarSpinalCords
 */
public class ReplayWarSpinalCordFactory implements WarSpinalCordFactory {
	private ReplayReader replayReader;

	public ReplayWarSpinalCordFactory(ReplayReader replayReader) {
		this.replayReader = replayReader;
	}

	public WarSpinalCord createSpinalCord() throws Exception {
		return new ReplayWarSpinalCord(replayReader);
	}

	public String getName() {
		return "Replay";
	}
}
