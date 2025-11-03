package ch.bbw.m320.stocktrading.model;

import java.math.BigDecimal;

/**
 * Represents a stock purchase transaction.
 * Extends Transaction class - demonstrates inheritance.
 *
 * @author Thomas
 * @version 1.0
 */
public class BuyTransaction extends Transaction {

    /**
     * Creates a new buy transaction.
     *
     * @param stockSymbol The symbol of the stock being purchased
     * @param quantity The number of shares to buy
     * @param pricePerShare The price per share at purchase time
     */
    public BuyTransaction(String stockSymbol, int quantity, BigDecimal pricePerShare) {
        super(stockSymbol, quantity, pricePerShare, TransactionType.BUY);
    }

    /**
     * Returns the balance impact (negative for purchases).
     * Demonstrates polymorphism - overrides abstract method from parent.
     */
    @Override
    public BigDecimal getBalanceImpact() {
        return getTotalValue().negate(); // Negative because buying costs money
    }

    /**
     * Provides buy-specific transaction details.
     * Demonstrates polymorphism.
     */
    @Override
    public String getTransactionDetails() {
        return String.format("Bought %d shares of %s at $%.2f per share (Total: $%.2f)",
                getQuantity(), getStockSymbol(), getPricePerShare(), getTotalValue());
    }
}
