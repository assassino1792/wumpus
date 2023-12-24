package org.example.database;

import org.example.game.GameState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;


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
}
