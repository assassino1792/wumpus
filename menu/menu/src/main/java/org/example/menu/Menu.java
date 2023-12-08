package org.example.menu;

import org.example.game.GamePlay;
import org.example.game.Hero;
import org.example.map.MapReader;
import org.example.map.WayType;

import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private MapReader mapReader = new MapReader();
    private Hero hero;
    private int mapSize;

    public void startGame() {

        MapReader mapReader = new MapReader();

        //mapReader.readMapFromFile();
        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();



        if (MenuValidator.isValidUsername(username)) {
            System.out.println("Welcome, " + username + "!");
            displayMenu();
        } else {
            System.out.println("Invalid username. It must be between 3 and 12 characters and not contain spaces.");
        }

    }


    public void displayMenu() {
        int choice;

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. MAP EDITOR");
            System.out.println("2. READ MAP FROM FILE");
            System.out.println("3. READ FROM DATABASE");
            System.out.println("4. EXIT");
            System.out.print("Please enter your choice (1-3): ");

            choice = scanner.nextInt();


            switch (choice) {
                case 1:
                    //System.out.println("MAP EDITOR selected.");
                    System.out.println("Currently, map editing is not available. Please use the READ MAP FROM FILE option.");
                    break;
                case 2:
                    System.out.println("READ MAP FROM FILE selected.");
                    //mapReader.readMapFromFile();
                    boolean isMapValid = mapReader.readMapFromFile();

                    if (!isMapValid) {
                        // Ha a térkép érvénytelen, visszatérünk a displayMenu-be
                        displayMenu();
                    }
                    displaySubMenu();
                    break;
                case 3:
                    System.out.println("READ FROM DATABASE selected.");
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return; // Kilépés a menüből
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }


        }


    }

            private void displaySubMenu() {
                int choice;

                System.out.println("\nSub Menu:");
                System.out.println("1. GAME");
                System.out.println("2. RETURN TO MAIN MENU");
                System.out.print("Please enter your choice (1-3): ");

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("GAME selected.");
                        mapReader.readMapFromFile();
                        displayGameMenu();
                        break;
                    case 2:
                        // Visszatérés a főmenübe
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            }

    private void displayGameMenu() {
        int choice;

        MapReader mapReader = new MapReader();
        hero = new Hero(); // Inicializáljuk a hőst


        boolean isMapValid = mapReader.readMapFromFile();
        if (isMapValid) {
            int mapSize = mapReader.getMapSize(); // Lekérjük a pálya méretét
            hero.initializeHero(mapSize); // Inicializáljuk a hőst a pálya méretével
           // displayMenu();
        } else {
            System.out.println("Invalid map.");
        }

        GamePlay gamePlay = new GamePlay(hero, mapSize); // Létrehozzuk a GamePlay példányt

        System.out.println("\nThe game has started.");


        while (true) {
            System.out.println("\nGame Menu:");

            System.out.println("1. Look North");
            System.out.println("2. Look East");
            System.out.println("3. Look West");
            System.out.println("4. Look South");
            System.out.println("5. Move");
            System.out.println("9. Save");
            System.out.println("10. Suspend");
            System.out.println("11. Give up the game");
            System.out.print("\nPlease enter your choice (1-11): ");


            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    gamePlay.changeHeroDirection(WayType.NORTH);
                    break;
                case 2:
                    gamePlay.changeHeroDirection(WayType.EAST);
                    break;
                case 3:
                    gamePlay.changeHeroDirection(WayType.WEST);
                    break;
                case 4:
                    gamePlay.changeHeroDirection(WayType.SOUTH);
                    break;
                case 5:
                    gamePlay.moveHero(); // Mozgatja a hőst
                    break;
               // TODO SAVE and SUSPEND
                case 11:
                    if (confirmExit()) {
                        System.out.println("You gave up the game.");
                        return; // Kilépés a menüből
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 11.");
            }
            mapReader.redrawMap(); // Újrarajzolja a térképet minden választás után
            System.out.println("\nRemaining arrows: " + hero.getArrowCount()); // Kiírjuk a hős maradék nyílak számát

        }

    }
        private boolean confirmExit() {
        System.out.print("Are you sure you want to give up the game? (Y/N): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("Y");
    }





}

