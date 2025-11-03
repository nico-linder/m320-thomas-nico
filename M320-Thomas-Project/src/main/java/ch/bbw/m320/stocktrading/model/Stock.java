package ch.bbw.m320.stocktrading.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a stock with its symbol, name, current price, and price history.
 * This class follows Clean Code principles with meaningful names and single responsibility.
 *
 * @author Thomas
 * @version 1.0
 */
public class Stock {
    private final String symbol;        // e.g., "AAPL"
    private final String name;          // e.g., "Apple Inc."
    private BigDecimal currentPrice;
    private final List<PricePoint> priceHistory;

    /**
     * Constructor for creating a new Stock.
     *
     * @param symbol The stock symbol (e.g., "AAPL")
     * @param name The full company name
     * @param initialPrice The initial price of the stock
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    public Stock(String symbol, String name, BigDecimal initialPrice) {
        // Clean Code: Validate input (Exception Handling)
        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock name cannot be null or empty");
        }
        if (initialPrice == null || initialPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Initial price must be positive");
        }

        this.symbol = symbol.toUpperCase();
        this.name = name;
        this.currentPrice = initialPrice;
        this.priceHistory = new ArrayList<>();

        // Add initial price to history
        addPricePoint(initialPrice);
    }

    /**
     * Updates the current price and adds it to the price history.
     *
     * @param newPrice The new price to set
     */
    public void updatePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.currentPrice = newPrice;
        addPricePoint(newPrice);
    }

    /**
     * Adds a price point to the history.
     * Private helper method following Clean Code principles (small, focused methods).
     */
    private void addPricePoint(BigDecimal price) {
        priceHistory.add(new PricePoint(price, LocalDateTime.now()));
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public List<PricePoint> getPriceHistory() {
        return new ArrayList<>(priceHistory); // Defensive copy
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f", name, symbol, currentPrice);
    }

    /**
     * Inner class representing a price point in time.
     * Encapsulates price and timestamp together.
     */
    public static class PricePoint {
        private final BigDecimal price;
        private final LocalDateTime timestamp;

        public PricePoint(BigDecimal price, LocalDateTime timestamp) {
            this.price = price;
            this.timestamp = timestamp;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return String.format("$%.2f at %s", price, timestamp);
        }
    }
}
