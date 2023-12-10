package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        }
    }
   }
