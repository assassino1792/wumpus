package org.example.game;

import org.example.map.MapID;
import org.example.map.WayType;
import org.example.map.MapValidator;

public class Hero {

    /** The current map ID of the hero. */
    private MapID mapID;
    /** The current way or direction the hero is facing. */
    private WayType way;
    /** The number of arrows the hero has. */
    private int arrowCount;

    /**
     * Initializes a new instance of the Hero class.
     */
    public Hero() {
    }

    /**
     * Gets the current way or direction the hero is facing.
     * @return The current way of the hero.
     */
    public WayType getWay() {
        return way;
    }

    /**
     * Sets the current newWay or direction the hero is facing.
     * @param newWay The new newWay to set for the hero.
     */
    public void setWay(final WayType newWay) {
        this.way = newWay;
    }
    /**
     * Gets the number of arrows the hero has.
     * @return The number of arrows the hero has.
     */
    public int getArrowCount() {
        return arrowCount;
    }
    /**
     * Sets the number of arrows the hero has.
     * @param newArrowCount The new number of arrows to set for the hero.
     */
    public void setArrowCount(final int newArrowCount) {
        this.arrowCount = newArrowCount;
    }
    /**
     * Gets the current map ID of the hero.
     * @return The current map ID of the hero.
     */
    public MapID getMapID() {
        return mapID;
    }
    /**
     * Sets the current map ID of the hero.
     * @param newMapID The new map ID to set for the hero.
     */
    public void setMapID(final MapID newMapID) {
        this.mapID = newMapID;
    }
    /** Indicates whether the hero has gold. */
    private boolean hasGold = false;
    /**
     * Checks if the hero has gold.
     * @return true if the hero has gold, false otherwise.
     */
    public boolean isHasGold() {
        return hasGold;
    }
    /**
     * Sets whether the hero has gold.
     * @param newHasGold true if the hero has gold, false otherwise.
     */
    public void setHasGold(final boolean newHasGold) {
        this.hasGold = newHasGold;
    }
    /**
     * Initializes the hero with the specified
     * number of arrows and facing north.
     * @param mapSize The size of the map to
     * determine the initial number of arrows.
     */
    public void initializeHero(final int mapSize) {
        this.arrowCount = MapValidator.wumpusCount(mapSize);
        this.way = WayType.NORTH;
    }

}
