package org.example.game;

public class LeaderboardEntry {

        private String playerName;
        private int steps;

        public LeaderboardEntry(String playerName, int steps) {
            this.playerName = playerName;
            this.steps = steps;
        }

        // Getterek és setterek
        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public int getSteps() {
            return steps;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }
    }


