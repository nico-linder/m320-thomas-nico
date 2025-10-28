import java.util.List;

public class LeagueStanding {
    private String leagueName;
    private List<StandingEntry> entries;

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public List<StandingEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<StandingEntry> entries) {
        this.entries = entries;
    }
}