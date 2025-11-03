package ch.bbw.m320.stocktrading.model;

import java.math.BigDecimal;

/**
 * Represents a stock sale transaction.
 * Extends Transaction class - demonstrates inheritance.
 *
 * @author Thomas
 * @version 1.0
 */
public class SellTransaction extends Transaction {

    /**
     * Creates a new sell transaction.
     *
     * @param stockSymbol The symbol of the stock being sold
     * @param quantity The number of shares to sell
     * @param pricePerShare The price per share at sale time
     */
    public SellTransaction(String stockSymbol, int quantity, BigDecimal pricePerShare) {
        super(stockSymbol, quantity, pricePerShare, TransactionType.SELL);
    }

    /**
     * Returns the balance impact (positive for sales).
     * Demonstrates polymorphism - overrides abstract method from parent.
     */
    @Override
    public BigDecimal getBalanceImpact() {
        return getTotalValue(); // Positive because selling adds money
    }

    /**
     * Provides sell-specific transaction details.
     * Demonstrates polymorphism.
     */
    @Override
    public String getTransactionDetails() {
        return String.format("Sold %d shares of %s at $%.2f per share (Total: $%.2f)",
                getQuantity(), getStockSymbol(), getPricePerShare(), getTotalValue());
    }
}
