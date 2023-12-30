package org.example.game;


/**
 * Represents an entry in the leaderboard with player information.
 */
public class LeaderboardEntry {
        /** The name of the player. */
        private String playerName;
        /** The number of steps taken by the player. */
        private int steps;
        /** The number of wins achieved by the player. */
        private int wins;

        /**
         * Initializes a new instance of the LeaderboardEntry class.
         * @param playername The name of the player.
         * @param newSteps The number of newSteps taken by the player.
         * @param win The number of win achieved by the player.
         */
        public LeaderboardEntry(final String playername,
                                final int newSteps, final int win) {
            this.playerName = playername;
            this.steps = newSteps;
            this.wins = win;
        }
        /**
         * Gets the name of the player.
         * @return The name of the player.
         */
        public String getPlayerName() {
            return playerName;
        }
        /**
         * Gets the number of steps taken by the player.
         * @return The number of steps taken by the player.
         */
        public int getSteps() {
            return steps;
        }
        /**
         * Gets the number of wins achieved by the player.
         * @return The number of wins achieved by the player.
         */
        public int getWins() {
            return wins;
        }
        /**
         * Sets the number of step taken by the player.
         * @param step The new number of step to set for the player.
         */
        public void setSteps(final int step) {
            this.steps = step;
        }
    }


