package org.wumpus.model;


public class Hero {

    private Long heroId;

    private String playerName;
    private BrickId brickId;
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

    public BrickId getBrickId() {
        return brickId;
    }

    public void setBrickId(BrickId brickId) {
        this.brickId = brickId;
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
