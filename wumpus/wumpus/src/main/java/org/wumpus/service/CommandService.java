package org.wumpus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
public class CommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionService.class);
    private final ActionService actionService;
    private final MapReaderService mapReaderService;
    private final MapValidator mapValidator;
    private final DBService dbService;

    public CommandService(ActionService actionService, MapReaderService mapReaderService, MapValidator mapValidator, DBService dbService) {
        this.actionService = actionService;
        this.mapReaderService = mapReaderService;
        this.mapValidator = mapValidator;
        this.dbService = dbService;
    }

    public void getHeroActions() {
        System.out.println("You can do it this with the hero:");
        for (HeroActionType heroActionType : HeroActionType.values()) {
            System.out.println(heroActionType.getCommandDescription());
        }
    }

    public void getActions(Hero hero, GameMap gameMap) {
        System.out.println("This is the menu:");
        for (ActionType actionType : ActionType.values()) {
            if (actionType == ActionType.GAME && gameMap == null) {
                continue;
            }

            if (actionType == ActionType.SAVE_DB && gameMap == null) {
                continue;
            }

            if (actionType == ActionType.READ_DB && CollectionUtils.isEmpty(dbService.loadInProgressGames())) {
                continue;
            }
            System.out.println(actionType.getCommandDescription());
        }
    }

    public void collectUserName(Scanner in) {
        System.out.println("Please give a playerName.");
        String playerName = in.next().trim();

        if (StringUtils.isEmpty(playerName) || playerName.length() < 4 || playerName.length() > 12) {
            System.out.println("Not valid playerName. Try again.");
            collectUserName(in);
        }

        System.out.println("Welcome player. PlayerName: " + playerName);

        Hero hero = HelperUtil.defaultHero(playerName);

        menuChoose(in, hero, null);
    }

    public void menuChoose(Scanner in, Hero hero, GameMap gameMap) {
        getActions(hero, gameMap);
        int actionNumber = 0;
        try {
            actionNumber = in.nextInt();
        } catch (Exception e) {
            menuChoose(in, hero, gameMap);
        }
        ActionType actionType = ActionType.byNumber(actionNumber);

        System.out.println("You choose this actionType: " + actionType);

        if (actionType == null) {
            System.out.println("Dont recognized menu command. Try again.");
            menuChoose(in, hero, gameMap);
        }

        switch (Objects.requireNonNull(actionType)) {
            case MAP_EDITOR -> {
                //TODO map editor
                System.out.println("We dont have map editor. Use read file.");
                menuChoose(in, hero, gameMap);
            }
            case READ_FILE -> {
                gameMap = mapReaderService.readMap(hero);
                try {
                    mapValidator.validateMap(gameMap.getBricks(), gameMap.getSize());
                } catch (IllegalArgumentException exception) {
                    LOGGER.warn("Invalid game!: [invalidReason: {}]", exception.getMessage());
                    gameMap = null;
                    hero = HelperUtil.defaultHero(hero.getPlayerName());
                    menuChoose(in, hero, gameMap);
                }

                hero.setArrowCount(HelperUtil.countBrickOfType(BrickType.WUMPUS, gameMap.getBricks().values()));

                menuChoose(in, hero, gameMap);
            }
            case READ_DB -> loadMapFromDb(in, hero, gameMap);
            case SAVE_DB -> {
                if (hero.isHasGold()) {
                    System.out.println("You won the game cant save.");
                    menuChoose(in, hero, gameMap);
                }

                if (gameMap == null) {
                    System.out.println("You dont load any map, please load one.");
                    menuChoose(in, hero, gameMap);
                }
                dbService.saveGame(hero, Objects.requireNonNull(gameMap));
                menuChoose(in, hero, gameMap);
            }
            case GAME -> {
                if (hero.isHasGold()) {
                    System.out.println("You won the game cant continue.");
                    menuChoose(in, hero, gameMap);
                }

                if (gameMap == null) {
                    System.out.println("You dont load any map, please load one.");
                    menuChoose(in, hero, gameMap);
                }

                gameChoose(in, hero, gameMap);
            }
            case EXIT -> {
            }
        }
    }

    private void loadMapFromDb(Scanner in, Hero hero, GameMap gameMap) {
        List<HeroEntity> heroEntityList = dbService.loadInProgressGames();
        System.out.println("Please choose which game do you want to load.");

        for (int i = 0; i < heroEntityList.size(); i++) {
            HeroEntity heroEntity = heroEntityList.get(i);
            System.out.println(
                    " Id: " + i +
                            " MapSize: " + heroEntity.getGameMap().getSize() +
                            " HeroMoveCount: " + heroEntity.getMoveCount()
            );
        }
        try {
            int index = in.nextInt();
            HeroEntity heroEntity = heroEntityList.get(index);
            if (heroEntity != null) {
                hero = HelperUtil.getHero(hero.getPlayerName(), heroEntity);
                gameMap = HelperUtil.getGameMap(heroEntity.getGameMap());

                System.out.println(
                        " You load this game" +
                                " MapSize: " + heroEntity.getGameMap().getSize() +
                                " HeroMoveCount: " + heroEntity.getMoveCount()
                );
                HelperUtil.drawMap(gameMap, hero);
                menuChoose(in, hero, gameMap);
            }
        } catch (Exception ignored) {
            loadMapFromDb(in, hero, gameMap);
        }
    }

    public void gameChoose(Scanner in, Hero hero, GameMap gameMap) {
        HelperUtil.drawMap(gameMap, hero);
        getHeroActions();
        int actionNumber = 0;
        try {
            actionNumber = in.nextInt();
        } catch (Exception e) {
            gameChoose(in, hero, gameMap);
        }

        HeroActionType heroActionType = HeroActionType.byNumber(actionNumber);
        System.out.println("You choose this heroActionType: " + heroActionType);

        if (heroActionType == null) {
            System.out.println("Dont recognized command. Try again.");
            gameChoose(in, hero, gameMap);
        }

        switch (Objects.requireNonNull(heroActionType)) {
            case STEP, TURN_LEFT, TURN_RIGHT, SHOOT -> {
                if (actionService.action(heroActionType, hero, gameMap)) {
                    menuChoose(in, hero, null);
                } else {
                    gameChoose(in, hero, gameMap);
                }
            }
            case SUSPEND -> {
                System.out.println("You suspended the game. We save the game into db. Return back to menu. Map is dismissed.");
                dbService.saveGame(hero, gameMap);
                menuChoose(in, HelperUtil.defaultHero(hero.getPlayerName()), null);
            }
            case GIVE_UP -> {
                System.out.println("You give up. Return back to menu. Map is dismissed.");
                menuChoose(in, HelperUtil.defaultHero(hero.getPlayerName()), null);
            }
        }
    }
}
