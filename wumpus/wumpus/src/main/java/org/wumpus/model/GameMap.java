package org.wumpus.model;

import java.util.HashMap;
import java.util.Map;

public class GameMap {

    private Map<BrickId, Brick> bricks = new HashMap<>();

    private int size;

    public Map<BrickId, Brick> getBricks() {
        return bricks;
    }

    public void setBricks(Map<BrickId, Brick> bricks) {
        this.bricks = bricks;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

