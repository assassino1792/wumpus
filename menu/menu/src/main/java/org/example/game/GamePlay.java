package org.example.game;

import org.example.map.MapID;
import org.example.map.WayType;
import org.example.map.MapReader;

public class GamePlay {

    private Hero hero;
    private int mapSize;
    // A pálya mérete
    private MapReader mapReader;




    public GamePlay(Hero hero, MapReader mapReader) {
        this.hero = hero;
        if (mapReader == null) {
            this.mapReader = new MapReader();
            this.mapReader.readMapFromFile(); // Feltételezve, hogy ez a metódus inicializálja a mapReader-t
        } else {
            this.mapReader = mapReader;
        }
        this.mapSize = mapReader.getMapSize();
        // Kezdeti beállítások, pl. a hős kezdeti pozíciója
        //this.hero.setMapID(new MapID(1, 1)); // Kezdeti pozíció (1,1)
        MapID heroInitialPosition = mapReader.getHeroInitialPosition();
        if (heroInitialPosition != null) {
            this.hero.setMapID(heroInitialPosition); // Beállítjuk a hős kezdeti pozícióját
        }
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

        // Ellenőrizzük, hogy a hős új pozíciójában van-e Pit (P)
        if (mapReader.getMapLines().get(vertical - 1).charAt(horizontal - 1) == 'P') {
            if (hero.getArrowCount() > 0) {
                hero.setArrowCount(hero.getArrowCount() - 1); // Csökkentjük a nyíl számát
                System.out.println("You stepped on a Pit! Lost an arrow. Remaining arrows: " + hero.getArrowCount()+"\n");
            }
        }

        hero.setMapID(new MapID(horizontal, vertical));

       // System.out.println("Hero's new position - Column: " + currentMapID.getHorizontal() + ", Row: " + currentMapID.getVertical());
    }
    public void shootArrow() {
        if (hero.getArrowCount() > 0) {
            MapID currentMapID = hero.getMapID();
            int horizontal = currentMapID.getHorizontal();
            int vertical = currentMapID.getVertical();

            boolean hitWall = false;
            boolean hitWumpus = false;
            while (!hitWall && !hitWumpus) {
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

                char targetChar = mapReader.getMapLines().get(vertical - 1).charAt(horizontal - 1);
                if (targetChar == 'W') {
                    hitWall = true; // A nyíl falba ütközött
                } else if (targetChar == 'U') {
                    hitWumpus = true;
                    mapReader.updateMapPosition(vertical - 1, horizontal - 1, '_'); // Frissítjük a pályát, eltávolítjuk a Wumpust
                }
            }

            hero.setArrowCount(hero.getArrowCount() - 1); // Csökkentjük a nyílak számát
            if (hitWumpus) {
                System.out.println("SCREEEEEEEEEAM! You hit a Wumpus! Remaining arrows: " + hero.getArrowCount());
            } else if (hitWall) {
                System.out.println("Arrow hit a wall and got destroyed. Remaining arrows: " + hero.getArrowCount());
            } else {
                System.out.println("Shot an arrow towards " + hero.getWay() + ". Remaining arrows: " + hero.getArrowCount());
            }
        } else {
            System.out.println("No more arrows left.");
        }
    }
}


