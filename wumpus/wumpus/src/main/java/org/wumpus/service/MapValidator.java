package org.wumpus.service;


import org.wumpus.config.WumpusConfig;
import org.wumpus.model.*;
import org.wumpus.util.HelperUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Service
public class MapValidator {

    private final WumpusConfig wumpusConfig;

    public MapValidator(WumpusConfig wumpusConfig) {
        this.wumpusConfig = wumpusConfig;
    }

    public void validateMap(Map<BrickId, Brick> bricks, int mapSize) {
        if (mapSize < wumpusConfig.getMinSize() || mapSize > wumpusConfig.getMaxSize()) {
            throw new IllegalArgumentException(String.format("The map size has to between %d and %d", wumpusConfig.getMinSize(), wumpusConfig.getMaxSize()));
        }

        if (CollectionUtils.isEmpty(bricks)) {
            throw new IllegalArgumentException("No bricks.");
        }

        if ((mapSize * mapSize) != bricks.size()) {
            throw new IllegalArgumentException("Missing bricks.");
        }

//        if (HelperUtil.countBrickOfType(BrickType.HERO, bricks.values()) != 1) {
//            throw new IllegalArgumentException("Missing or too much hero.");
//        }

        if (HelperUtil.countBrickOfType(BrickType.GOLD, bricks.values()) != wumpusConfig.getGoldCount()) {
            throw new IllegalArgumentException("Missing or too much gold.");
        }

        if (HelperUtil.countBrickOfType(BrickType.WUMPUS, bricks.values()) != wumpusCount(mapSize)) {
            throw new IllegalArgumentException("Missing or too much wumpus.");
        }


        for (int i = 1; i <= mapSize; i++) {
            for (int j = 1; j <= mapSize; j++) {

                BrickId brickId = new BrickId(i, j);

                Brick brick = bricks.getOrDefault(brickId, null);

                if (brick == null) {
                    throw new IllegalArgumentException("No bricks on i: " + i + " j: " + j);
                }

                if ((i == 1 || j == 1 || i == mapSize || j == mapSize) && brick.getBrickType() != BrickType.WALL) {
                    throw new IllegalArgumentException("On border of game table have to be WALL! i: " + i + " j: " + j);
                }
            }
        }

    }

    private int wumpusCount(int mapSize) {
        int wumpusCount = wumpusConfig.getWumpusStage1();
        if (mapSize > 8) {
            wumpusCount = wumpusConfig.getWumpusStage2();
        }
        if (mapSize > 14) {
            wumpusCount = wumpusConfig.getWumpusStage3();
        }
        return wumpusCount;
    }


}

