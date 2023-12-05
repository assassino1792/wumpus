package org.example.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapReader {

    public void readMapFromFile() {
        String filePath = "/maps/wumpusinput.txt";
        List<String> mapLines = new ArrayList<>();
        String header = null;

        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            // Első sor (fejléc) beolvasása
            header = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                mapLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        } catch (NullPointerException e) {
            System.out.println("File not found: " + filePath);
            return;
        }

        if (MapValidator.isValidMapSize(mapLines.size())) {
            // Fejléc kiírása
            if (header != null) {
                System.out.println("  " + header);
            }

            // Oszlopok fejléce
            System.out.print("  "); // Kezdő szóközök
            for (int i = 0; i < mapLines.get(0).length(); i++) {
                System.out.print((char)('a' + i));
            }
            System.out.println();

            // A pálya sorainak kiírása
            for (int i = 0; i < mapLines.size(); i++) {
                System.out.print((i + 1) + " "); // Sor számozása
                System.out.println(mapLines.get(i));
            }
        } else {
            System.out.println("Invalid map size. The map must be between 6x6 and 20x20.");
        }
    }
}
