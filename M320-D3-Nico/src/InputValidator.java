public class InputValidator {

    public void validateLeagueChoice(int choice) throws ValidationException {
        if (choice < 1 || choice > 5) {
            throw new ValidationException(
                    "Ungültige Liga-Auswahl! Bitte Zahl zwischen 1 und 5 eingeben."
            );
        }
    }

    public void validateNotEmpty(String input, String fieldName) throws ValidationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ValidationException(
                    fieldName + " darf nicht leer sein!"
            );
        }
    }
}