package org.example.game;

import org.example.database.DatabaseConnection;
import org.example.database.DatabaseService;
import org.example.map.MapID;
import org.example.map.MapReader;
import org.example.map.WayType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GamePlay class to manage the gameplay mechanics.
 */
public class GamePlay {
    /** The hero character in the game. */
    private Hero hero;
    /** The name of the player. */
    private String playerName;
    /** The size of the game map. */
    private int mapSize;
    /** Reader to read and manage the game map. */
    private MapReader mapReader;
    /** Flag to indicate if the game is over. */
    private boolean gameOver = false;
    /** Count of steps taken by the player. */
    private int stepsCount = 0;
    /** Count of Wumpuses killed in the game. */
    private int wumpusKilledCount;
    /** Flag to indicate if the game is won. */
    private boolean gameWon = false;
    /** Initial position of the hero on the map. */
    private MapID heroInitialPosition;
    /** Logger for logging game events and information. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(GamePlay.class);

    /**
     * Initializes the gameplay with the specified parameters.
     * @param knight The knight character in the game.
     * @param mapLoader The reader to read and manage the game map.
     * @param initialStepCount Initial count of steps.
     * @param initialWumpusCount Initial count of Wumpuses killed.
     * @param playername The name of the player.
     */
    public GamePlay(
           final Hero knight,
           final MapReader mapLoader,
           final int initialStepCount,
           final int initialWumpusCount,
           final String playername) {

        this.hero = knight;
        this.playerName = playername;
        this.stepsCount = initialStepCount;
        this.wumpusKilledCount = initialWumpusCount;

        if (mapLoader == null) {
            this.mapReader = new MapReader();
            this.mapReader.readMapFromFile();
        } else {
            this.mapReader = mapLoader;
        }
        this.mapSize = mapLoader.getMapSize();
        LOGGER.info("GamePlay initialized for player: {}",
                playername);

        MapID heroinitialposition = mapLoader.getHeroInitialPosition();
        this.heroInitialPosition = mapLoader.getHeroInitialPosition();
        if (heroinitialposition != null) {
            this.hero.setMapID(heroinitialposition);
        }
    }
    /**
     * Sets the initial position of the hero on the map.
     * @param newheroInitialPosition The new initial position
     * to be set for the hero.
     */
    public void setHeroInitialPosition(final MapID newheroInitialPosition) {
        this.heroInitialPosition = newheroInitialPosition;
    }
    /**
     * Changes the direction in which the hero is facing.
     * @param newDirection The new direction for the hero to face.
     */
    public void changeHeroDirection(final WayType newDirection) {
        hero.setWay(newDirection);
        System.out.println("You are now looking " + newDirection);
    }

    /**
     * Retrieves the current step count of the game.
     * @return The number of steps taken by the player so far.
     */
    public int getStepCount() {
        return stepsCount;
    }

