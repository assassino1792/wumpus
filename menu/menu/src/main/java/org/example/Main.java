package org.example;

import org.example.database.DatabaseConnection;
import org.example.menu.Menu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        Menu menu = new Menu();
        menu.startGame();

        DatabaseConnection dbConnection = new DatabaseConnection();
       // Connection conn = dbConnection.getConnection();
        // Itt végezheted el az adatbázis műveleteket a 'conn' objektum használatával
        dbConnection.close(); // Ne felejtsd el lezárni a kapcsolatot, amikor már nincs rá szükség
    }

}




