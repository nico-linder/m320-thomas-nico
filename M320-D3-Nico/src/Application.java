import java.io.IOException;

public class Application {
    private InputHandler inputHandler;
    private InputValidator validator;
    private FootballService footballService;
    private StatisticsAnalyzer statisticsAnalyzer;
    private OutputFormatter outputFormatter;

    public Application() {
        this.inputHandler = new InputHandler();
        this.validator = new InputValidator();
        this.footballService = new FootballService();
        this.statisticsAnalyzer = new StatisticsAnalyzer();
        this.outputFormatter = new OutputFormatter();
    }

    public void run() {
        outputFormatter.displayWelcome();

        boolean running = true;

        while (running) {
            try {
                // Delegation: Input sammeln
                int choice = inputHandler.selectLeague();

                // Delegation: Input validieren
                validator.validateLeagueChoice(choice);

                // Liga-Code bestimmen
                String leagueCode = getLeagueCode(choice);

                outputFormatter.displayLoading(getLeagueName(choice));

                // Delegation: API aufrufen
                LeagueStanding standing = footballService.getStandings(leagueCode);

                // Delegation: Statistiken berechnen
                java.util.Map<String, Object> statistics =
                        statisticsAnalyzer.analyzeStandings(standing);

                // Delegation: Ergebnisse anzeigen
                outputFormatter.displayStandings(standing, statistics);

                // Nächste Abfrage?
                running = inputHandler.askContinue();

            } catch (ValidationException e) {
                outputFormatter.displayError("Validierung: " + e.getMessage());
            } catch (IOException e) {
                outputFormatter.displayError("API-Fehler: " + e.getMessage());
                running = false;
            } catch (Exception e) {
                outputFormatter.displayError("Unerwarteter Fehler: " + e.getMessage());
                running = false;
            }
        }

        inputHandler.close();
        outputFormatter.displayGoodbye();
    }

    private String getLeagueCode(int choice) {
        switch (choice) {
            case 1: return "PL";   // Premier League
            case 2: return "BL1";  // Bundesliga
            case 3: return "PD";   // Primera Division (La Liga)
            case 4: return "SA";   // Serie A
            case 5: return "FL1";  // Ligue 1
            default: return "PL";
        }
    }

    private String getLeagueName(int choice) {
        switch (choice) {
            case 1: return "Premier League";
            case 2: return "Bundesliga";
            case 3: return "La Liga";
            case 4: return "Serie A";
            case 5: return "Ligue 1";
            default: return "Liga";
        }
    }
}