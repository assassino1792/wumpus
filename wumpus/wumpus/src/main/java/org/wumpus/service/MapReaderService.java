package org.wumpus.service;


import org.wumpus.model.*;
import org.wumpus.util.HelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class MapReaderService {

    private final Logger LOGGER = LoggerFactory.getLogger(MapReaderService.class);

    public GameMap readMap(Hero hero) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("maps/wumpuszinput.txt"));
            String line = reader.readLine();
            boolean firstLine = true;

            GameMap gameMap = new GameMap();

            int row = 1;
            while (line != null) {
                System.out.println(line);
                if (firstLine) {
                    firstLine = false;
                    String[] split = line.split(" ");
                    gameMap.setSize((Integer.parseInt(split[0])));
                    setHeroBrickAndWay(split, hero);
                } else {
                    int col = 1;
                    String[] split = line.split("");
                    for (String coli : split) {
                        Brick brick = new Brick();
                        brick.setBrickType(BrickType.byKey(coli.charAt(0)));
                        BrickId brickId = new BrickId(col, row);
                        brick.setBrickId(brickId);
                        gameMap.getBricks().put(brickId, brick);
                        col++;
                    }
                    row++;
                }

                // read next line
                line = reader.readLine();
            }
            reader.close();
            return gameMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setHeroBrickAndWay(String[] split, Hero hero) {
        String horizontalChar = split[1];
        String verticalChar = split[2];
        String wayKey = split[3];

        int hori = HelperUtil.indexOfChar(horizontalChar);
        int veri = (Integer.parseInt(verticalChar));

        hero.setBrickId(new BrickId(hori, veri));
        hero.setWay(WayType.byKey(wayKey.charAt(0)));
    }
}

