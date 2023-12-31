package org.example.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a class responsible for reading
 * and validating game maps from a file.
 */
public class MapReader {

    /** The lines of the map read from the file. */
    private List<String> mapLines = new ArrayList<>();

    /** The initial position of the hero on the map. */
    private MapID heroInitialPosition;

    /**
     * Reads the map data from a file.
     * @return true if the map was successfully
     * read and validated, false otherwise.
     */
    public boolean readMapFromFile() {

        String filePath = "/maps/wumpusinput.txt";
        mapLines.clear();
        String header = null;
        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(is))) {
            header = reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                mapLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return false;
        } catch (NullPointerException e) {
            System.out.println("Error: File not found: " + filePath);
            return false;
        }

        int actualWumpusCount = countWumpusOnMap();
        System.out.println("Found Wumpus number: " + actualWumpusCount);
        int mapSize = mapLines.size();
        int expectedWumpusCount = MapValidator.wumpusCount(mapSize);
        if (actualWumpusCount != expectedWumpusCount) {
            System.out.println("Error: Invalid Wumpus number."
                    +
                    " Expected number: " + expectedWumpusCount + ","
                    +
                    " Actual number: " + actualWumpusCount);
            return false;
        }

        if (header != null && !header.isEmpty()) {
            if (!MapValidator.isValidMapDimensions(mapLines, header)) {
                System.out.println("Error: Invalid map dimensions."
                        +
                        " The map size does not match the header.");
                return false;
            }
        }

        if (!MapValidator.isValidMapSize(mapLines.size())) {
            System.out.println("Error: Invalid map size."
                    +
                    " The map must be between 6x6 and 20x20.");
            return false;
        }

        if (!MapValidator.isSurroundedByWalls(mapLines)) {
            System.out.println("Error: Invalid map."
                    +
                    " The map must be surrounded by walls.");
            return false;
        }
        if (header != null) {
            System.out.println("  " + header);
            MapValidator.checkAndPrintHeaderDetails(header);
        }
        int wumpusCount = MapValidator.wumpusCount(mapSize);


        System.out.print(" ");
        if (mapLines.size() < 10) {
            System.out.print("  ");
        } else {
            System.out.print("  ");
        }
        for (int i = 0; i < mapLines.get(0).length(); i++) {
            System.out.print((char) ('a' + i));
        }
        System.out.println();

        for (int i = 0; i < mapLines.size(); i++) {
            if (i < 9) {
                System.out.print((i + 1) + "  ");
            } else {
                System.out.print((i + 1) + " ");
            }
            System.out.println(mapLines.get(i));
        }
        if (!MapValidator.hasExactlyOneGold(mapLines)) {
            System.out.println("Error: The map must "
                    +
                    "contain exactly one gold piece.");
            return false;
        }

        if (header != null && header.length() >= 5) {
            // Keresd meg az első szám végét
            int firstNumberEndIndex = 0;
            while (firstNumberEndIndex < header.length()
                    && Character.isDigit(header.charAt(firstNumberEndIndex))) {
                firstNumberEndIndex++;
            }

            int heroColumnIndex = header.indexOf(' ', firstNumberEndIndex) + 1;
            char heroColumnChar = header.charAt(heroColumnIndex);
            int heroColumn = heroColumnChar - 'A' + 1;

            int heroRowIndex = header.indexOf(' ', heroColumnIndex) + 1;
            String rowStr = header.substring(heroRowIndex).split(" ")[0];
            int heroRow;
            try {
                heroRow = Integer.parseInt(rowStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid hero row number"
                        +
                        " in header: " + rowStr);
                return false;
            }
            heroInitialPosition = new MapID(heroColumn, heroRow);
            // Kiírjuk a hős oszlopát és sorát
            System.out.println("Hero's column: " + heroColumn);
            System.out.println("Hero's row: " + heroRow);


            char heroInitialChar = mapLines.get(heroRow - 1)
                    .charAt(heroColumn - 1);
            if (heroInitialChar == 'W' || heroInitialChar == 'U') {
                System.out.println("Error: Hero's initial position cannot"
                        +
                        " be on a wall (W) or a Wumpus (U).");
                return false;
            }

            heroInitialPosition = new MapID(heroColumn, heroRow);

        }
        return true;
    }

    /**
     * Gets the initial position of the hero on the map.
     * @return The initial position of the hero.
     */
    public MapID getHeroInitialPosition() {
        return heroInitialPosition;
    }

    /**
     * Gets the lines of the map.
     * @return The list of map lines.
     */
    public List<String> getMapLines() {
        return mapLines;
    }

    /**
     * Counts the number of Wumpus characters 'U' on the map.
     * @return The number of Wumpus characters on the map.
     */
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

    /**
     * Gets the size of the map.
     * @return The size of the map.
     */
    public int getMapSize() {
        return mapLines.size();
    }

    /**
     * Redraws the map with the current hero position.
     * @param heroPosition The current position of the hero on the map.
     */
    public void redrawMap(final MapID heroPosition) {
        System.out.print("   ");
        for (int i = 0; i < mapLines.get(0).length(); i++) {
            System.out.print((char) ('a' + i));
        }
        System.out.println();
        // A pálya sorainak kiírása
        for (int i = 0; i < mapLines.size(); i++) {
            if (i < 9) {
                System.out.print((i + 1) + "  ");
            } else {
                System.out.print((i + 1) + " ");
            }
            System.out.println(mapLines.get(i));
        }
    }

    /**
     * Updates the map position at the specified
     * row and column with a new character.
     * @param row The row where the update will occur.
     * @param column The column where the update will occur.
     * @param newChar The new character to set at the specified position.
     */
    public void updateMapPosition(
            final int row,
            final int column,
            final char newChar) {
        if (row >= 0 && row < mapLines.size() && column >= 0
                && column < mapLines.get(row).length()) {
            StringBuilder updatedLine = new StringBuilder(mapLines.get(row));
            updatedLine.setCharAt(column, newChar);
            mapLines.set(row, updatedLine.toString());
        }
    }

    /**
     * Sets the map lines from a string representation.
     * @param mapState The string representation of the map state.
     */
    public void setMapLinesFromString(final String mapState) {
        this.mapLines = new ArrayList<>(
                Arrays.asList(mapState.split("\n")));
    }
}

