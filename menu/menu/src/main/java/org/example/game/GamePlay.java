package org.example.game;

import org.example.map.MapID;
import org.example.map.WayType;

public class GamePlay {

    private Hero hero;
    private int mapSize; // A pálya mérete

    public GamePlay(Hero hero, int mapSize) {
        this.hero = hero;
        this.mapSize = mapSize;
        // Kezdeti beállítások, pl. a hős kezdeti pozíciója
        this.hero.setMapID(new MapID(1, 1)); // Kezdeti pozíció (1,1)
    }

    public void changeHeroDirection(WayType newDirection) {
        hero.setWay(newDirection);
        System.out.println("You are now looking " + newDirection);
    }

    public void moveHero() {
        MapID currentMapID = hero.getMapID();
        int horizontal = currentMapID.getHorizontal();
        int vertical = currentMapID.getVertical();

        switch (hero.getWay()) {
            case NORTH:
                if (vertical > 1) vertical--;
                break;
            case EAST:
                if (horizontal < mapSize) horizontal++;
                break;
            case SOUTH:
                if (vertical < mapSize) vertical++;
                break;
            case WEST:
                if (horizontal > 1) horizontal--;
                break;
        }

        hero.setMapID(new MapID(horizontal, vertical));
        System.out.println("Moved to column: " + horizontal + ", row: " + vertical);
    }
}
