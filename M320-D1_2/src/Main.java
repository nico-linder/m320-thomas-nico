
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank myBank = new Bank("Thomas & Nico Bank");

        Account accountNico = new Account("CH01", "Nico Linder", 6769.00);
        Account accountThomas = new Account("CH02", "Thomas Stern", 69420.00);
        myBank.addAccount(accountNico);
        myBank.addAccount(accountThomas);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Welcome to " + myBank.getBankName() + " ---");
            System.out.println("1. Display all accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Print account statement");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    myBank.displayAllAccounts();
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    String accNumDeposit = scanner.nextLine();
                    System.out.print("Enter amount to deposit: ");
                    double amountDeposit = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    Account accDeposit = myBank.findAccount(accNumDeposit);
                    if (accDeposit != null) {
                        accDeposit.deposit(amountDeposit);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    String accNumWithdraw = scanner.nextLine();
                    System.out.print("Enter amount to withdraw: ");
                    double amountWithdraw = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    Account accWithdraw = myBank.findAccount(accNumWithdraw);
                    if (accWithdraw != null) {
                        accWithdraw.withdraw(amountWithdraw);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter source account number: ");
                    String fromAcc = scanner.nextLine();
                    System.out.print("Enter destination account number: ");
                    String toAcc = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double amountTransfer = scanner.nextDouble();
                    scanner.nextLine(); // consume newline
                    myBank.executeTransfer(fromAcc, toAcc, amountTransfer);
                    break;
                case 5:
                    System.out.print("Enter account number: ");
                    String accNumStatement = scanner.nextLine();
                    Account accStatement = myBank.findAccount(accNumStatement);
                    if (accStatement != null) {
                        accStatement.printStatement();
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 6:
                    System.out.println("Thank you for banking with us!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
