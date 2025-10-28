import java.util.Scanner;

public class InputHandler {
    private Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public int selectLeague() {
        System.out.println("\nVerfügbare Ligen:");
        System.out.println("[1] Premier League (England)");
        System.out.println("[2] Bundesliga (Deutschland)");
        System.out.println("[3] La Liga (Spanien)");
        System.out.println("[4] Serie A (Italien)");
        System.out.println("[5] Ligue 1 (Frankreich)");
        System.out.print("\nWählen Sie eine Liga (1-5): ");

        String input = scanner.nextLine().trim();


        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // Wird von Validator abgefangen
        }
    }

    public boolean askContinue() {
        System.out.print("\nWeitere Liga analysieren? (j/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("j") || answer.equals("ja") || answer.equals("y");
    }

    public void close() {
        scanner.close();
    }
}