public class Main {
    public static void main(String[] args) {
        Bank myBank = new Bank("Thomas & Nico Bank");

        Account accountNico = new Account("CH01", "Nico Linder", 6769.00);
        Account accountThomas = new Account("CH02", "Thomas Stern", 69420.00);
        myBank.addAccount(accountNico);
        myBank.addAccount(accountThomas);

        myBank.displayAllAccounts();

        System.out.println("\n--- Performing deposit ---");
        accountNico.deposit(150.00);
        System.out.printf("New balance for Nico: %.2f CHF\n", accountNico.getBalance());

        myBank.executeTransfer("CH02", "CH01", 300.00);

        System.out.println("\n--- Final Account Statuses ---");
        myBank.displayAllAccounts();

        accountNico.printStatement();
        accountThomas.printStatement();
    }
}