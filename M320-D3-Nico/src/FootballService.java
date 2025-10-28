import java.io.*;
import java.net.*;
import java.util.*;

public class FootballService {
    private static final String API_URL = "https://api.football-data.org/v4";
    private static final String API_KEY = "7f95ff66781c4115852680350eef2bac";

    public LeagueStanding getStandings(String leagueCode) throws IOException {
        String url = API_URL + "/competitions/" + leagueCode + "/standings";
        String jsonResponse = executeRequest(url);
        return parseStandings(jsonResponse, leagueCode);
    }

    private String executeRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Header setzen
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-Auth-Token", API_KEY);
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new IOException("API Request fehlgeschlagen: HTTP " + responseCode);
        }

        // Response lesen
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private LeagueStanding parseStandings(String json, String leagueCode) {
        LeagueStanding standing = new LeagueStanding();
        standing.setLeagueName(getLeagueNameFromCode(leagueCode));

        List<StandingEntry> entries = new ArrayList<>();

        entries.add(createDemoEntry(1, "Bayer Leverkusen", 10, 8, 2, 0, 28, 10));
        entries.add(createDemoEntry(2, "Bayern München", 10, 8, 1, 1, 32, 11));
        entries.add(createDemoEntry(3, "RB Leipzig", 10, 7, 2, 1, 24, 9));
        entries.add(createDemoEntry(4, "SC Freiburg", 10, 6, 2, 2, 19, 12));
        entries.add(createDemoEntry(5, "VfB Stuttgart", 10, 5, 3, 2, 23, 16));

        standing.setEntries(entries);

        return standing;
    }

    private StandingEntry createDemoEntry(int pos, String team, int played,
                                          int won, int draw, int lost,
                                          int goalsFor, int goalsAgainst) {
        StandingEntry entry = new StandingEntry();
        entry.setPosition(pos);
        entry.setTeamName(team);
        entry.setPlayedGames(played);
        entry.setWon(won);
        entry.setDraw(draw);
        entry.setLost(lost);
        entry.setGoalsFor(goalsFor);
        entry.setGoalsAgainst(goalsAgainst);
        entry.setGoalDifference(goalsFor - goalsAgainst);
        entry.setPoints(won * 3 + draw);
        return entry;
    }

    private String getLeagueNameFromCode(String code) {
        switch (code) {
            case "PL": return "Premier League";
            case "BL1": return "Bundesliga";
            case "PD": return "La Liga";
            case "SA": return "Serie A";
            case "FL1": return "Ligue 1";
            default: return "Liga";
        }
    }
}