package org.example.game;

import org.example.map.MapID;
import org.example.map.WayType;
import org.example.map.MapValidator;

public class Hero {


    private MapID mapID;
    private WayType way;
    private int arrowCount;


    public Hero() {}
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
    public MapID getMapID() {
        return mapID;
    }
    public void setMapID(MapID mapID) {
        this.mapID = mapID;
    }
    private boolean hasGold = false;
    public boolean isHasGold() { return hasGold; }
    public void setHasGold(boolean hasGold) {this.hasGold = hasGold;}
    public void initializeHero(int mapSize) {
        this.arrowCount = MapValidator.WumpusCount(mapSize);
        this.way = WayType.NORTH;
    }

}
