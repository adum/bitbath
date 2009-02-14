package org.hacker.engine.war;

/**
 * builds WarSpinalCords
 */
public interface WarSpinalCordFactory {
    public WarSpinalCord createSpinalCord() throws Exception;
    public String getName();
}
