package Impl1;

class Team {
    private Player[] players;
    private int playerCount;
    private static final int MAX_PLAYERS = 11;

    public Team() {
        players = new Player[MAX_PLAYERS];
        playerCount = 0;
    }

    public boolean addPlayer(Player player) {
        if (playerCount < MAX_PLAYERS) {
            players[playerCount] = player;
            playerCount++;
            System.out.println(player.getName() + " added to the team");
            return true;
        }
        System.out.println("Team is full!");
        return false;
    }

    public void trainAllPlayers() {
        System.out.println("\n=== Training Session ===");
        for (int i = 0; i < playerCount; i++) {
            players[i].train();
        }
    }

    public void displayTeam() {
        System.out.println("\n=== Team Roster ===");
        for (int i = 0; i < playerCount; i++) {
            players[i].displayInfo();
        }
    }

    public long getTotalSalaryCost() {
        double total = 0;
        for (int i = 0; i < playerCount; i++) {
            total += players[i].calculateSalary();
        }
        return Math.round(total);
    }
}
