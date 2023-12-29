package org.example.database;

import org.example.game.LeaderboardEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static final String URL = "jdbc:mysql://localhost:3306/wumpusdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Database connection established");
            initializeDatabase();
        } catch (SQLException e) {
            logger.error("Error connecting to the database", e);
        }
    }

    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            String createPlayerNamesTable = "CREATE TABLE IF NOT EXISTS PlayerNames (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "PlayerName VARCHAR(255) NOT NULL, " +
                    "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createPlayerNamesTable);
            logger.info("PlayerNames table created successfully");

            String createLeaderboardTable = "CREATE TABLE IF NOT EXISTS Leaderboard (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "PlayerName VARCHAR(255) NOT NULL, " +
                    "Steps INT NOT NULL, " +
                    "Wins INT DEFAULT 0, " +
                    "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createLeaderboardTable);
            logger.info("Leaderboard table created successfully");

            String createGameStateTable = "CREATE TABLE IF NOT EXISTS GameState (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "PlayerName VARCHAR(255) NOT NULL, " +
                    "MapState TEXT NOT NULL, " +
                    "HeroPositionX INT NOT NULL, " +
                    "HeroPositionY INT NOT NULL, " +
                    "HeroInitialPositionX INT NOT NULL, " +
                    "HeroInitialPositionY INT NOT NULL, " +
                    "ArrowCount INT NOT NULL, " +
                    "StepCount INT NOT NULL, " +
                    "WumpusKilledCount INT NOT NULL, " +
                    "hasGold BOOLEAN, " +
                    "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createGameStateTable);
            logger.info("GameState table created successfully");

        } catch (SQLException e) {
            logger.error("Error initializing the database", e);
        }
    }

  //  public void insertOrUpdateLeaderboard(String playerName, int steps) {}

    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String sql = "SELECT PlayerName, Steps FROM Leaderboard ORDER BY Steps ASC";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String playerName = rs.getString("PlayerName");
                int steps = rs.getInt("Steps");
                int wins = rs.getInt("Wins");
                leaderboard.add(new LeaderboardEntry(playerName, steps, wins));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving leaderboard", e);
        }
        return leaderboard;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("Error getting database connection", e);
            return null;
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }
}

