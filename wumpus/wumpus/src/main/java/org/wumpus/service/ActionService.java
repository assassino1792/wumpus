package org.wumpus.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public class ActionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionService.class);
    private final MovingService movingService;
    private final DBService dbService;
    private WumpusConfig wumpusConfig;

    public ActionService(MovingService movingService, DBService dbService, WumpusConfig wumpusConfig) {
        this.movingService = movingService;
        this.dbService = dbService;
        this.wumpusConfig = wumpusConfig;
    }

    public boolean action(HeroActionType heroActionType, Hero hero, GameMap gameMap) {
        switch (heroActionType) {
            case STEP -> {
                return step(hero, gameMap);
            }
            case TURN_LEFT -> hero.setWay(movingService.nextWay(hero.getWay(), TurnDirection.LEFT));
            case TURN_RIGHT -> hero.setWay(movingService.nextWay(hero.getWay(), TurnDirection.RIGHT));
            case SHOOT -> shoot(hero, gameMap);
        }
        return false;
    }

    private boolean step(Hero hero, GameMap gameMap) {
        boolean failGame = false;

        boolean possibleStep = true;
        boolean heroWon = false;
        Brick nextBrick = movingService.nextBrick(hero.getBrickId(), hero.getWay(), gameMap);
        switch (nextBrick.getBrickType()) {
            case WALL -> {
                LOGGER.warn("This is a wall. Impossible step.");
                possibleStep = false;
            }
            case WUMPUS -> {
                LOGGER.error("This is a wumpus. You died!");
                failGame = true;
            }
            case PIT -> {
                LOGGER.warn("You lost one arrow.");
                loseOneArrow(hero);
            }
            case GOLD -> {
                hero.setHasGold(true);
                heroWon = true;
                failGame = true;
            }
        }
        if (possibleStep) {
            hero.setMoveCount(hero.getMoveCount() + 1);
            hero.setBrickId(nextBrick.getBrickId());

            if (heroWon) {
                LOGGER.info("Hero won! You are clever. Your score: {}", hero.getMoveCount());
                dbService.saveGame(hero, gameMap);
                dbService.leaderboard();
            }
        }

        return failGame;
    }

    private void shoot(Hero hero, GameMap gameMap) {
        if (hero.getArrowCount() < 1) {
            LOGGER.warn("Not enough arrow!");
            return;
        }

        Brick nextBrick = movingService.nextBrick(hero.getBrickId(), hero.getWay(), gameMap);
        doWhile:
        while (true) {
            switch (nextBrick.getBrickType()) {
                case WALL -> {
                    LOGGER.info("The arrow hits the wall.");
                    loseOneArrow(hero);
                    break doWhile;
                }
                case WUMPUS -> {
                    LOGGER.info("The arrow hits the wumpus. SCREAAAAAAAAM!");
                    loseOneArrow(hero);
                    nextBrick.setBrickType(BrickType.EMPTY);
                    break doWhile;
                }
            }
            nextBrick = movingService.nextBrick(nextBrick.getBrickId(), hero.getWay(), gameMap);
        }
    }

    private void loseOneArrow(Hero hero) {
        hero.setArrowCount(hero.getArrowCount() - 1);
    }
}
