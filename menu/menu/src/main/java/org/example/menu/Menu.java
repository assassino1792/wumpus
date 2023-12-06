package org.example.menu;

import org.example.map.MapReader;

import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private MapReader mapReader = new MapReader();


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

        System.out.println("\nThe game has started.");
        while (true) {
            System.out.println("\nGame Menu:");
            System.out.println("1. SAVE");
            System.out.println("2. SUSPEND");
            System.out.println("3. EXIT GAME");
            System.out.print("Please enter your choice (1-3): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("SAVE selected.");
                    // Itt lehet hozzáadni a SAVE funkciókat
                    break;
                case 2:
                    System.out.println("SUSPEND selected.");
                    // Itt lehet hozzáadni a SUSPEND funkciókat
                    break;
                case 3:
                    if (confirmExit()) {
                        System.out.println("You lost.");
                        return;
                    } break;
                        default:
                            System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    }
            }
        }
        private boolean confirmExit() {
        System.out.print("Are you sure you want to give up the game? (Y/N): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("Y");
    }





}

