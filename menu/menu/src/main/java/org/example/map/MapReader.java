package org.example.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapReader {

    private List<String> mapLines = new ArrayList<>();


    public boolean readMapFromFile() {

        String filePath = "/maps/wumpusinput.txt";
        mapLines.clear();
        //List<String> mapLines = new ArrayList<>();
        String header = null;

        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            header = reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                mapLines.add(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return false; // Itt adjuk vissza a false értéket
        } catch (NullPointerException e) {
            System.out.println("File not found: " + filePath);
            return false; // Itt is adjuk vissza a false értéket
        }

        int actualWumpusCount = countWumpusOnMap();
        //System.out.println("Found Wumpus number: " + actualWumpusCount); // Kiírjuk a Wumpusok számát

        int mapSize = mapLines.size();
        int expectedWumpusCount = MapValidator.WumpusCount(mapSize);

        if (actualWumpusCount != expectedWumpusCount) {
            System.out.println("Invalid Wumpus number. Expected number: " + expectedWumpusCount + ", Actual number: " + actualWumpusCount);
            return false;
        }

        // Ellenőrizzük a pálya méretét a fejléc alapján
        if (header != null && header.length() > 0) {
            char firstNumberChar = header.charAt(0);
            if (!MapValidator.isValidMapDimensions(mapLines, firstNumberChar)) {
                System.out.println("Invalid map dimensions. The map size does not match the header.");
                return false;
            }
        }

        // Ellenőrizzük a térkép méretét és a falakat a beolvasás után
        if (!MapValidator.isValidMapSize(mapLines.size())) {
            System.out.println("Invalid map size. The map must be between 6x6 and 20x20.");
            return false;
        }

        if (!MapValidator.isSurroundedByWalls(mapLines)) {
            System.out.println("Invalid map. The map must be surrounded by walls.");
            return false;
        }


        if (header != null) {
            System.out.println("  " + header);
            MapValidator.checkAndPrintHeaderDetails(header);
        }

        // Pálya méretének meghatározása
        int wumpusCount = MapValidator.WumpusCount(mapSize);
        // Oszlopok fejléce és a pálya sorainak kiírása
        System.out.print("  ");
        for (int i = 0; i < mapLines.get(0).length(); i++) {
            System.out.print((char)('a' + i));
        }
        System.out.println();

        for (int i = 0; i < mapLines.size(); i++) {
            System.out.print((i + 1) + " ");
            System.out.println(mapLines.get(i));
        }

        return true; // Sikeres beolvasás és feldolgozás

    }

    public int countWumpusOnMap() {
        int wumpusCount = 0;
        for (String line : mapLines) {
            for (char c : line.toCharArray()) {
                if (c == 'U') {
                    wumpusCount++;
                }
            }
        }
        return wumpusCount;
    }

}
