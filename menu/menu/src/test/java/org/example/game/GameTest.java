package org.example.game;

import org.example.game.GamePlay;
import org.example.game.Hero;
import org.example.map.MapID;
import org.example.map.MapReader;
import org.example.map.WayType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameTest {

    private GamePlay gamePlay;
    private Hero hero;
    private MapReader mapReader;

    @BeforeEach
    void setUp() {
        hero = new Hero();
        mapReader = new MapReader();
        assertTrue(mapReader.readMapFromFile()); // Inicializálja a térképet és a hős kezdeti pozícióját
        hero.setMapID(mapReader.getHeroInitialPosition()); // Beállítja a hős kezdeti pozícióját
        hero.setWay(WayType.NORTH); // Beállítja a hős irányát
        gamePlay = new GamePlay(hero, mapReader, 0, 0, "TestPlayer");
    }

    @Test
    void testInitialHeroPosition() {
        // Ellenőrizze, hogy a hős kezdeti pozíciója helyes-e
        assertEquals(mapReader.getHeroInitialPosition(), hero.getMapID());
    }

    @Test
    void testHeroMovement() {
        // Mozgassa a hőst és ellenőrizze az új pozíciót
        gamePlay.changeHeroDirection(WayType.NORTH);
        gamePlay.moveHero();
        // Feltételezve, hogy a kezdeti pozíció (3, 8) és a térkép mérete megengedi a mozgást
        assertEquals(new MapID(3, 7), hero.getMapID()); // Feltételezve, hogy északra mozog
    }

    @Test
    void testPickUpGold() {
        // Feltételezve, hogy az arany a hős kezdeti pozícióján nincs és nem tudja felvenni
        gamePlay.pickUpGold();
        assertFalse(hero.isHasGold());
    }

    @Test
    void testWinningGame() {
        // Feltételezve, hogy a hős felvette az aranyat és visszatér a kezdeti pozícióba
        hero.setHasGold(true);
        // Mozgassa a hőst a kezdeti pozícióba
        gamePlay.moveHero();
        hero.setMapID(new MapID(3, 8));
        assertTrue(gamePlay.hasWon());
    }
}

