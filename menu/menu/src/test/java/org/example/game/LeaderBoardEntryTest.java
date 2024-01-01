package org.example.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class LeaderBoardEntryTest {

    private LeaderboardEntry entry;

    @BeforeEach
    void setUp() {
        entry = new LeaderboardEntry("TestPlayer", 10, 5);
    }

    @Test
    void testConstructor() {
        assertEquals("TestPlayer", entry.getPlayerName());
        assertEquals(10, entry.getSteps());
        assertEquals(5, entry.getWins());
    }

    @Test
    void testSetSteps() {
        entry.setSteps(15);
        assertEquals(15, entry.getSteps());
    }

    @Test
    void testGetPlayerName() {
        assertEquals("TestPlayer", entry.getPlayerName());
    }

    @Test
    void testGetSteps() {
        assertEquals(10, entry.getSteps());
    }

    @Test
    void testGetWins() {
        assertEquals(5, entry.getWins());
    }
}
