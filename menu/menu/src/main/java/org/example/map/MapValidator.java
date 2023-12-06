package org.example.map;
import java.util.*;


public class MapValidator {


    public static boolean isValidMapSize(int size) {
        return size >= 6 && size <= 20;
    }

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

    public static boolean isValidMapDimensions(List<String> mapLines, char firstNumberChar) {
        if (mapLines == null || mapLines.isEmpty()) {
            return false;
        }

        int expectedSize;
        if (Character.isDigit(firstNumberChar)) {
            expectedSize = Character.getNumericValue(firstNumberChar);
        } else {
            return false; // Ha a firstNumberChar nem számjegy
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
        if (!isRowFullOfWalls(mapLines.get(0)) || !isRowFullOfWalls(mapLines.get(rowCount - 1))) {
            return false;
        }

        // Ellenőrizzük a többi sort
        for (int i = 1; i < rowCount - 1; i++) {
            String row = mapLines.get(i);
            if (row.length() != colCount || row.charAt(0) != 'W' || row.charAt(colCount - 1) != 'W') {
                return false;
            }
        }

        return true;
    }
    private static boolean isRowFullOfWalls(String row) {
        for (char c : row.toCharArray()) {
            if (c != 'W') {
                return false;
            }
        }
        return true;
    }

    public static int WumpusCount(int mapSize) {
        if (!isValidMapSize(mapSize)) {
            throw new IllegalArgumentException("A pálya mérete érvénytelen: " + mapSize);
        }

        if (mapSize <= 8) {
            return 1;
        } else if (mapSize <= 14) {
            return 2;
        } else {
            return 3;
        }
    }




}

