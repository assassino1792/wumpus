package org.example.game;

import org.example.database.DatabaseConnection;
import org.example.database.DatabaseService;
import org.example.map.MapID;
import org.example.map.MapReader;
import org.example.map.WayType;

public class GamePlay {

    private Hero hero;
    private String playerName;
    private int mapSize;
    private MapReader mapReader;
    private boolean gameOver = false;
    private int stepsCount=0;
    private int wumpusKilledCount;
    private boolean gameWon=false;
    private MapID heroInitialPosition;
    private int heroInitialPosX;
    private int heroInitialPosY;


    public GamePlay(Hero hero, MapReader mapReader, int initialStepCount, int initialWumpusCount, String playerName) {
        this.hero = hero;
        this.playerName = playerName;
        this.stepsCount = initialStepCount;
        this.wumpusKilledCount = initialWumpusCount;
        if (mapReader == null) {
            this.mapReader = new MapReader();
            this.mapReader.readMapFromFile();
        } else {
            this.mapReader = mapReader;
        }
        this.mapSize = mapReader.getMapSize();

        // Kezdeti beállítások, pl. a hős kezdeti pozíciója
        MapID heroInitialPosition = mapReader.getHeroInitialPosition();
        this.heroInitialPosition = mapReader.getHeroInitialPosition();
        if (heroInitialPosition != null) {
            this.hero.setMapID(heroInitialPosition); // Beállítjuk a hős kezdeti pozícióját
        }
    }
    public void setHeroInitialPosition(MapID heroInitialPosition) {
        this.heroInitialPosition = heroInitialPosition;
    }
    public void changeHeroDirection(WayType newDirection) {
        hero.setWay(newDirection);
        System.out.println("You are now looking " + newDirection);
    }

    public int getStepCount() {
        return stepsCount;
    }

    public boolean hasWon() {
        if (hero.isHasGold()) {
            MapID currentPos = hero.getMapID();
            MapID initialPos = this.heroInitialPosition;
            if (currentPos.getHorizontal() == initialPos.getHorizontal() && currentPos.getVertical() == initialPos.getVertical()) {
                System.out.println("\nCongratulations! You are clever! You have successfully returned the gold to the starting position in " + stepsCount + " steps. YOU WON!\n");
                gameWon = true;

                DatabaseService dbService = new DatabaseService(new DatabaseConnection());
                dbService.insertOrUpdateLeaderboard(this.playerName, stepsCount);

                LeaderBoard leaderboard = new LeaderBoard(dbService);
                leaderboard.updateLeaderboard(this.playerName, stepsCount);

                return true;
            }
        }
        return false;
    }

    public boolean isGameWon() {
        return gameWon;
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


        char targetChar = mapReader.getMapLines().get(vertical - 1).charAt(horizontal - 1);
        if (targetChar == 'U') {
            System.out.println("\nYou stepped on a Wumpus! GAME OVER.\n");
            gameOver = true;
            return;
        }else if (targetChar == 'W') {
                System.out.println("\nCannot move onto a wall!\n");
            return;
        }else {
                hero.setMapID(new MapID(horizontal, vertical));
                System.out.println("Hero's new position - Column: " + horizontal + ", Row: " + vertical);
            }

        // Ellenőrizzük, hogy a hős új pozíciójában van-e Pit (P)
        if (mapReader.getMapLines().get(vertical - 1).charAt(horizontal - 1) == 'P') {
            if (hero.getArrowCount() > 0) {
                hero.setArrowCount(hero.getArrowCount() - 1); // Csökkentjük a nyílak számát
                System.out.println("You stepped on a Pit! Lost an arrow. Remaining arrows: " + hero.getArrowCount()+"\n");
            }
        }
        hero.setMapID(new MapID(horizontal, vertical));

       //DEBUG
       // System.out.println("Hero's current position: " + hero.getMapID());
       // System.out.println("Hero's initial position: " + mapReader.getHeroInitialPosition());

        if (hero.isHasGold() && hero.getMapID().equals(this.heroInitialPosition)) {
            hasWon();
        }

        stepsCount++;
       // System.out.println("Hero's new position - Column: " + currentMapID.getHorizontal() + ", Row: " + currentMapID.getVertical());
    }




    public boolean isGameOver() {
        return gameOver;
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
                wumpusKilledCount++;
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



    public void pickUpGold() {
        MapID heroPosition = hero.getMapID();
        int row = heroPosition.getVertical() - 1;
        int column = heroPosition.getHorizontal() - 1;

        if (mapReader.getMapLines().get(row).charAt(column) == 'G') {
            hero.setHasGold(true); // A hős felvette az aranyat
            mapReader.updateMapPosition(row, column, '_'); // Eltávolítjuk az aranyat a pályáról
            System.out.println("You picked up the gold!");
        } else {
            System.out.println("No gold here to pick up.");
        }
    }
    public void dropGold() {
        if (hero.isHasGold()) { // Itt használjuk az új getter metódust
            MapID heroPosition = hero.getMapID();
            int row = heroPosition.getVertical() - 1;
            int column = heroPosition.getHorizontal() - 1;

            hero.setHasGold(false); // A hős letette az aranyat
            mapReader.updateMapPosition(row, column, 'G'); // Helyezzük vissza az aranyat a pályára
            System.out.println("You dropped the gold!");
        } else {
            System.out.println("You don't have any gold to drop.");
        }
    }
    public int getWumpusKilledCount() {
        return wumpusKilledCount;
    }
    public MapID getHeroInitialPosition() {
        return heroInitialPosition;
    }


}
