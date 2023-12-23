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
        dbConnection.close();
    }

}




