package org.example.database;

import org.example.game.GameState;
import org.example.game.LeaderboardEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides services for interacting with the database.
 */
public class DatabaseService {
    /** Logger for this class. */
    private static final Logger LOGGER
    = LoggerFactory.getLogger(DatabaseService.class);
    /** Database connection instance. */
    private final DatabaseConnection dbConnection;
    /** SQL query for updating win count in the leaderboard. */
    private static final String WIN_UPDATE_SQL
    = "UPDATE Leaderboard SET Wins = Wins + 1 WHERE PlayerName = ?";

    /**
     * Constructs a new DatabaseService
     * with the given database connection.
     *@param connection The database connection to use.
     */
    public DatabaseService(final DatabaseConnection connection) {
        this.dbConnection = connection;
    }
    /**
    * Inserts a player's name into the PlayerNames table.
    * @param playerName The name of the player to insert.
    */
    public void insertPlayerName(final String playerName) {
        String sql
        = "INSERT INTO PlayerNames (PlayerName) VALUES (?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             pstmt.setString(1, playerName);
             pstmt.executeUpdate();
             LOGGER.info("Player name '{}'"
                     +
                     " inserted into PlayerNames table", playerName);
        } catch (SQLException e) {
            LOGGER.error("Error inserting player name"
                    +
                    " into PlayerNames table", e);
        }
    }

    /** Index for the player name in prepared statements. */
    private static final int PLAYER_NAME_INDEX = 1;
    /** Index for the map state in prepared statements. */
    private static final int MAP_STATE_INDEX = 2;
    /** Index for the hero's X position in prepared statements. */
    private static final int HERO_POS_X_INDEX = 3;
    /** Index for the hero's Y position in prepared statements. */
    private static final int HERO_POS_Y_INDEX = 4;
    /** Index for the hero's initial X position in prepared statements. */
    private static final int HERO_INIT_POS_X_INDEX = 5;
    /** Index for the hero's initial Y position in prepared statements. */
    private static final int HERO_INIT_POS_Y_INDEX = 6;
    /** Index for the arrow count in prepared statements. */
    private static final int ARROW_COUNT_INDEX = 7;
    /** Index for the step count in prepared statements. */
    private static final int STEP_COUNT_INDEX = 8;
    /** Index for the Wumpus killed count in prepared statements. */
    private static final int WUMPUS_KILLED_COUNT_INDEX = 9;
    /** Index for the has gold flag in prepared statements. */
    private static final int HAS_GOLD_INDEX = 10;
    /** Index for the timestamp in prepared statements. */
    private static final int TIMESTAMP_INDEX = 11;
    /**
    * Saves the current game state for a player.
     * Saves the current game state for a player.
     * @param playerName The name of the player.
     * @param mapState The current state of the map.
     * @param heroPosX The hero's current X position.
     * @param heroPosY The hero's current Y position.
     * @param heroInitialPosX The hero's initial X position.
     * @param heroInitialPosY The hero's initial Y position.
     * @param arrowCount The number of arrows the hero has.
     * @param stepCount The number of steps taken by the hero.
     * @param wumpusKilledCount The number of Wumpuses killed by the hero.
     * @param hasGold Whether the hero has the gold.
     */
    public void saveGameState(final String playerName,
                              final String mapState,
                              final int heroPosX,
                              final int heroPosY,
                              final int heroInitialPosX,
                              final int heroInitialPosY,
                              final int arrowCount,
                              final int stepCount,
                              final int wumpusKilledCount,
                              final boolean hasGold) {

        String sql = "INSERT INTO GameState (PlayerName,"
                     +
                     " MapState, HeroPositionX,"
                     +
                     "HeroPositionY, HeroInitialPositionX, "
                     +
                     "HeroInitialPositionY, ArrowCount,"
                     +
                     "StepCount, wumpusKilledCount, hasGold, Timestamp) VALUES"
                     +
                     " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(PLAYER_NAME_INDEX, playerName);
            pstmt.setString(MAP_STATE_INDEX, mapState);
            pstmt.setInt(HERO_POS_X_INDEX, heroPosX);
            pstmt.setInt(HERO_POS_Y_INDEX, heroPosY);
            pstmt.setInt(HERO_INIT_POS_X_INDEX, heroInitialPosX);
            pstmt.setInt(HERO_INIT_POS_Y_INDEX, heroInitialPosY);
            pstmt.setInt(ARROW_COUNT_INDEX, arrowCount);
            pstmt.setInt(STEP_COUNT_INDEX, stepCount);
            pstmt.setInt(WUMPUS_KILLED_COUNT_INDEX, wumpusKilledCount);
            pstmt.setBoolean(HAS_GOLD_INDEX, hasGold);
            pstmt.setTimestamp(TIMESTAMP_INDEX,
                    new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            LOGGER.info("Game state saved for player '{}'. Step count: {},"
                    +
                    " Wumpus killed: {}, Has gold: {}", playerName,
                    stepCount, wumpusKilledCount, hasGold);
        } catch (SQLException e) {
            LOGGER.error("Error saving game state for player '{}'",
                    playerName, e);
        }
    }

    /**
     * Loads the game state for a given player.
     * @param playerName The name of the player
     * whose game state is to be loaded.
     * @return The loaded game state, or null if no state is found.
     */
    public GameState loadGameState(final String playerName) {
        String sql = "SELECT * FROM GameState WHERE PlayerName = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String mapState = rs.getString("MapState");
                int heroPosX = rs.getInt("HeroPositionX");
                int heroPosY = rs.getInt("HeroPositionY");
                int heroInitialPosX = rs.getInt("HeroInitialPositionX");
                int heroInitialPosY = rs.getInt("HeroInitialPositionY");
                int arrowCount = rs.getInt("ArrowCount");
                int stepCount = rs.getInt("StepCount");
                int wumpusKilledCount = rs.getInt("wumpusKilledCount");
                boolean hasGold = rs.getBoolean("hasGold");
                LOGGER.info("Game state loaded successfully for player '{}'",
                        playerName);
                return new GameState(playerName, mapState, heroPosX, heroPosY,
                heroInitialPosX, heroInitialPosY, arrowCount, stepCount,
                wumpusKilledCount, hasGold);
            } else {
                LOGGER.info("No game state found for player '{}'",
                        playerName);
            }
        } catch (SQLException e) {
            LOGGER.error("Error loading game state for player '{}'",
                    playerName, e);
        }
        return null;
    }
    /**
     * Inserts or updates a player's entry in the leaderboard.
     * @param playerName The name of the player.
     * @param steps The number of steps taken by the player.
     * @param hasWon Whether the player has won the game.
     */
    public void insertOrUpdateLeaderboard(
            final String playerName,
            final int steps,
            final boolean hasWon) {

        String checkSql = "SELECT Steps FROM Leaderboard"
                +
                " WHERE PlayerName = ?";
        String insertSql = "INSERT INTO Leaderboard "
                +
                "(PlayerName, Steps, Wins) VALUES (?, ?, ?)";
        String updateSql = "UPDATE Leaderboard SET Steps = ? "
                +
                "WHERE PlayerName = ? AND ? < Steps";
        boolean shouldUpdateWins = false;

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, playerName);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int existingSteps = rs.getInt("Steps");
                if (steps < existingSteps) {
                    try (PreparedStatement updateStmt =
                                 conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, steps);
                        updateStmt.setString(2, playerName);
                        updateStmt.setInt(3, steps);
                        updateStmt.executeUpdate();
                        LOGGER.info("Leaderboard updated for player '{}':"
                                +
                                " new steps count is {}", playerName, steps);
                        shouldUpdateWins = true;
                    }
                } else {
                    LOGGER.info("No update required for player '{}'"
                                    +
                            " on leaderboard: existing steps count is lower",
                            playerName);
                }
            } else {
                try (PreparedStatement insertStmt =
                             conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, playerName);
                    insertStmt.setInt(2, steps);
                    insertStmt.setInt(3, hasWon ? 1 : 0);
                    insertStmt.executeUpdate();
                    LOGGER.info("New leaderboard entry created for player '{}':"
                                    +
                            " steps count is {}, wins: {}",
                            playerName, steps, hasWon ? 1 : 0);
                }
            }
            if (hasWon && shouldUpdateWins) {
                try (PreparedStatement winUpdateStmt =
                             conn.prepareStatement(WIN_UPDATE_SQL)) {
                    winUpdateStmt.setString(1, playerName);
                    winUpdateStmt.executeUpdate();
                    LOGGER.info("Incremented win count for player '{}'",
                            playerName);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating or inserting leaderboard"
                    +
                    " for player '{}'", playerName, e);
        }
    }
    /**
     * Retrieves the leaderboard from the database.
     * @return A list of LeaderboardEntry
     * objects representing the leaderboard.
     */
    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String sql = "SELECT PlayerName, Steps, Wins"
                +
                " FROM Leaderboard ORDER BY Wins DESC, Steps ASC";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String playerName = rs.getString("PlayerName");
                int steps = rs.getInt("Steps");
                int wins = rs.getInt("Wins");
                leaderboard.add(new LeaderboardEntry(playerName, steps, wins));
            }
            LOGGER.info("Leaderboard retrieved successfully");
        } catch (SQLException e) {
            LOGGER.error("Error retrieving leaderboard", e);
        }
        return leaderboard;
    }
}
