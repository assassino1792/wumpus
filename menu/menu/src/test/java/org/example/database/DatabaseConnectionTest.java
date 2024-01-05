package org.example.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseConnectionTest {

    private DatabaseConnection databaseConnection;

    @BeforeEach
    void setUp() {
        databaseConnection = new DatabaseConnection();
    }

    @AfterEach
    void tearDown() {
        databaseConnection.close();
    }

    @Test
    void testInitializeDatabase() {

        // Ellenőrizd, hogy a szükséges táblák létrejönnek-e

        assertTrue(checkTableExists("PlayerNames"));

        assertTrue(checkTableExists("Leaderboard"));

        assertTrue(checkTableExists("GameState"));
    }


    @Test
    void testDatabaseConnection() {
        // Teszteld az adatbáziskapcsolatot
        // Ellenőrizd, hogy a kapcsolat nem null és a helyes adatbázishoz kapcsolódik

        Connection connection = databaseConnection.getConnection();

        assertNotNull(connection);
        // Ellenőrzi, hogy a kapcsolat létezik és nyitott
        assertTrue(connectionIsValid(connection));
    }



    // Segédfüggvény az adott tábla létezésének ellenőrzéséhez
    private boolean checkTableExists(String tableName) {
        // Implementálja a tábla létezés ellenőrzését
        // a konkrét adatbázisodhoz megfelelően
        // Példa: SQL lekérdezés az adott tábla létezésének ellenőrzéséhez
        String sql = "SHOW TABLES LIKE ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, tableName);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Segédfüggvény az adatok beszúrásához a Leaderboard táblába tesztekhez
    private void insertTestDataIntoLeaderboard() {
        // Implementáld az adatok beszúrását a Leaderboard táblába tesztekhez
        // Példa: SQL lekérdezés az adatok beszúrásához
        String sql = "INSERT INTO Leaderboard (PlayerName, Steps, Wins) VALUES (?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "Player1");
            preparedStatement.setInt(2, 100);
            preparedStatement.setInt(3, 2);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Player2");
            preparedStatement.setInt(2, 150);
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Player3");
            preparedStatement.setInt(2, 200);
            preparedStatement.setInt(3, 3);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Segédfüggvény az adatbázis kapcsolat ellenőrzéséhez
    private boolean connectionIsValid(Connection connection) {
        // Implementálja a kapcsolat ellenőrzését a konkrét adatbázisodhoz megfelelően
        // Példa: Ellenőrzi, hogy a kapcsolat létezik és nyitott-e
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
