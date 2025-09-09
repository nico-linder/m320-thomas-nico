import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String accountHolderName;
        private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        logTransaction("Initial Deposit", initialDeposit);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            logTransaction("Deposit", amount);
            System.out.println(String.format("SUCCESS: %.2f CHF deposited to account %s.", amount, accountNumber));
        } else {
            System.out.println("ERROR: Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            logTransaction("Withdrawal", amount);
            System.out.println(String.format("SUCCESS: %.2f CHF withdrawn from account %s.", amount, accountNumber));
            return true;
        } else {
            System.out.println(String.format("ERROR: Insufficient funds or invalid amount for withdrawal from account %s.", accountNumber));
            return false;
        }
    }

    private void logTransaction(String type, double amount) {
        Transaction transaction = new Transaction(type, amount);
        this.transactionHistory.add(transaction);
    }

    public void printStatement() {
        System.out.println("\n--- Account Statement for: " + accountHolderName + " (" + accountNumber + ") ---");
        System.out.println(String.format("Current Balance: %.2f CHF", balance));
        System.out.println("--- Transaction History ---");
        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
        System.out.println("---------------------------------");
    }
}