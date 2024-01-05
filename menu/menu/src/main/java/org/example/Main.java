/**
 * Class containing the entry point of the game.
 */
package org.example;

import org.example.database.DatabaseConnection;
import org.example.menu.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    /**
     * The entry point of the program.
     * @param args The program's arguments.
     */
    public static void main(String[] args) {
        try {
        SpringApplication.run(Main.class, args);
        logger.info("Application started");
        Menu menu = new Menu();
        menu.startGame();

        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.close();

        } catch (Exception e) {
            logger.error("Error during application execution", e);
        }
    }
}




