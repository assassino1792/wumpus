package org.example.game;

import org.example.map.MapID;
import org.example.map.WayType;

public class Hero {

    private Long heroId;

    private String playerName;
    private MapID mapID;
    private WayType way;
    private int arrowCount;
    private boolean hasGold;
    private int moveCount;

    public Hero() {
    }

    public WayType getWay() {
        return way;
    }

    public void setWay(WayType way) {
        this.way = way;
    }

    public int getArrowCount() {
        return arrowCount;
    }

    public void setArrowCount(int arrowCount) {
        this.arrowCount = arrowCount;
    }

    public boolean isHasGold() {
        return hasGold;
    }

    public void setHasGold(boolean hasGold) {
        this.hasGold = hasGold;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public MapID getBrickId() {
        return mapID;
    }

    public void setBrickId(MapID mapID) {
        this.mapID = mapID;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long heroId) {
        this.heroId = heroId;
    }
}
