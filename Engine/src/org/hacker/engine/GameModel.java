package org.hacker.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * base for all game types
 */
public abstract class GameModel {
	/** we save here how to replay this game */
    protected StringBuilder replay = new StringBuilder();
    /** all randomness comes from this instance */
    protected final Random r;
    /** a list of which factions have been eliminated */
    protected List<Object> elim_list = new ArrayList<Object>();

    public GameModel(long seed) {
        r = new Random(seed);
    }

    /**
     * progresses one step
     */
    public abstract void step();

    /**
     * @return faction that won, or null if none
     */
    public abstract Object findWinner();

    /**
     * @return a string representing the how the game was played out
     */
    public String getReplay() {
        return replay.toString();
    }

    /**
     * @return factions who have been killed
     */
    public List<Object> getEliminated() {
        return elim_list;
    }

    /**
     * @return a property list displaying info about the current game
     */
    public abstract Properties[] getBotTextInfo();
}
