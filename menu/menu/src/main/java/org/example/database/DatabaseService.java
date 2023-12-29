package org.example.database;

import org.example.game.GameState;
import org.example.game.LeaderboardEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);
    private DatabaseConnection dbConnection;
    private static final String winUpdateSql = "UPDATE Leaderboard SET Wins = Wins + 1 WHERE PlayerName = ?";

    public DatabaseService(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insertPlayerName(String playerName) {
        String sql = "INSERT INTO PlayerNames (PlayerName) VALUES (?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.executeUpdate();
            logger.info("Player name '{}' inserted into PlayerNames table", playerName);
        } catch (SQLException e) {
            logger.error("Error inserting player name into PlayerNames table", e);
        }
    }

    public void saveGameState(String playerName, String mapState, int heroPosX, int heroPosY, int heroInitialPosX, int heroInitialPosY, int arrowCount, int stepCount, int WumpusKilledCount, boolean hasGold) {
        String sql = "INSERT INTO GameState (PlayerName, MapState, HeroPositionX, HeroPositionY, HeroInitialPositionX, HeroInitialPositionY, ArrowCount, StepCount, WumpusKilledCount, hasGold, Timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.setString(2, mapState);
            pstmt.setInt(3, heroPosX);
            pstmt.setInt(4, heroPosY);
            pstmt.setInt(5, heroInitialPosX);
            pstmt.setInt(6, heroInitialPosY);
            pstmt.setInt(7, arrowCount);
            pstmt.setInt(8, stepCount);
            pstmt.setInt(9, WumpusKilledCount);
            pstmt.setBoolean(10, hasGold);
            pstmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            logger.info("Game state saved for player '{}'. Step count: {}, Wumpus killed: {}, Has gold: {}", playerName, stepCount, WumpusKilledCount, hasGold);
        } catch (SQLException e) {
            logger.error("Error saving game state for player '{}'", playerName, e);
        }
    }


    public GameState loadGameState(String playerName) {
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
                int WumpusKilledCount = rs.getInt("WumpusKilledCount");
                boolean hasGold = rs.getBoolean("hasGold");
                logger.info("Game state loaded successfully for player '{}'", playerName);
                return new GameState(playerName, mapState, heroPosX, heroPosY, heroInitialPosX, heroInitialPosY, arrowCount, stepCount, WumpusKilledCount, hasGold);
            } else {
                logger.info("No game state found for player '{}'", playerName);
            }
        } catch (SQLException e) {
            logger.error("Error loading game state for player '{}'", playerName, e);
        }
        return null;
    }

    public void insertOrUpdateLeaderboard(String playerName, int steps, boolean hasWon) {
        String checkSql = "SELECT Steps FROM Leaderboard WHERE PlayerName = ?";
        String insertSql = "INSERT INTO Leaderboard (PlayerName, Steps, Wins) VALUES (?, ?, ?)";
        String updateSql = "UPDATE Leaderboard SET Steps = ? WHERE PlayerName = ? AND ? < Steps";
        boolean shouldUpdateWins = false;

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, playerName);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int existingSteps = rs.getInt("Steps");
                if (steps < existingSteps) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, steps);
                        updateStmt.setString(2, playerName);
                        updateStmt.setInt(3, steps);
                        updateStmt.executeUpdate();
                        logger.info("Leaderboard updated for player '{}': new steps count is {}", playerName, steps);
                        shouldUpdateWins = true;
                    }
                } else {
                    logger.info("No update required for player '{}' on leaderboard: existing steps count is lower", playerName);
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, playerName);
                    insertStmt.setInt(2, steps);
                    insertStmt.setInt(3, hasWon ? 1 : 0);
                    insertStmt.executeUpdate();
                    logger.info("New leaderboard entry created for player '{}': steps count is {}, wins: {}", playerName, steps, hasWon ? 1 : 0);
                }
            }
            if (hasWon && shouldUpdateWins) {
                try (PreparedStatement winUpdateStmt = conn.prepareStatement(winUpdateSql)) {
                    winUpdateStmt.setString(1, playerName);
                    winUpdateStmt.executeUpdate();
                    logger.info("Incremented win count for player '{}'", playerName);
                }
            }
        } catch (SQLException e) {
            logger.error("Error updating or inserting leaderboard for player '{}'", playerName, e);
        }
    }
    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String sql = "SELECT PlayerName, Steps, Wins FROM Leaderboard ORDER BY Steps ASC";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String playerName = rs.getString("PlayerName");
                int steps = rs.getInt("Steps");
                int wins = rs.getInt("Wins");
                leaderboard.add(new LeaderboardEntry(playerName, steps, wins));
            }
            logger.info("Leaderboard retrieved successfully");
        } catch (SQLException e) {
            logger.error("Error retrieving leaderboard", e);
        }
        return leaderboard;
    }
}
