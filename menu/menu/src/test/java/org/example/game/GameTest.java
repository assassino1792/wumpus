package org.example.game;

import org.example.map.MapID;
import org.example.map.MapReader;
import org.example.map.WayType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testDropGold() {
        // Feltételezve, hogy a hős felvette az aranyat, majd ledobja
        hero.setHasGold(true);
        gamePlay.dropGold();
        assertFalse(hero.isHasGold());
    }

    @Test
    void testShootArrow() {
        // Feltételezve, hogy a hősnek van nyila és lő egyet
        hero.setArrowCount(1);
        gamePlay.shootArrow();
        assertEquals(0, hero.getArrowCount());
    }

    @Test
    void testGetStepCount() {
        // Ellenőrizze, hogy a lépésszámláló helyesen működik-e
        gamePlay.moveHero();
        assertEquals(1, gamePlay.getStepCount());
    }

    @Test
    void testHasWon() {
        // Feltételezve, hogy a hős felvette az aranyat és visszatér a kezdeti pozícióba
        hero.setHasGold(true);
        hero.setMapID(gamePlay.getHeroInitialPosition());
        assertTrue(gamePlay.hasWon());
    }

    @Test
    void testChangeHeroDirection() {
        // Ellenőrizze, hogy a hős irányváltoztatása helyesen működik-e
        gamePlay.changeHeroDirection(WayType.EAST);
        assertEquals(WayType.EAST, hero.getWay());
    }
    @Test
    void testIsGameWon() {
        // Feltételezve, hogy a hős kezdeti pozíciója (3, 8)
        MapID initialPosition = new MapID(3, 8);
        hero.setMapID(initialPosition);
        gamePlay.setHeroInitialPosition(initialPosition);

        // Feltételezve, hogy a hős felvette az aranyat
        hero.setHasGold(true);

        // Mozgassa a hőst északra (3, 7)
        gamePlay.changeHeroDirection(WayType.NORTH);
        gamePlay.moveHero();

        // Mozgassa vissza a hőst a kezdeti pozícióba (3, 8)
        gamePlay.changeHeroDirection(WayType.SOUTH);
        gamePlay.moveHero();

        // Ellenőrizze, hogy a játék megnyerése helyesen állapítható meg
        assertTrue(gamePlay.isGameWon());
    }

    @Test
    void testIsGameOver() {
        // Feltételezve, hogy a játék még nem ért véget
        assertFalse(gamePlay.isGameOver());
        // További logika szükséges lehet a játék végét tesztelni, pl. hős halála vagy győzelme
    }

    @Test
    void testGetWumpusKilledCount() {
        // Ellenőrizze, hogy a megölt Wumpusok száma helyesen van-e nyilvántartva
        // Ez a teszt feltételezi, hogy a hős már megölt egy Wumpust
        hero.setArrowCount(1);
        gamePlay.shootArrow(); // Feltételezve, hogy ez megöli a Wumpust
        assertEquals(1, gamePlay.getWumpusKilledCount());
    }

    @Test
    void testSetHeroInitialPosition() {
        // Beállítja a hős kezdeti pozícióját és ellenőrzi, hogy helyesen van-e beállítva
        MapID newInitialPosition = new MapID(5, 5);
        gamePlay.setHeroInitialPosition(newInitialPosition);
        assertEquals(newInitialPosition, gamePlay.getHeroInitialPosition());
    }
}

