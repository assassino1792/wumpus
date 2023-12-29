package org.example.database;

import org.example.game.GameState;
import org.example.game.LeaderboardEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseService {

    private DatabaseConnection dbConnection;

    public DatabaseService(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insertPlayerName(String playerName) {
        String sql = "INSERT INTO PlayerNames (PlayerName) VALUES (?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.executeUpdate();
            // Itt lehet naplózni, ha szükséges
        } catch (SQLException e) {
            // Itt kezeljük a kivételt, és naplózhatjuk is
            e.printStackTrace(); // Naplózás helyett egyelőre csak kiírjuk a hibát
        }
    }

    public void insertLeaderboardEntry(String playerName, int stepsCount) {
        String sql = "INSERT INTO Leaderboard (PlayerName, StepsCount, CompletionTime) VALUES (?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, stepsCount);
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Naplózás helyett egyelőre csak kiírjuk a hibát
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
        } catch (SQLException e) {
            e.printStackTrace();
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

                return new GameState(playerName, mapState, heroPosX, heroPosY, heroInitialPosX, heroInitialPosY, arrowCount, stepCount, WumpusKilledCount, hasGold);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // A DatabaseService osztályban
    public void insertOrUpdateLeaderboard(String playerName, int steps) {
        String checkSql = "SELECT Steps FROM Leaderboard WHERE PlayerName = ?";
        String insertSql = "INSERT INTO Leaderboard (PlayerName, Steps) VALUES (?, ?)";
        String updateSql = "UPDATE Leaderboard SET Steps = ? WHERE PlayerName = ?";

        try (Connection conn = dbConnection.getConnection(); // Itt használjuk a dbConnection példányt
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, playerName);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int existingSteps = rs.getInt("Steps");
                if (steps < existingSteps) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, steps);
                        updateStmt.setString(2, playerName);
                        updateStmt.executeUpdate();
                    }
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, playerName);
                    insertStmt.setInt(2, steps);
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String sql = "SELECT PlayerName, Steps FROM Leaderboard ORDER BY Steps ASC";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql); // Itt használjuk a pstmt változót
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String playerName = rs.getString("PlayerName");
                int steps = rs.getInt("Steps");
                leaderboard.add(new LeaderboardEntry(playerName, steps));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }
}
