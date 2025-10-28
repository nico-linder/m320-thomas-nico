import java.util.List;
import java.util.Map;

public class OutputFormatter {

    public void displayWelcome() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  FUSSBALL-STATISTIK-ANALYZER          ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    public void displayLoading(String leagueName) {
        System.out.println("\n⏳ Lade " + leagueName + " Daten...\n");
    }

    public void displayStandings(LeagueStanding standing, Map<String, Object> stats) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           " + standing.getLeagueName().toUpperCase() +
                " - TABELLE                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        // Header
        System.out.println("Pos | Team                    | Sp | S  | U | N | Tore    | Diff | Pkt");
        System.out.println("────┼─────────────────────────┼────┼────┼───┼───┼─────────┼──────┼────");

        // Top 5 Teams
        List<StandingEntry> entries = standing.getEntries();
        int count = Math.min(5, entries.size());

        for (int i = 0; i < count; i++) {
            StandingEntry entry = entries.get(i);
            System.out.printf("%2d  | %-23s | %2d | %2d | %d | %d | %2d:%-4d | %+4d | %2d%n",
                    entry.getPosition(),
                    truncate(entry.getTeamName(), 23),
                    entry.getPlayedGames(),
                    entry.getWon(),
                    entry.getDraw(),
                    entry.getLost(),
                    entry.getGoalsFor(),
                    entry.getGoalsAgainst(),
                    entry.getGoalDifference(),
                    entry.getPoints()
            );
        }

        System.out.println("\n═══════════════════════════════════════════════════════════════\n");

        // Statistiken
        displayStatistics(stats);
    }

    private void displayStatistics(Map<String, Object> stats) {
        System.out.println("📊 STATISTIKEN:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.printf("⚽ Durchschnitt Tore pro Spiel: %.1f%n", stats.get("avgGoals"));
        System.out.printf("🥇 Beste Offensive: %s (%d Tore)%n",
                stats.get("topOffense"), stats.get("topOffenseGoals"));
        System.out.printf("🛡️ Beste Defensive: %s (%d Gegentore)%n",
                stats.get("topDefense"), stats.get("topDefenseGoals"));
        System.out.printf("📈 Meiste Punkte: %s (%d Punkte)%n",
                stats.get("topTeam"), stats.get("topPoints"));

        System.out.println("\n═══════════════════════════════════════════════════════════════");
    }

    public void displayError(String message) {
        System.out.println("\n❌ FEHLER: " + message);
    }

    public void displayGoodbye() {
        System.out.println("\n👋 Auf Wiedersehen!\n");
    }

    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}