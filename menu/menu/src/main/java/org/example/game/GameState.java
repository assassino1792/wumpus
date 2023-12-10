package org.example.game;

public class GameState {
    private String playerName;
    private String mapState;
    private int heroPosX;
    private int heroPosY;
    private int arrowCount;
    private int stepCount;

    private int wumpusCount;

    // Konstruktor, getterek és setterek

    public GameState(String playerName, String mapState, int heroPosX, int heroPosY, int arrowCount, int stepCount, int wumpusCount) {
        this.playerName = playerName;
        this.mapState = mapState;
        this.heroPosX = heroPosX;
        this.heroPosY = heroPosY;
        this.arrowCount = arrowCount;
        this.stepCount = stepCount;
        this.wumpusCount = wumpusCount;
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
    // Getter a hős X pozíciójához
    public int getHeroPosX() {
        return heroPosX;
    }
    public int getHeroPosY() {
        return heroPosY;
    }
    public int getarrowCount() {
        return arrowCount;
    }
    public int getStepCount() {
        return stepCount;
    }

}

