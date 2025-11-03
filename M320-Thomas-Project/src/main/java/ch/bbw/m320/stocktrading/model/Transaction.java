package ch.bbw.m320.stocktrading.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Abstract base class for all transaction types.
 * Demonstrates inheritance hierarchy and polymorphism.
 * This is part of the Template Method pattern where subclasses define transaction-specific behavior.
 *
 * @author Thomas
 * @version 1.0
 */
public abstract class Transaction {
    private final String transactionId;
    private final String stockSymbol;
    private final int quantity;
    private final BigDecimal pricePerShare;
    private final LocalDateTime timestamp;
    private final TransactionType type;

    /**
     * Protected constructor for subclasses.
     * Clean Code: Constructor validates all inputs
     */
    protected Transaction(String stockSymbol, int quantity, BigDecimal pricePerShare, TransactionType type) {
        // Input validation (Exception Handling)
        if (stockSymbol == null || stockSymbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (pricePerShare == null || pricePerShare.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price per share must be positive");
        }

        this.transactionId = UUID.randomUUID().toString();
        this.stockSymbol = stockSymbol.toUpperCase();
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.timestamp = LocalDateTime.now();
        this.type = type;
    }

    /**
     * Calculates the total value of the transaction.
     * Clean Code: Small, focused method with clear purpose
     */
    public BigDecimal getTotalValue() {
        return pricePerShare.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Abstract method to get the impact on balance.
     * Positive for sales, negative for purchases.
     * This demonstrates polymorphism - each subclass implements its own behavior.
     */
    public abstract BigDecimal getBalanceImpact();

    /**
     * Abstract method to format transaction details.
     * Each transaction type can customize its string representation.
     */
    public abstract String getTransactionDetails();

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPricePerShare() {
        return pricePerShare;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s", timestamp, type, getTransactionDetails());
    }

    /**
     * Enum for transaction types.
     * Clean Code: Using enums instead of string constants or magic numbers
     */
    public enum TransactionType {
        BUY,
        SELL
    }
}
