package org.example.game;

public class LeaderboardEntry {

        private String playerName;
        private int steps;
        private int wins;

        public LeaderboardEntry(String playerName, int steps, int wins) {
            this.playerName = playerName;
            this.steps = steps;
            this.wins = wins;
        }

        public String getPlayerName() {
            return playerName;
        }

        //public void setPlayerName(String playerName) {this.playerName = playerName;}

        public int getSteps() {
            return steps;
        }
        public int getWins() {return wins;}

     //   public void setSteps(int steps) {this.steps = steps;}
    }


