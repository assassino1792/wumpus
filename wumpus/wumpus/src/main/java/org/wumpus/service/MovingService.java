package org.wumpus.service;

package com.misi.wumpus.service;

import org.wumpus.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MovingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovingService.class);

    public WayType nextWay(WayType actual, TurnDirection turnDirection) {
        char nextWayKey = actual.getKey();
        if (turnDirection == TurnDirection.LEFT) {
            nextWayKey = actual.getLeft();
        } else if (turnDirection == TurnDirection.RIGHT) {
            nextWayKey = actual.getRight();
        }

        return WayType.byKey(nextWayKey);
    }

    public Brick nextBrick(BrickId actaulBrickId, WayType wayType, GameMap gameMap) {
        int horizontal = actaulBrickId.getHorizontal();
        int vertical = actaulBrickId.getVertical();
        boolean isAdd = wayType.isAdd();
        if (wayType.isHorizontal()) {
            horizontal += isAdd ? 1 : -1;
        } else {
            vertical += isAdd ? 1 : -1;
        }

        return gameMap.getBricks().get(new BrickId(horizontal, vertical));
    }

}

