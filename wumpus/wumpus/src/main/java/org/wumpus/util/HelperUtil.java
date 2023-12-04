package org.wumpus.util;



import com.misi.wumpus.model.*;
import com.misi.wumpus.persistence.entity.BrickEntity;
import com.misi.wumpus.persistence.entity.GameMapEntity;
import com.misi.wumpus.persistence.entity.HeroEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class HelperUtil {

    private final static List<String> ALPHABET = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");


    public static Brick foundBrickByType(BrickType brickType, Collection<Brick> bricks) {
        return bricks.stream().filter(brick -> brick.getBrickType() == brickType).findFirst().orElse(null);
    }

    public static int countBrickOfType(BrickType brickType, Collection<Brick> bricks) {
        return (int) bricks.stream().filter(brick -> brick.getBrickType() == brickType).count();
    }

    public static Hero defaultHero(String playerName) {
        Hero hero = new Hero();
        hero.setPlayerName(playerName);
        return hero;
    }

    public static int indexOfChar(String charter) {
        return HelperUtil.ALPHABET.indexOf(charter) + 1;
    }

    public static String charOfIndex(int index) {
        return HelperUtil.ALPHABET.get(index - 1);
    }

    public static Hero getHero(String playerName, HeroEntity heroEntity) {
        Hero hero = new Hero();
        hero.setPlayerName(playerName);
        hero.setHeroId(heroEntity.getHeroId());
        hero.setBrickId(new BrickId(heroEntity.getHorizontal(), heroEntity.getVertical()));
        hero.setWay(heroEntity.getWayType());
        hero.setArrowCount(heroEntity.getArrowCount());
        hero.setHasGold(heroEntity.isHasGold());
        hero.setMoveCount(heroEntity.getMoveCount());
        return hero;
    }

    public static GameMap getGameMap(GameMapEntity gameMapEntity) {
        GameMap gameMap = new GameMap();
        gameMap.setSize(gameMapEntity.getSize());
        gameMap.setBricks(new HashMap<>());

        for (BrickEntity brickEntity : gameMapEntity.getBricks()) {
            Brick brick = new Brick();
            BrickId brickId = new BrickId(brickEntity.getHorizontal(), brickEntity.getVertical());
            brick.setBrickId(brickId);
            brick.setBrickType(brickEntity.getBrickType());
            gameMap.getBricks().put(brickId, brick);
        }
        return gameMap;
    }

    public static HeroEntity getHeroEntity(Hero hero, GameMap gameMap) {
        HeroEntity heroEntity = new HeroEntity();
        heroEntity.setHeroId(hero.getHeroId());
        heroEntity.setPlayerName(hero.getPlayerName());
        heroEntity.setHorizontal(hero.getBrickId().getHorizontal());
        heroEntity.setVertical(hero.getBrickId().getVertical());
        heroEntity.setWayType(hero.getWay());
        heroEntity.setArrowCount(hero.getArrowCount());
        heroEntity.setHasGold(hero.isHasGold());
        heroEntity.setMoveCount(hero.getMoveCount());

        GameMapEntity gameMapEntity = new GameMapEntity();
        gameMapEntity.setSize(gameMap.getSize());
        gameMapEntity.setBricks(new ArrayList<>());
        heroEntity.setGameMap(gameMapEntity);

        for (Brick brick : gameMap.getBricks().values()) {
            BrickEntity brickEntity = new BrickEntity();
            brickEntity.setHorizontal(brick.getBrickId().getHorizontal());
            brickEntity.setVertical(brick.getBrickId().getVertical());
            brickEntity.setBrickType(brick.getBrickType());
            gameMapEntity.getBricks().add(brickEntity);
        }
        return heroEntity;
    }

    public static void drawMap(GameMap gameMap, Hero hero) {
        System.out.println("This is your map!");
        System.out.print(" ");
        for (int i = 1; i <= gameMap.getSize(); i++) {
            System.out.print(i);
        }

        for (int i = 1; i <= gameMap.getSize(); i++) {
            boolean newLine = true;
            for (int j = 1; j <= gameMap.getSize(); j++) {
                if (newLine) {
                    newLine = false;
                    System.out.println();
                    System.out.print(i);
                }
                BrickId brickId = new BrickId(j, i);

                Brick brick = gameMap.getBricks().getOrDefault(brickId, null);
                System.out.print(brick.getBrickType().getKey());
            }
        }
        System.out.println();
        System.out.println(
                " Hero stand on." +
                        " Hori: " + hero.getBrickId().getHorizontal() +
                        " Verti: " + hero.getBrickId().getVertical() +
                        " See this way: " + hero.getWay().name() +
                        " StepCount: " + hero.getMoveCount() +
                        " ArrowCount: " + hero.getArrowCount()
        );
    }
}
