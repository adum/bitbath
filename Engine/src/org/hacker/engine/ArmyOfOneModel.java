package org.hacker.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * a game where each faction only has one entity
 */
public abstract class ArmyOfOneModel extends GameModel {
    protected List<Bot> bots;
    private int lastMover = -1; // index of bot that moved last, helps with stepping

    public ArmyOfOneModel(List<Bot> bots, long seed) {
        super(seed);
        this.bots = bots;
    }
    
    @Override
    public Bot findWinner() {
        Bot aliveBot = null;
        for (Bot bot : bots) {
            if (bot.isAlive()) {
                if (aliveBot != null)
                    return null; // two is too many
                aliveBot = bot;
            }
        }
        return aliveBot;
    }
    
    public List<Bot> getBots() {
        return bots;
    }

    @Override
    public Properties[] getBotTextInfo() {
        List<Properties> props = new ArrayList<Properties>();
        for (Bot bot : bots) {
            if (!bot.isAlive()) continue;
            Properties p = bot.infoAsProperties();
            if (p != null)
                props.add(p);
        }
        
        return props.toArray(new Properties[0]);
    }
    
    @Override
    public void step() {
        // whose turn is it to move?
        Bot bot = null;
        if (lastMover < 0) {
            bot = bots.get(0);
            lastMover = 0;
        }
        else {
            int pos = lastMover;
            while (true) {
                if (++pos == bots.size()) pos = 0;
                if (bots.get(pos).isAlive()) {
                    bot = bots.get(pos);
                    lastMover = pos;
                    break;
                }
                if (pos == lastMover)
                    throw new RuntimeException("no alive bots!");
            }
        }
        
        moveBot(bot, lastMover);
        if (!bot.isAlive()) {
            // record elimination
            elim_list.add(bot);
            // special case: add winner to end of list
            Bot winner = findWinner();
            if (winner != null) {
                elim_list.add(winner);
            }
        }
    }
    
    protected abstract void moveBot(Bot bot, int index);
}
