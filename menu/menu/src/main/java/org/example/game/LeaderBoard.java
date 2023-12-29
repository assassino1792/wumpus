package org.example.game;

import org.example.database.DatabaseService;
public class LeaderBoard {
    private DatabaseService dbService;
    public LeaderBoard(DatabaseService dbService) {this.dbService = dbService;}
    public void updateLeaderboard(String playerName, int steps) {
        dbService.insertOrUpdateLeaderboard(playerName, steps);
    }
/*    public List<LeaderboardEntry> getLeaderboard() {
        // Itt lekérjük a ranglistát az adatbázisból
        return dbService.getLeaderboard();
    }
*/
}



