package org.example.game;

import org.example.database.DatabaseService;

/**
 * A class representing the leaderboard for the game.
 */
public class LeaderBoard {
    /** The database service for leaderboard data storage. */
    private DatabaseService dbService;
    /**
     * Initializes a new instance of the LeaderBoard class.
     * @param databaseService The database service
     * used for leaderboard data storage.
     */
    public LeaderBoard(final DatabaseService databaseService) {
        this.dbService = databaseService;
    }
    /**
     * Updates the leaderboard with the player's information.
     * @param playerName The name of the player.
     * @param steps The number of steps taken by the player.
     * @param hasWon Indicates whether the player has won the game.
     */
    public void updateLeaderboard(
            final String playerName,
            final int steps,
            final boolean hasWon) {
        dbService.insertOrUpdateLeaderboard(playerName, steps, hasWon);
    }

}