    /**
     * Checks if the player has won the game.
     * A player wins the game if they have collected
     * the gold and returned it to the starting position.
     * @return true if the player has won the game, false otherwise.
     */
    public boolean hasWon() {
        if (hero.isHasGold()) {
            MapID currentPos = hero.getMapID();
            MapID initialPos = this.heroInitialPosition;
            if (currentPos.getHorizontal() == initialPos.getHorizontal()
                    && currentPos.getVertical() == initialPos.getVertical()) {
                System.out.println("\nCongratulations! You are clever!"
                        +
                        " You have successfully returned the gold"
                        +
                        " to the starting"
                        +
                        " position in " + stepsCount + " steps. YOU WON!\n");
                gameWon = true;
                LOGGER.info("Player '{}' has won the game in {} steps",
                        playerName, stepsCount);

                DatabaseService dbService =
                        new DatabaseService(new DatabaseConnection());
                dbService.insertOrUpdateLeaderboard(this.playerName,
                        stepsCount, true);

                LeaderBoard leaderboard = new LeaderBoard(dbService);
                leaderboard.updateLeaderboard(this.playerName,
                        stepsCount, true);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the game has been won.
     * @return true if the game is won, false otherwise.
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Moves the hero based on their current direction.
     * This method updates the hero's position on the map
     * .If the hero encounters a Wumpus or a wall,
     * the game ends or the move is blocked, respectively.
     * If the hero steps on a Pit, they lose an arrow.
     * The method also checks if the hero has won the game
     * by returning the gold to the starting position.
     */
    public void moveHero() {
        MapID currentMapID = hero.getMapID();
        int horizontal = currentMapID.getHorizontal();
        int vertical = currentMapID.getVertical();


        switch (hero.getWay()) {
            case NORTH:
                if (vertical > 1) {
                    vertical--;
                }
                break;
            case EAST:
                if (horizontal < mapSize) {
                    horizontal++;
                }
                break;
            case SOUTH:
                if (vertical < mapSize) {
                    vertical++;
                }
                break;
            case WEST:
                if (horizontal > 1) {
                    horizontal--;
                }
                break;
            default:
                break;
        }

        char targetChar = mapReader.getMapLines().get(vertical - 1)
                .charAt(horizontal - 1);
        if (targetChar == 'U') {
            System.out.println("\nYou stepped on a Wumpus! GAME OVER.\n");
            gameOver = true;
            return;
        } else if (targetChar == 'W') {
                System.out.println("\nCannot move onto a wall!\n");
            return;
        } else {
                hero.setMapID(new MapID(horizontal, vertical));
                System.out.println("Hero's new position - Column:"
                        +
                        " " + horizontal + ", Row: " + vertical);
            }

        if (mapReader.getMapLines().get(vertical - 1)
                .charAt(horizontal - 1) == 'P') {
            if (hero.getArrowCount() > 0) {
                hero.setArrowCount(hero.getArrowCount() - 1);
                System.out.println("You stepped on a Pit! Lost an arrow."
                        +
                        "Remaining arrows: " + hero.getArrowCount());
            }
        }
        hero.setMapID(new MapID(horizontal, vertical));


        if (hero.isHasGold() && hero.getMapID().equals(
                this.heroInitialPosition)) {
            hasWon();
        }
        stepsCount++;
    }

    /**
     * Checks if the game is over.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Shoots an arrow in the current direction.
     * This method allows the hero to shoot an arrow
     * in their current direction. It checks if the arrow hits a Wumpus,
     * a wall, or if it is destroyed.
     * It updates the game state accordingly,
     * including the remaining arrow count and
     * the Wumpus kill count.
     */
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
                        if (vertical > 1) {
                            vertical--;
                        }
                        break;
                    case EAST:
                        if (horizontal < mapSize) {
                            horizontal++;
                        }
                        break;
                    case SOUTH:
                        if (vertical < mapSize) {
                            vertical++;
                        }
                        break;
                    case WEST:
                        if (horizontal > 1) {
                            horizontal--;
                        }
                        break;
                    default:
                        break;
                }
                char targetChar = mapReader.getMapLines().get(vertical - 1)
                        .charAt(horizontal - 1);
                if (targetChar == 'W') {
                    hitWall = true;
                } else if (targetChar == 'U') {
                    hitWumpus = true;
                    mapReader.updateMapPosition(vertical - 1,
                            horizontal - 1, '_');
                }
            }

            hero.setArrowCount(hero.getArrowCount() - 1);
            if (hitWumpus) {
                wumpusKilledCount++;
                System.out.println("SCREEEEEEEEEAM! You hit a Wumpus!"
                        +
                        " Remaining arrows: " + hero.getArrowCount());
                LOGGER.info("Player '{}' hit a Wumpus at position - "
                        +
                        "Column: {}, Row: {}",
                        playerName, horizontal, vertical);
            } else if (hitWall) {
                System.out.println("Arrow hit a wall and got destroyed."
                        +
                        " Remaining arrows: " + hero.getArrowCount());
                LOGGER.warn("Player '{}' arrow hit a wall at position"
                        +
                        " - Column: {}, Row: {}",
                        playerName, horizontal, vertical);
            } else {
                System.out.println("Shot an arrow towards " + hero.getWay()
                        +
                        ". Remaining arrows: " + hero.getArrowCount());
            }
        } else {
            System.out.println("No more arrows left.");
        }
    }

    /**
     * Allows the hero to pick up gold from their
     * current position on the map.
     * This method allows the hero to pick up gold if
     * it is present at their current position on the map. It updates
     * the game state, setting the hero's gold status to
     * true and updating the map to indicate the absence of gold
     * at that location.
     */
    public void pickUpGold() {
        MapID heroPosition = hero.getMapID();
        int row = heroPosition.getVertical() - 1;
        int column = heroPosition.getHorizontal() - 1;

        if (mapReader.getMapLines().get(row).charAt(column) == 'G') {
            hero.setHasGold(true);
            mapReader.updateMapPosition(row, column, '_');
            System.out.println("You picked up the gold!");
            LOGGER.info("Player '{}' picked up the gold at position"
                    +
                    " - Column: {}, Row: {}", playerName, column + 1, row + 1);
        } else {
            System.out.println("No gold here to pick up.");
        }
    }

    /**
     * Allows the hero to drop the gold they are
     * carrying at their current position on the map.
     * This method allows the hero to drop the gold
     * they are carrying at their current position on the map.
     * It updates the game state, setting the hero's
     * gold status to false and updating the map to indicate the
     * presence of gold at that location.
     */
    public void dropGold() {
        if (hero.isHasGold()) {
            MapID heroPosition = hero.getMapID();
            int row = heroPosition.getVertical() - 1;
            int column = heroPosition.getHorizontal() - 1;

            hero.setHasGold(false);
            mapReader.updateMapPosition(row, column, 'G');
            System.out.println("You dropped the gold!");
            LOGGER.info("Player '{}' dropped the gold at position"
                    +
                    " - Column: {}, Row: {}", playerName, column + 1, row + 1);
        } else {
            System.out.println("You don't have any gold to drop.");
        }
    }

    /**
     * Returns the count of Wumpuses killed by the player.
     * @return The count of Wumpuses killed.
     */
    public int getWumpusKilledCount() {
        return wumpusKilledCount;
    }

    /**
     * Returns the initial position of the hero on the map.
     * @return The initial position of the hero as a MapID object.
     */
    public MapID getHeroInitialPosition() {
        return heroInitialPosition;
    }
}
