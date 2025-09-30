import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank myBank = new Bank("Thomas & Nico Bank");

        Account accountNico = new Account("CH01", "Nico Linder", 6769.00);
        Account accountThomas = new Account("CH02", "Thomas Stern", 69420.00);
        myBank.addAccount(accountNico);
        myBank.addAccount(accountThomas);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- " + myBank.getName() + " ---");
            System.out.println("1. Show all accounts");
            System.out.println("2. Deposit money");
            System.out.println("3. Transfer money");
            System.out.println("4. Show account statement");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    myBank.displayAllAccounts();
                    break;

                case "2":
                    System.out.print("Enter IBAN: ");
                    String iban = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double depositAmount = Double.parseDouble(scanner.nextLine());

                    Account depositAcc = myBank.findAccount(iban);
                    if (depositAcc != null) {
                        depositAcc.deposit(depositAmount);
                        System.out.printf("New balance: %.2f CHF\n", depositAcc.getBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case "3":
                    System.out.print("From IBAN: ");
                    String from = scanner.nextLine();
                    System.out.print("To IBAN: ");
                    String to = scanner.nextLine();
                    System.out.print("Amount: ");
                    double transferAmount = Double.parseDouble(scanner.nextLine());

                    myBank.executeTransfer(from, to, transferAmount);
                    break;

                case "4":
                    System.out.print("Enter IBAN: ");
                    String ibanStatement = scanner.nextLine();
                    Account accStatement = myBank.findAccount(ibanStatement);
                    if (accStatement != null) {
                        accStatement.printStatement();
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case "0":
                    running = false;
                    System.out.println("Exiting. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }

        scanner.close();
    }
}
