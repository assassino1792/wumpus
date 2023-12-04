package org.wumpus.database.entity;


import org.wumpus.model.WayType;
import jakarta.persistence.*;

@Entity
public class HeroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long heroId;

    @Column(nullable = false)
    private String playerName;
    @Column(nullable = false)
    private int horizontal;
    @Column(nullable = false)
    private int vertical;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WayType wayType;

    @Column(nullable = false)
    private int arrowCount;

    @Column(nullable = false)
    private boolean hasGold;

    @Column(nullable = false)
    private int moveCount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private GameMapEntity gameMap;

    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long heroId) {
        this.heroId = heroId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public WayType getWayType() {
        return wayType;
    }

    public void setWayType(WayType wayType) {
        this.wayType = wayType;
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

    public GameMapEntity getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMapEntity gameMap) {
        this.gameMap = gameMap;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
}
