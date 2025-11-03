package ch.bbw.m320.stocktrading.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a user of the stock trading system.
 * Manages user account, balance, portfolio, and transaction history.
 * Clean Code: Single Responsibility - manages user data and account operations
 *
 * @author Thomas
 * @version 1.0
 */
public class User {
    private final String userId;
    private final String username;
    private BigDecimal balance;
    private final Portfolio portfolio;
    private final List<Transaction> transactionHistory;

    /**
     * Creates a new user with an initial balance.
     *
     * @param username The username
     * @param initialBalance The starting balance
     */
    public User(String username, BigDecimal initialBalance) {
        // Input validation (Exception Handling)
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.balance = initialBalance;
        this.portfolio = new Portfolio();
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Adds funds to the user's balance.
     * Clean Code: Small method with clear purpose
     *
     * @param amount The amount to add
     */
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    /**
     * Removes funds from the user's balance.
     *
     * @param amount The amount to withdraw
     * @throws IllegalArgumentException if insufficient balance
     */
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                    String.format("Insufficient balance. Available: $%.2f, Requested: $%.2f",
                            balance, amount));
        }
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Checks if the user has sufficient balance for a transaction.
     *
     * @param amount The amount to check
     * @return true if balance is sufficient
     */
    public boolean hasSufficientBalance(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    /**
     * Adds a transaction to the user's history.
     * Clean Code: Encapsulation - transaction history is managed internally
     *
     * @param transaction The transaction to add
     */
    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        transactionHistory.add(transaction);
    }

    /**
     * Gets the user's transaction history.
     * Clean Code: Returns defensive copy to prevent external modification
     *
     * @return Unmodifiable list of transactions
     */
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    /**
     * Calculates the total portfolio value including cash balance.
     *
     * @param stockMarket The stock market to get current prices from
     * @return The total account value (balance + portfolio value)
     */
    public BigDecimal getTotalAccountValue(StockMarket stockMarket) {
        BigDecimal portfolioValue = portfolio.calculateTotalValue(stockMarket);
        return balance.add(portfolioValue);
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    @Override
    public String toString() {
        return String.format("User: %s (Balance: $%.2f, Holdings: %d stocks)",
                username, balance, portfolio.getStockCount());
    }
}
