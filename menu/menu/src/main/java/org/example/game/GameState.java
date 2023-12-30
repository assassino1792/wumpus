package org.example.game;

public class GameState {
    /** The name of the player. */
    private String playerName;
    /** The current map state of the game. */
    private String mapState;
    /** The horizontal position of the hero on the map. */
    private int heroPosX;
    /** The vertical position of the hero on the map. */
    private int heroPosY;
    /** The number of arrows held by the hero. */
    private int arrowCount;
    /** The number of steps taken by the player. */
    private int stepCount;

    /** The count of Wumpuses killed by the player. */
    private int wumpusCount;

    /** Indicates whether the player has gold. */
    private boolean hasGold;
    /** The initial horizontal position of the hero on the map. */
    private int heroInitialPosX;
    /** The initial vertical position of the hero on the map. */
    private int heroInitialPosY;

    /**
     * Represents the state of the game for
     * a player, including player name,
     * map state, hero positions, arrow count,
     * step count, wumpus count, and gold status.
     *
     * @param playerNameParam The name of the player.
     * @param mapStateParam The state of the game map.
     * @param heroPosXParam The horizontal position of the hero.
     * @param heroPosYParam The vertical position of the hero.
     * @param heroInitialPosXParam The initial horizontal position of the hero.
     * @param heroInitialPosYParam The initial vertical position of the hero.
     * @param arrowCountParam The number of arrows held by the hero.
     * @param stepCountParam The number of steps taken by the player.
     * @param wumpusKilledCountParam The count of Wumpuses killed by the player.
     * @param hasGoldParam Indicates whether the player has gold.
     */

    public GameState(
          final String playerNameParam,
          final String mapStateParam,
          final int heroPosXParam,
          final int heroPosYParam,
          final int heroInitialPosXParam,
          final int heroInitialPosYParam,
          final int arrowCountParam,
          final int stepCountParam,
          final int wumpusKilledCountParam,
          final boolean hasGoldParam) {

        this.playerName = playerNameParam;
        this.mapState = mapStateParam;
        this.heroPosX = heroPosXParam;
        this.heroPosY = heroPosYParam;
        this.arrowCount = arrowCountParam;
        this.stepCount = stepCountParam;
        this.wumpusCount = wumpusCount;
        this.hasGold = hasGoldParam;
        this.heroInitialPosX = heroInitialPosXParam;
        this.heroInitialPosY = heroInitialPosYParam;
    }
    /**
     * Gets the count of Wumpuses killed by the player.
     *
     * @return The count of Wumpuses killed.
     */

    public int getWumpusCount() {
        return wumpusCount;
    }
    /**
     * Gets the state of the game map.
     *
     * @return The state of the game map as a String.
     */
    public String getMapState() {
        return mapState;
    }
    /**
     * Gets the horizontal position of the hero.
     *
     * @return The horizontal position of the hero.
     */
    public int getHeroPosX() {
        return heroPosX;
    }
    /**
     * Gets the vertical position of the hero.
     *
     * @return The vertical position of the hero.
     */
    public int getHeroPosY() {
        return heroPosY;
    }
    /**
     * Gets the number of arrows held by the hero.
     *
     * @return The number of arrows held by the hero.
     */
    public int getarrowCount() {
        return arrowCount;
    }
    /**
     * Gets the number of steps taken by the player.
     *
     * @return The number of steps taken by the player.
     */
    public int getStepCount() {
        return stepCount;
    }
    /**
     * Checks if the player has gold.
     *
     * @return true if the player has gold, false otherwise.
     */
    public boolean isHasGold() {
        return hasGold;
    }
    /**
     * Sets whether the player has gold.
     *
     * @param hasGold true if the player has gold, false otherwise.
     */
    public void setHasGold(final boolean hasGold) {
        this.hasGold = hasGold;
    }
    /**
     * Gets the initial horizontal position of the hero.
     *
     * @return The initial horizontal position of the hero.
     */
    public int getHeroInitialPosX() {
        return heroInitialPosX;
    }
    /**
     * Sets the initial horizontal position of the hero.
     *
     * @param heroInitialPosX The new initial horizontal position of the hero.
     */
   public void setHeroInitialPosX(final int heroInitialPosX) {
        this.heroInitialPosX = heroInitialPosX;
    }
    /**
     * Gets the initial vertical position of the hero.
     *
     * @return The initial vertical position of the hero.
     */
    public int getHeroInitialPosY() {
        return heroInitialPosY;
    }
    /**
     * Sets the initial vertical position of the hero.
     *
     * @param heroInitialPosY The new initial vertical position of the hero.
     */
   public void setHeroInitialPosY(final int heroInitialPosY) {
        this.heroInitialPosY = heroInitialPosY;
    }

}

