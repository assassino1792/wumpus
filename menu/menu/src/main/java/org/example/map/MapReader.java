package org.example.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapReader {

    private List<String> mapLines = new ArrayList<>();
    private MapID heroInitialPosition;


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
            System.out.println("Error: File not found: " + filePath);
            return false; // Itt is adjuk vissza a false értéket
        }


        //Wumpus
        int actualWumpusCount = countWumpusOnMap();
        System.out.println("Found Wumpus number: " + actualWumpusCount); // Kiírjuk a Wumpusok számát
        int mapSize = mapLines.size();
        int expectedWumpusCount = MapValidator.WumpusCount(mapSize);
        if (actualWumpusCount != expectedWumpusCount) {
            System.out.println("Error: Invalid Wumpus number. Expected number: " + expectedWumpusCount + ", Actual number: " + actualWumpusCount);
            return false;
        }


        // Ellenőrizzük a pálya méretét a fejléc alapján
        if (header != null && !header.isEmpty()) {
            if (!MapValidator.isValidMapDimensions(mapLines, header)) {
                System.out.println("Error: Invalid map dimensions. The map size does not match the header.");
                return false;
            }
        }

        // Ellenőrizzük a térkép méretét és a falakat a beolvasás után
        if (!MapValidator.isValidMapSize(mapLines.size())) {
            System.out.println("Error: Invalid map size. The map must be between 6x6 and 20x20.");
            return false;
        }

        if (!MapValidator.isSurroundedByWalls(mapLines)) {
            System.out.println("Error: Invalid map. The map must be surrounded by walls.");
            return false;
        }
     //   System.out.println("Map size: " + getMapSize());


        if (header != null) {
            System.out.println("  " + header);
            MapValidator.checkAndPrintHeaderDetails(header);
        }

        int wumpusCount = MapValidator.WumpusCount(mapSize);


        // Oszlopok fejléce és a pálya sorainak kiírása
        System.out.print(" ");
        if (mapLines.size() < 10) {
            System.out.print("  "); // szóköz a 9-es sor alattiaknak
        } else {
            System.out.print("  "); // szóköz a 10-es sor felettieknek
        }
        for (int i = 0; i < mapLines.get(0).length(); i++) {
            System.out.print((char)('a' + i));
        }
        System.out.println();

        for (int i = 0; i < mapLines.size(); i++) {
            if (i < 9) { // A 9-es sor ,soroknál szóköz
                System.out.print((i + 1) + "  ");
            } else { // A 10-es sor ,soroknál szóköz
                System.out.print((i + 1) + " ");
            }
            System.out.println(mapLines.get(i));
        }

        if (!MapValidator.hasExactlyOneGold(mapLines)) {
            System.out.println("Error: The map must contain exactly one gold piece.");
            return false;
        }



        // Hős helyzetének meghatározása
        if (header != null && header.length() >= 5) {
            // Keresd meg az első szám végét
            int firstNumberEndIndex = 0;
            while (firstNumberEndIndex < header.length() && Character.isDigit(header.charAt(firstNumberEndIndex))) {
                firstNumberEndIndex++;
            }

            // Keresd meg a hős oszlopát
            int heroColumnIndex = header.indexOf(' ', firstNumberEndIndex) + 1;
            char heroColumnChar = header.charAt(heroColumnIndex);
            int heroColumn = heroColumnChar - 'A' + 1;

            // Keresd meg a hős sorát
            int heroRowIndex = header.indexOf(' ', heroColumnIndex) + 1;
            String rowStr = header.substring(heroRowIndex).split(" ")[0];
            int heroRow;
            try {
                heroRow = Integer.parseInt(rowStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid hero row number in header: " + rowStr);
                return false;
            }
            heroInitialPosition = new MapID(heroColumn, heroRow);

            // Kiírjuk a hős oszlopát és sorát
            System.out.println("Hero's column: " + heroColumn);
            System.out.println("Hero's row: " + heroRow);

        }


        return true; // Sikeres beolvasás és feldolgozás

    }

    public MapID getHeroInitialPosition() {
        return heroInitialPosition;
    }

    public List<String> getMapLines() {
        return mapLines;
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

    // Új metódus a pálya méretének lekérdezésére
    public int getMapSize() {
        return mapLines.size();
    }
    public void redrawMap(MapID heroPosition) {
        // Oszlopok fejléce
        System.out.print("   "); // Kezdő szóközök (3 szóköz a jobb formázásért)
        for (int i = 0; i < mapLines.get(0).length(); i++) {
            System.out.print((char)('a' + i));
        }
        System.out.println();

        // A pálya sorainak kiírása
        for (int i = 0; i < mapLines.size(); i++) {
            if (i < 9) {
                System.out.print((i + 1) + "  "); // Két szóköz a 9-es sor alattiaknak
            } else {
                System.out.print((i + 1) + " "); // Egy szóköz a 10-es sor felettieknek
            }
            System.out.println(mapLines.get(i));
        }

        // Kiírjuk a hős aktuális pozícióját
        if (heroPosition != null) {
            System.out.println("Hero's current position - Column: " + heroPosition.getHorizontal() + ", Row: " + heroPosition.getVertical());
        }
    }
    public void updateMapPosition(int row, int column, char newChar) {
        if (row >= 0 && row < mapLines.size() && column >= 0 && column < mapLines.get(row).length()) {
            StringBuilder updatedLine = new StringBuilder(mapLines.get(row));
            updatedLine.setCharAt(column, newChar); // Frissítjük a karaktert
            mapLines.set(row, updatedLine.toString()); // Frissítjük a sor tartalmát
        }
    }

}

