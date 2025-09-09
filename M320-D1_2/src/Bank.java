import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accounts;
    private String bankName;

    public Bank(String name) {
        this.bankName = name;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("New account created for " + account.getAccountHolderName() + " with number " + account.getAccountNumber());
    }

    public String getBankName() {
        return bankName;
    }

    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean executeTransfer(String fromAccountNumber, String toAccountNumber, double amount) {
        System.out.println(String.format("\n>>> Attempting to transfer %.2f CHF from %s to %s...", amount, fromAccountNumber, toAccountNumber));

        Account fromAccount = findAccount(fromAccountNumber);
        Account toAccount = findAccount(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            System.out.println("ERROR: One or both accounts not found.");
            return false;
        }

        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            System.out.println(">>> Transfer successful!");
            return true;
        } else {
            System.out.println(">>> Transfer failed. See error message above.");
            return false;
        }
    }

    public void displayAllAccounts() {
        System.out.println("\n=== All Accounts at " + bankName + " ===");
        for (Account acc : accounts) {
            System.out.println(String.format("Holder: %-15s | Account: %s | Balance: %.2f CHF",
                    acc.getAccountHolderName(), acc.getAccountNumber(), acc.getBalance()));
        }
        System.out.println("========================================");
    }
}