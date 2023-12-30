package org.example.map;
import java.util.List;

/**
 * Provides utility methods for validating game maps.
 */
public class MapValidator {

    /**
     * Checks if the map size is valid (between 6x6 and 20x20).
     * @param size The size of the map.
     * @return true if the map size is valid, false otherwise.
     */
    public static boolean isValidMapSize(int size) {
        return size >= 6 && size <= 20;
    }

    /**
     * Checks and prints details from the map header.
     * @param header The map header string.
     */
    public static void checkAndPrintHeaderDetails(String header) {
        if (header != null && header.length() >= 3) {
            char firstnumber =header.charAt(0);
            char secondChar = header.charAt(2);
            char thirdChar = header.charAt(4);
            char lastChar = header.charAt(header.length() - 1);
          //  System.out.println("A fejléc második karaktere: " + firstnumber);
          //  System.out.println("A fejléc második karaktere: " + secondChar);
          //  System.out.println("A fejléc harmadik karaktere: " + thirdChar);
          //  System.out.println("A fejléc negyedik karaktere: " + lastChar);
        } else {
          //  System.out.println("A fejléc túl rövid vagy null.");
        }
    }
    /**
     * Validates map dimensions based on the map lines and header.
     * @param mapLines The lines of the map.
     * @param header The map header string.
     * @return true if map dimensions are valid, false otherwise.
     */
    public static boolean isValidMapDimensions(
            List<String> mapLines,
            String header) {
        if (mapLines == null || mapLines.isEmpty() ||
                header == null || header.isEmpty()) {
            return false;
        }
        // Keresd meg az első nem számjegy karakter pozícióját a fejlécben
        int firstNonDigitIndex = 0;
        while (firstNonDigitIndex < header.length() &&
                Character.isDigit(header.charAt(firstNonDigitIndex))) {
            firstNonDigitIndex++;
        }
        // Ha nincs szám a fejlécben, akkor téves a méret
        if (firstNonDigitIndex == 0) {
            return false;
        }
        // Alakítsd át a számjegyeket számmá
        int expectedSize;
        try {
            expectedSize = Integer.parseInt(
                    header.substring(0, firstNonDigitIndex)
            );
        } catch (NumberFormatException e) {
            return false; // Ha a számjegyek nem alakíthatók át érvényes számmá
        }
        int rowCount = mapLines.size();
        int colCount = mapLines.get(0).length();
        return rowCount == expectedSize && colCount == expectedSize;
    }
    public static boolean isSurroundedByWalls(List<String> mapLines) {
        if (mapLines == null || mapLines.isEmpty()) {
            return false;
        }
        int rowCount = mapLines.size();
        int colCount = mapLines.get(0).length();
        // Ellenőrizzük az első és utolsó sort
        if (!isRowFullOfWalls(mapLines.get(0)) ||
                !isRowFullOfWalls(mapLines.get(rowCount - 1))) {
            return false;
        }
        // Ellenőrizzük a többi sort
        for (int i = 1; i < rowCount - 1; i++) {
            String row = mapLines.get(i);
            if (row.length() != colCount || row.charAt(0) != 'W'
                    || row.charAt(colCount - 1) != 'W') {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a given row consists entirely of walls ('W').
     * @param row The row to check.
     * @return true if the row is full of walls, false otherwise.
     */
    private static boolean isRowFullOfWalls(String row) {
        for (char c : row.toCharArray()) {
            if (c != 'W') {
                return false;
            }
        }
        return true;
    }
    /**
     * Calculates the number of Wumpus creatures based on map size.
     * @param mapSize The size of the map.
     * @return The number of Wumpus creatures.
     */
    public static int WumpusCount(int mapSize) {
        if (!isValidMapSize(mapSize)) {
            System.out.println("Invalid map size: " + mapSize);
        }
        if (mapSize < 6) {
            return 0;
        }
        if (mapSize <= 8) {
            return 1;
        } else if (mapSize <= 14) {
            return 2;
        } else {
            return 3;
        }
    }
    /**
     * Checks if the map contains exactly one gold piece.
     * @param mapLines The lines of the map.
     * @return true if the map contains
     * exactly one gold piece, false otherwise.
     */
    public static boolean hasExactlyOneGold(List<String> mapLines) {
        int goldCount = 0;
        for (String line : mapLines) {
            for (char c : line.toCharArray()) {
                if (c == 'G') {
                    goldCount++;
                }
            }
        }
        return goldCount == 1;
    }
}

