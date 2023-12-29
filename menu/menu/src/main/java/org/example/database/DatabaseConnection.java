package org.example.database;

import org.example.game.LeaderboardEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    /** The database connection. */
    private Connection connection;
    /** Logger for this class. */
    private static final Logger LOGGER
    = LoggerFactory.getLogger(DatabaseConnection.class);
    /** Database URL. */
    private static final String URL = "jdbc:mysql://localhost:3306/wumpusdb";
    /** Database user. */
    private static final String USER = "root";
    /** Database password. */
    private static final String PASSWORD = "";

    /**
     * Constructs a new DatabaseConnection
     * and initializes the database.
     */
    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.info("Database connection established");
            initializeDatabase();
        } catch (SQLException e) {
            LOGGER.error("Error connecting to the database", e);
        }
    }
    /**
     * Initializes the database by creating
     * necessary tables if they do not exist.
     */
    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            String createPlayerNamesTable
                    = "CREATE TABLE IF NOT EXISTS PlayerNames ("
                    +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    +
                    "PlayerName VARCHAR(255) NOT NULL, "
                    +
                    "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createPlayerNamesTable);
            LOGGER.info("PlayerNames table created successfully");

            String createLeaderboardTable
                    = "CREATE TABLE IF NOT EXISTS Leaderboard ("
                    +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    +
                    "PlayerName VARCHAR(255) NOT NULL, "
                    +
                    "Steps INT NOT NULL, "
                    +
                    "Wins INT DEFAULT 0, "
                    +
                    "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createLeaderboardTable);
            LOGGER.info("Leaderboard table created successfully");

            String createGameStateTable
                    = "CREATE TABLE IF NOT EXISTS GameState ("
                    +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, "
                    +
                    "PlayerName VARCHAR(255) NOT NULL, "
                    +
                    "MapState TEXT NOT NULL, "
                    +
                    "HeroPositionX INT NOT NULL, "
                    +
                    "HeroPositionY INT NOT NULL, "
                    +
                    "HeroInitialPositionX INT NOT NULL, "
                    +
                    "HeroInitialPositionY INT NOT NULL, "
                    +
                    "ArrowCount INT NOT NULL, "
                    +
                    "StepCount INT NOT NULL, "
                    +
                    "WumpusKilledCount INT NOT NULL, "
                    +
                    "hasGold BOOLEAN, "
                    +
                    "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createGameStateTable);
            LOGGER.info("GameState table created successfully");

        } catch (SQLException e) {
            LOGGER.error("Error initializing the database", e);
        }
    }
    /**
     * Retrieves the leaderboard from the database.
     * @return A list of LeaderboardEntry objects representing the leaderboard.
     */
    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String sql = "SELECT PlayerName, "
                +
                "Steps FROM Leaderboard ORDER BY Steps ASC";
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
            LOGGER.error("Error retrieving leaderboard", e);
        }
        return leaderboard;
    }
    /**
     * Gets a connection to the database.
     * @return A Connection object to the database.
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("Error getting database connection", e);
            return null;
        }
    }
    /** Closes the database connection. */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Database connection closed");
            } catch (SQLException e) {
                LOGGER.error("Error closing database connection", e);
            }
        }
    }
}

