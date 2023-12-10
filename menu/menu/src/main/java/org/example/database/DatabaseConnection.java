package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/wumpusdb";
    private static final String USER = "root"; 
    private static final String PASSWORD = "";

    private Connection connection;

    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Itt hozhatod létre az adatbázis táblákat, ha szükséges
            // Például: stmt.execute("CREATE TABLE IF NOT EXISTS ExampleTable (id INT PRIMARY KEY, name VARCHAR(255))");
            //stmt.execute("CREATE TABLE IF NOT EXISTS ExampleTable (id INT PRIMARY KEY, name VARCHAR(255))");
            String sql = "CREATE TABLE IF NOT EXISTS PlayerNames (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "PlayerName VARCHAR(255) NOT NULL, " +
                    "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

