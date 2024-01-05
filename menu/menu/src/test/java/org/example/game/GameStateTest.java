package org.example.game;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    private GameState gameState;

    @BeforeEach
    void setUp() {
        // Inicializálja a GameState objektumot teszteléshez
        gameState = new GameState("Player1",
                "Map1",
                5,
                5,
                3,
                10,
                2,
                0,
                0,
                false);
    }

    @Test
    void testSetHasGold() {
        // Teszteli a setHasGold metódust
        gameState.setHasGold(true);
        assertTrue(gameState.isHasGold());
    }

    @Test
    void testSetHeroInitialPosition() {
        // Teszteli a hős kezdeti pozíciójának beállítását
        gameState.setHeroInitialPosX(7);
        gameState.setHeroInitialPosY(8);
        assertEquals(7, gameState.getHeroInitialPosX());
        assertEquals(8, gameState.getHeroInitialPosY());
    }

    @Test
    void testGetMapState() {
        // Ellenőrzi a térkép állapotát
        assertNotNull(gameState.getMapState());
    }

    @Test
    void testGetHeroPosition() {
        // Ellenőrzi a hős pozícióját
        assertEquals(5, gameState.getHeroPosX());
        assertEquals(5, gameState.getHeroPosY());
    }

    @Test
    void testGetArrowCount() {
        // Ellenőrzi a nyilak számát
        assertEquals(2, gameState.getarrowCount());
    }
}