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
    private int heroInitialPosX;
    private int heroInitialPosY;

    public GameState(String playerName, String mapState, int heroPosX, int heroPosY, int heroInitialPosX, int heroInitialPosY, int arrowCount, int stepCount, int wumpusKilledCount, boolean hasGold) {
        this.playerName = playerName;
        this.mapState = mapState;
        this.heroPosX = heroPosX;
        this.heroPosY = heroPosY;
        this.arrowCount = arrowCount;
        this.stepCount = stepCount;
        this.wumpusCount = wumpusCount;
        this.hasGold = hasGold;
        this.heroInitialPosX = heroInitialPosX;
        this.heroInitialPosY = heroInitialPosY;
    }
   // public String getPlayerName() {return playerName;}
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
    public void setHasGold(boolean hasGold) {this.hasGold = hasGold;}
    public int getHeroInitialPosX() {return heroInitialPosX;}

   // public void setHeroInitialPosX(int heroInitialPosX) {this.heroInitialPosX = heroInitialPosX;}

    public int getHeroInitialPosY() {return heroInitialPosY;}

   // public void setHeroInitialPosY(int heroInitialPosY) {this.heroInitialPosY = heroInitialPosY;}

}

