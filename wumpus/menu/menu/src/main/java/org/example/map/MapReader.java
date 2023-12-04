package org.example.map;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class MapReader {

    public void readMapFromFile() {
        String filePath = "/maps/wumpusinput.txt";

        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("File not found: " + filePath);
        }
    }
}

