import java.util.*;

public class StatisticsAnalyzer {

    public Map<String, Object> analyzeStandings(LeagueStanding standing) {
        Map<String, Object> statistics = new HashMap<>();

        List<StandingEntry> entries = standing.getEntries();

        // Durchschnittliche Tore berechnen
        double avgGoals = calculateAverageGoals(entries);
        statistics.put("avgGoals", avgGoals);

        // Beste Offensive finden
        StandingEntry topOffense = findTopOffense(entries);
        statistics.put("topOffense", topOffense.getTeamName());
        statistics.put("topOffenseGoals", topOffense.getGoalsFor());

        // Beste Defensive finden
        StandingEntry topDefense = findTopDefense(entries);
        statistics.put("topDefense", topDefense.getTeamName());
        statistics.put("topDefenseGoals", topDefense.getGoalsAgainst());

        // Team mit meisten Punkten
        StandingEntry topTeam = entries.get(0); // Bereits sortiert
        statistics.put("topTeam", topTeam.getTeamName());
        statistics.put("topPoints", topTeam.getPoints());

        return statistics;
    }

    private double calculateAverageGoals(List<StandingEntry> entries) {
        int totalGoals = 0;
        int totalGames = 0;

        for (StandingEntry entry : entries) {
            totalGoals += entry.getGoalsFor();
            totalGames += entry.getPlayedGames();
        }

        return totalGames > 0 ? (double) totalGoals / totalGames : 0.0;
    }

    private StandingEntry findTopOffense(List<StandingEntry> entries) {
        StandingEntry top = entries.get(0);

        for (StandingEntry entry : entries) {
            if (entry.getGoalsFor() > top.getGoalsFor()) {
                top = entry;
            }
        }

        return top;
    }

    private StandingEntry findTopDefense(List<StandingEntry> entries) {
        StandingEntry top = entries.get(0);

        for (StandingEntry entry : entries) {
            if (entry.getGoalsAgainst() < top.getGoalsAgainst()) {
                top = entry;
            }
        }

        return top;
    }
}