package org.example.map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConfigurationProperties("wumpus")

/**
 * Configuration class for Wumpus game properties.
 */
public class WumpusConfig {


    private int minSize;
    private int maxSize;
    private int goldCount;
    private int wumpusStage1;
    private int wumpusStage2;
    private int wumpusStage3;

    /**
     * Gets the minimum map size allowed.
     * @return The minimum map size.
     */
    public int getMinSize() {
        return minSize;
    }
    /**
     * Sets the minimum map size allowed.
     * @param minSize The minimum map size to set.
     */
    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }
    /**
     * Gets the maximum map size allowed.
     * @return The maximum map size.
     */
    public int getMaxSize() {
        return maxSize;
    }
    /**
     * Sets the maximum map size allowed.
     * @param maxSize The maximum map size to set.
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
    /**
     * Gets the number of gold pieces in the game.
     * @return The number of gold pieces.
     */
    public int getGoldCount() {
        return goldCount;
    }
    /**
     * Sets the number of gold pieces in the game.
     * @param goldCount The number of gold pieces to set.
     */
    public void setGoldCount(int goldCount) {
        this.goldCount = goldCount;
    }
    /**
     * Gets the number of Wumpus creatures in stage 1.
     * @return The number of Wumpus creatures in stage 1.
     */
    public int getWumpusStage1() {
        return wumpusStage1;
    }

    /**
     * Sets the number of Wumpus creatures in stage 1.
     * @param wumpusStage1 The number of Wumpus creatures in stage 1 to set.
     */
    public void setWumpusStage1(int wumpusStage1) {
        this.wumpusStage1 = wumpusStage1;
    }
    /**
     * Gets the number of Wumpus creatures in stage 2.
     * @return The number of Wumpus creatures in stage 2.
     */
    public int getWumpusStage2() {
        return wumpusStage2;
    }
    /**
     * Sets the number of Wumpus creatures in stage 2.
     * @param wumpusStage2 The number of Wumpus creatures in stage 2 to set.
     */
    public void setWumpusStage2(int wumpusStage2) {
        this.wumpusStage2 = wumpusStage2;
    }
    /**
     * Gets the number of Wumpus creatures in stage 3.
     * @return The number of Wumpus creatures in stage 3.
     */
    public int getWumpusStage3() {
        return wumpusStage3;
    }
    /**
     * Sets the number of Wumpus creatures in stage 3.
     * @param wumpusStage3 The number of Wumpus creatures in stage 3 to set.
     */
    public void setWumpusStage3(int wumpusStage3) {
        this.wumpusStage3 = wumpusStage3;
    }
}


