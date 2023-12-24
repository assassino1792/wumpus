package org.example.game;

public class GameState {
    private String playerName;
    private String mapState;
    private int heroPosX;
    private int heroPosY;
    private int arrowCount;
    private int stepCount;

    private int wumpusCount;

    private boolean hasGold;

    public GameState(String playerName, String mapState, int heroPosX, int heroPosY, int arrowCount, int stepCount, int wumpusCount,boolean hasGold) {
        this.playerName = playerName;
        this.mapState = mapState;
        this.heroPosX = heroPosX;
        this.heroPosY = heroPosY;
        this.arrowCount = arrowCount;
        this.stepCount = stepCount;
        this.wumpusCount = wumpusCount;
        this.hasGold = hasGold;
    }
    public String getPlayerName() {
        return playerName;
    }
    public int getWumpusCount() {
        return wumpusCount;
    }
    public String getMapState() {
        return mapState;
    }
    public int getHeroPosX() {
        return heroPosX;
    }
    public int getHeroPosY() {
        return heroPosY;
    }
    public int getarrowCount() {return arrowCount;}
    public int getStepCount() {
        return stepCount;
    }
    public boolean isHasGold() {return hasGold;}
    public void setHasGold(boolean hasGold) {this.hasGold = hasGold;
    }

}

