package org.example.menu;

import org.example.database.DatabaseConnection;
import org.example.database.DatabaseService;
import org.example.game.GamePlay;
import org.example.game.GameState;
import org.example.game.Hero;
import org.example.game.LeaderboardEntry;
import org.example.map.MapID;
import org.example.map.MapReader;
import org.example.map.WayType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Scanner;

/**
 * The main menu for the game application, responsible for user interactions.
 */
public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private MapReader mapReader = new MapReader();
    private Hero hero;
    private DatabaseService dbService;
    private String username;
    private int initialStepCount;
    private int initialWumpusCount;

  //  private boolean hasGold;
    private static final Logger LOGGER =
          LoggerFactory.getLogger( GamePlay.class );

    /**
     * Constructs a Menu object and initializes the database service.
     */
    public Menu() {
        this.mapReader = new MapReader();
        DatabaseConnection dbConnection = new DatabaseConnection();
        this.dbService = new DatabaseService(dbConnection);
    }
    /**
     * Starts the game by prompting the user for their username.
     */
    public void startGame() {

        //   MapReader mapReader = new MapReader();
        System.out.print("Please enter your username: ");
        this.username = scanner.nextLine();

        if (MenuValidator.isValidUsername(username)) {
            System.out.println("Welcome, " + username + "!");
            dbService.insertPlayerName(username);
            displayMenu();
            LOGGER.info("Starting game for user: {}", username);
        } else {
            System.out.println("Invalid username. It must be between"
                    +
                    " 3 and 12 characters and not contain spaces.");
        }
    }
    /**
     * Displays the main menu and handles user choices.
     */
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
                  LOGGER.info("User {} selected MAP EDITOR", username);
                  System.out.println("Currently, map editing is not available."
                            +
                            " Please use the READ MAP FROM FILE option.");
                    break;
                case 2:
                    LOGGER.info("User {} selected READ MAP FROM FILE",
                            username);
                    System.out.println("READ MAP FROM FILE selected.");
                    boolean isMapValid = mapReader.readMapFromFile();
                    if (!isMapValid) {
                        displayMenu();
                    }
                    displaySubMenu();
                    break;
                case 3:
                    LOGGER.info("User {} selected READ FROM DATABASE",
                            username);
                    GameState loadedState = dbService.loadGameState(username);
                    if (loadedState != null) {
                        mapReader.setMapLinesFromString(
                                loadedState.getMapState()
                        );
                        hero = new Hero();
                        hero.setMapID(new MapID(loadedState.getHeroPosX(),
                                loadedState.getHeroPosY()));
                        hero.setArrowCount(loadedState.getarrowCount());
                        initialStepCount = loadedState.getStepCount();
                        initialWumpusCount = loadedState.getWumpusCount();
                        hero.setHasGold(loadedState.isHasGold());
                        GamePlay gamePlay = new GamePlay(
                                hero,
                                mapReader,
                                initialStepCount,
                                initialWumpusCount,
                                username
                        );
                        gamePlay.setHeroInitialPosition(
                                new MapID(loadedState.getHeroInitialPosX(),
                                        loadedState.getHeroInitialPosY())
                        );
                        continueGame(gamePlay);

                    } else {
                        System.out.println("No saved game found for "
                                +
                                username);
                    }
                    break;
                case 4:
                    LOGGER.info("User {} selected LIST THE LEADERBOARD",
                            username);
                    System.out.println("LIST THE LEADERBOARD selected.");
                    List<LeaderboardEntry> entries =
                            dbService.getLeaderboard();
                    for (LeaderboardEntry entry : entries) {
                        System.out.println(entry.getPlayerName()
                                + " - " + entry.getSteps() + " steps "
                                + entry.getWins() + " - " + " wins");
                    }
                    break;

                case 5:
                    LOGGER.info("User {} selected EXIT", username);
                    System.out.println("Exiting...");
                    return;
                default:
                    LOGGER.warn("User {} made an invalid choice: {}",
                            username, choice);
                    System.out.println("Invalid choice."
                            +
                            " Please enter 1, 2, or 3.");
            }
        }
    }

    private void continueGame(GamePlay gamePlay) {

        int choice;
        LOGGER.info("Continuing game for user: {}", username);
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
                    LOGGER.info("User {} changed hero direction to North", username);
                    gamePlay.changeHeroDirection(WayType.NORTH);
                    break;
                case 2:
                    LOGGER.info("User {} changed hero direction to East", username);
                    gamePlay.changeHeroDirection(WayType.EAST);
                    break;
                case 3:
                    LOGGER.info("User {} changed hero direction to West", username);
                    gamePlay.changeHeroDirection(WayType.WEST);
                    break;
                case 4:
                    LOGGER.info("User {} changed hero direction to South", username);
                    gamePlay.changeHeroDirection(WayType.SOUTH);
                    break;
                case 5:
                    LOGGER.info("User {} moved the hero", username);
                    gamePlay.moveHero();
                    break;
                case 6:
                    LOGGER.info("User {} shot an arrow", username);
                    gamePlay.shootArrow();
                    break;
                case 7:
                    LOGGER.info("User {} picked up the gold", username);
                    gamePlay.pickUpGold();
                    break;
                case 8:
                    LOGGER.info("User {} dropped the gold", username);
                    gamePlay.dropGold();
                    break;
                case 9:
                    LOGGER.info("User {} chose to save the game", username);
                    String mapState = getMapStateAsString();
                    int wumpusCount = gamePlay.getWumpusKilledCount();
                    int heroInitialPosX = gamePlay.getHeroInitialPosition().getHorizontal();
                    int heroInitialPosY = gamePlay.getHeroInitialPosition().getVertical();
                    dbService.saveGameState(username, mapState, hero.getMapID().getHorizontal(), hero.getMapID().getVertical(), heroInitialPosX, heroInitialPosY, hero.getArrowCount(), gamePlay.getStepCount(), wumpusCount, hero.isHasGold());                    System.out.println("Game saved successfully.");
                    break;

                case 11:
                    if (confirmExit()) {
                        LOGGER.info("User {} chose to give up the game", username);
                        System.out.println("You gave up the game.");
                        return;
                    }
                    break;
                default:
                    LOGGER.warn("User {} made an invalid choice: {}", username, choice);
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
                LOGGER.info("User {} selected GAME in sub menu", username);
                System.out.println("GAME selected.");
                mapReader.readMapFromFile();
                displayGameMenu();
                break;
            case 2:
                LOGGER.info("User {} returned to main menu", username);
                displayMenu();
                break;
            default:
                LOGGER.warn("User {} made an invalid choice in sub menu: {}", username, choice);
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        }
    }

    private void displayGameMenu() {
        int choice;
        if (mapReader == null) {
            mapReader = new MapReader();
        }
        if (hero == null) {
            hero = new Hero();
        }
        boolean isMapValid = mapReader.readMapFromFile();
        if (isMapValid) {
            int mapSize = mapReader.getMapSize();
            hero.initializeHero(mapSize);
            LOGGER.info("Game initialized for user {}", username);
        } else {
            LOGGER.warn("Map reading failed for user {}", username);
        }

        GamePlay gamePlay = new GamePlay(hero, mapReader, initialStepCount, initialWumpusCount, username);

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
                LOGGER.info("User {} won the game", username);
                displayMenu();
                return;
            }

            if (gamePlay.isGameOver()) {
                System.out.println("\nYou lost! Returning to main menu...\n");
                LOGGER.info("User {} lost the game", username);
                displayMenu();
                break;
            }

            System.out.print("\nPlease enter your choice (1-11): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    LOGGER.info("User {} changed direction to NORTH", username);
                    gamePlay.changeHeroDirection(WayType.NORTH);
                    break;
                case 2:
                    LOGGER.info("User {} changed direction to EAST", username);
                    gamePlay.changeHeroDirection(WayType.EAST);
                    break;
                case 3:
                    LOGGER.info("User {} changed direction to WEST", username);
                    gamePlay.changeHeroDirection(WayType.WEST);
                    break;
                case 4:
                    LOGGER.info("User {} changed direction to SOUTH", username);
                    gamePlay.changeHeroDirection(WayType.SOUTH);
                    break;
                case 5:
                    LOGGER.info("User {} moved hero", username);
                    gamePlay.moveHero();
                    break;
                case 6:
                    LOGGER.info("User {} shot an arrow", username);
                    gamePlay.shootArrow();
                    break;
                case 7:
                    LOGGER.info("User {} picked up gold", username);
                    gamePlay.pickUpGold();
                    break;
                case 8:
                    LOGGER.info("User {} dropped gold", username);
                    gamePlay.dropGold();
                    break;
                case 9:
                    String mapState = getMapStateAsString();
                    int wumpusCount = gamePlay.getWumpusKilledCount();
                    int heroInitialPosX = gamePlay.getHeroInitialPosition().getHorizontal();
                    int heroInitialPosY = gamePlay.getHeroInitialPosition().getVertical();
                    dbService.saveGameState(username, mapState, hero.getMapID().getHorizontal(), hero.getMapID().getVertical(), heroInitialPosX, heroInitialPosY, hero.getArrowCount(), gamePlay.getStepCount(), wumpusCount, hero.isHasGold());
                    LOGGER.info("Game state saved for user {}", username);
                    System.out.println("Game saved successfully.");
                    break;

                case 11:
                    if (confirmExit()) {
                        LOGGER.info("User {} gave up the game", username);
                        System.out.println("You gave up the game.");
                        return;
                    }
                    break;
                default:
                    LOGGER.warn("User {} made an invalid choice: {}", username, choice);
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
        boolean exitConfirmed = response.equalsIgnoreCase("Y");
        if (exitConfirmed) {
            LOGGER.info("User {} confirmed to exit the game", username);
        } else {
            LOGGER.info("User {} chose not to exit the game", username);
        }
        return exitConfirmed;
    }
}

