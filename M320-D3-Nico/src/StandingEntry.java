public class StandingEntry {
    private int position;
    private String teamName;
    private int playedGames;
    private int won;
    private int draw;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    private int points;

    // Constructor
    public StandingEntry(int position, String teamName, int playedGames, int won, int draw, int lost,
                         int goalsFor, int goalsAgainst, int goalDifference, int points) {
        this.position = position;
        this.teamName = teamName;
        this.playedGames = playedGames;
        this.won = won;
        this.draw = draw;
        this.lost = lost;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
        this.points = points;
    }

    public StandingEntry() {}

    // Getters
    public int getPosition() { return position; }
    public String getTeamName() { return teamName; }
    public int getPlayedGames() { return playedGames; }
    public int getWon() { return won; }
    public int getDraw() { return draw; }
    public int getLost() { return lost; }
    public int getGoalsFor() { return goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }
    public int getGoalDifference() { return goalDifference; }
    public int getPoints() { return points; }

    // Setters
    public void setPosition(int position) { this.position = position; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public void setPlayedGames(int playedGames) { this.playedGames = playedGames; }
    public void setWon(int won) { this.won = won; }
    public void setDraw(int draw) { this.draw = draw; }
    public void setLost(int lost) { this.lost = lost; }
    public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }
    public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }
    public void setGoalDifference(int goalDifference) { this.goalDifference = goalDifference; }
    public void setPoints(int points) { this.points = points; }

    public void updateGoalDifference() {
        this.goalDifference = goalsFor - goalsAgainst;
    }

    @Override
    public String toString() {
        return position + ". " + teamName +
                " | Pld: " + playedGames +
                " | W: " + won +
                " | D: " + draw +
                " | L: " + lost +
                " | GF: " + goalsFor +
                " | GA: " + goalsAgainst +
                " | GD: " + goalDifference +
                " | Pts: " + points;
    }
}
