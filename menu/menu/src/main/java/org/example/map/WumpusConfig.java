package org.example.map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConfigurationProperties("wumpus")
public class WumpusConfig {

    private int minSize;
    private int maxSize;
    private int goldCount;
    private int wumpusStage1;
    private int wumpusStage2;
    private int wumpusStage3;


    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getGoldCount() {
        return goldCount;
    }

    public void setGoldCount(int goldCount) {
        this.goldCount = goldCount;
    }

    public int getWumpusStage1() {
        return wumpusStage1;
    }

    public void setWumpusStage1(int wumpusStage1) {
        this.wumpusStage1 = wumpusStage1;
    }

    public int getWumpusStage2() {
        return wumpusStage2;
    }

    public void setWumpusStage2(int wumpusStage2) {
        this.wumpusStage2 = wumpusStage2;
    }

    public int getWumpusStage3() {
        return wumpusStage3;
    }

    public void setWumpusStage3(int wumpusStage3) {
        this.wumpusStage3 = wumpusStage3;
    }
}


