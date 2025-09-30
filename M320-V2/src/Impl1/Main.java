package Impl1;

public class Main {
    public static void main(String[] args) {
        Team team = new Team();

        team.addPlayer(new Goalkeeper("Alisson", 32, 120000));
        team.addPlayer(new Defender("Van Dijk", 33, 200000));
        team.addPlayer(new Defender("Frimpong", 26, 180000));
        team.addPlayer(new Defender("Kerkez", 31, 100000));
        team.addPlayer(new Defender("Konate", 25, 70000));
        team.addPlayer(new Midfielder("Mac Allister", 26, 150000));
        team.addPlayer(new Midfielder("Szoboszlai", 24, 100000));
        team.addPlayer(new Midfielder("Gravenberch", 22, 80000));
        team.addPlayer(new Forward("Salah", 33, 350000));
        team.addPlayer(new Forward("Isak", 25, 140000));
        team.addPlayer(new Forward("Wirtz", 28, 120000));

        team.displayTeam();
        System.out.println("Total salary cost: $" + team.getTotalSalaryCost());

        team.trainAllPlayers();

        System.out.println("\nAfter training:");
        team.displayTeam();
        System.out.println("Total salary cost: $" + team.getTotalSalaryCost());
    }
}
