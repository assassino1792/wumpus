package org.example.menu;

import org.example.database.DatabaseConnection;
import org.example.database.DatabaseService;
import org.example.game.GamePlay;
import org.example.game.GameState;
import org.example.game.Hero;
import org.example.map.MapReader;
import org.example.map.WayType;
import org.example.map.MapID;


import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private MapReader mapReader = new MapReader();
    private Hero hero;
    private DatabaseService dbService;
    private String username;
    private int initialStepCount;
    private int initialWumpusCount;

    private boolean hasGold;

    public Menu() {
        this.mapReader = new MapReader();
        DatabaseConnection dbConnection = new DatabaseConnection();
        this.dbService = new DatabaseService(dbConnection);
    }

    public void startGame() {

        //   MapReader mapReader = new MapReader();
        System.out.print("Please enter your username: ");
        this.username = scanner.nextLine();

        if (MenuValidator.isValidUsername(username)) {
            System.out.println("Welcome, " + username + "!");
            dbService.insertPlayerName(username); // Itt mentjük el az adatbázisba
            displayMenu();
        } else {
            System.out.println("Invalid username. It must be between 3 and 12 characters and not contain spaces.");
        }
    }

    public void displayMenu() {
        int choice;

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. MAP EDITOR");
            System.out.println("2. READ MAP FROM FILE");
            System.out.println("3. READ FROM DATABASE");
            System.out.println("4. LIST THE LEADERBOARD");
            System.out.println("5. EXIT");
            System.out.print("Please enter your choice (1-3): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Currently, map editing is not available. Please use the READ MAP FROM FILE option.");
                    break;
                case 2:
                    System.out.println("READ MAP FROM FILE selected.");
                    boolean isMapValid = mapReader.readMapFromFile();
                    if (!isMapValid) {
                        displayMenu();
                    }
                    displaySubMenu();
                    break;
                case 3:
                    GameState loadedState = dbService.loadGameState(username);
                    if (loadedState != null) {
                        mapReader.setMapLinesFromString(loadedState.getMapState());
                        hero = new Hero();
                        hero.setMapID(new MapID(loadedState.getHeroPosX(), loadedState.getHeroPosY()));
                        hero.setArrowCount(loadedState.getarrowCount());
                        initialStepCount = loadedState.getStepCount();
                        initialWumpusCount = loadedState.getWumpusCount();
                        hero.setHasGold(loadedState.isHasGold());
                        GamePlay gamePlay = new GamePlay(hero, mapReader, initialStepCount, initialWumpusCount);
                        gamePlay.setHeroInitialPosition(new MapID(loadedState.getHeroInitialPosX(), loadedState.getHeroInitialPosY()));
                        continueGame(gamePlay);

                    } else {
                        System.out.println("No saved game found for " + username);
                    }
                    break;
                case 4:
                    break;

                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    private void continueGame(GamePlay gamePlay) {

        int choice;
        System.out.println("\nContinuing the game.\n");

        while (true) {

            System.out.println("\nRemaining arrows: " + hero.getArrowCount());
            System.out.println("\nGame Menu:\n");
            System.out.println("1. Look North");
            System.out.println("2. Look East");
            System.out.println("3. Look West");
            System.out.println("4. Look South");
            System.out.println("5. Move");
            System.out.println("6. Shoot");
            System.out.println("7. Pick up the gold");
            System.out.println("8. Drop the gold");
            System.out.println("9. Save");
            System.out.println("10. Suspend");
            System.out.println("11. Give up the game");


            if (gamePlay.isGameWon()) {
                displayMenu();
                return;
            }

            if (gamePlay.isGameOver()) {
                System.out.println("\nYou lost! Returning to main menu...\n");
                displayMenu();
                break;
            }

            System.out.print("\nPlease enter your choice (1-11): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    gamePlay.changeHeroDirection(WayType.NORTH);
                    break;
                case 2:
                    gamePlay.changeHeroDirection(WayType.EAST);
                    break;
                case 3:
                    gamePlay.changeHeroDirection(WayType.WEST);
                    break;
                case 4:
                    gamePlay.changeHeroDirection(WayType.SOUTH);
                    break;
                case 5:
                    gamePlay.moveHero();
                    break;
                case 6:
                    gamePlay.shootArrow();
                    break;
                case 7:
                    gamePlay.pickUpGold();
                    break;
                case 8:
                    gamePlay.dropGold();
                    break;
                case 9:
                    String mapState = getMapStateAsString();
                    int wumpusCount = gamePlay.getWumpusKilledCount();
                    int heroInitialPosX = gamePlay.getHeroInitialPosition().getHorizontal();
                    int heroInitialPosY = gamePlay.getHeroInitialPosition().getVertical();
                    dbService.saveGameState(username, mapState, hero.getMapID().getHorizontal(), hero.getMapID().getVertical(), heroInitialPosX, heroInitialPosY, hero.getArrowCount(), gamePlay.getStepCount(), wumpusCount, hero.isHasGold());                    System.out.println("Game saved successfully.");
                    break;

                case 11:
                    if (confirmExit()) {
                        System.out.println("You gave up the game.");
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 11.");
            }

            mapReader.redrawMap(hero.getMapID());

        }
    }



    private void displaySubMenu() {
        int choice;

        System.out.println("\nSub Menu:");
        System.out.println("1. GAME");
        System.out.println("2. RETURN TO MAIN MENU");
        System.out.print("Please enter your choice (1-3): ");

        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("GAME selected.");
                mapReader.readMapFromFile();
                displayGameMenu();
                break;
            case 2:
                displayMenu();
                break;
            default:
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        }
    }

    private void displayGameMenu() {
        int choice;
        if (mapReader == null) {
            mapReader = new MapReader();
        }
        if (hero == null) {
            hero = new Hero(); // Inicializáljuk a hőst
        }
        boolean isMapValid = mapReader.readMapFromFile();
        if (isMapValid) {
            int mapSize = mapReader.getMapSize(); // Lekérjük a pálya méretét
            hero.initializeHero(mapSize); // Inicializáljuk a hőst a pálya méretével
        }

        GamePlay gamePlay = new GamePlay(hero, mapReader, initialStepCount, initialWumpusCount);

        System.out.println("\nThe game has started.\n");

        while (true) {
            System.out.println("\nRemaining arrows: " + hero.getArrowCount());
            System.out.println("\nGame Menu:\n");
            System.out.println("1. Look North");
            System.out.println("2. Look East");
            System.out.println("3. Look West");
            System.out.println("4. Look South");
            System.out.println("5. Move");
            System.out.println("6. Shoot");
            System.out.println("7. Pick up the gold");
            System.out.println("8. Drop the gold");
            System.out.println("9. Save");
            System.out.println("10. Suspend");
            System.out.println("11. Give up the game");

            if (gamePlay.isGameWon()) {
                displayMenu();
                return;
            }

            if (gamePlay.isGameOver()) {
                System.out.println("\nYou lost! Returning to main menu...\n");
                displayMenu();
                break;
            }

            System.out.print("\nPlease enter your choice (1-11): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    gamePlay.changeHeroDirection(WayType.NORTH);
                    break;
                case 2:
                    gamePlay.changeHeroDirection(WayType.EAST);
                    break;
                case 3:
                    gamePlay.changeHeroDirection(WayType.WEST);
                    break;
                case 4:
                    gamePlay.changeHeroDirection(WayType.SOUTH);
                    break;
                case 5:
                    gamePlay.moveHero();
                    break;
                case 6:
                    gamePlay.shootArrow();
                    break;
                case 7:
                    gamePlay.pickUpGold();
                    break;
                case 8:
                    gamePlay.dropGold();
                    break;
                case 9:
                    String mapState = getMapStateAsString();
                    int wumpusCount = gamePlay.getWumpusKilledCount();
                    int heroInitialPosX = gamePlay.getHeroInitialPosition().getHorizontal();
                    int heroInitialPosY = gamePlay.getHeroInitialPosition().getVertical();
                    dbService.saveGameState(username, mapState, hero.getMapID().getHorizontal(), hero.getMapID().getVertical(), heroInitialPosX, heroInitialPosY, hero.getArrowCount(), gamePlay.getStepCount(), wumpusCount, hero.isHasGold());
                    System.out.println("Game saved successfully.");
                    break;

                case 11:
                    if (confirmExit()) {
                        System.out.println("You gave up the game.");
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 11.");
            }

            mapReader.redrawMap(hero.getMapID());
        }
    }

    private String getMapStateAsString() {
        StringBuilder sb = new StringBuilder();
        for (String line : mapReader.getMapLines()) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }



    private boolean confirmExit() {
        System.out.print("Are you sure you want to give up the game? (Y/N): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("Y");
    }
}

