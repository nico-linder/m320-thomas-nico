package ch.bbw.m320.stocktrading.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * Represents a user's stock portfolio.
 * Manages stock holdings and their quantities.
 * Clean Code: Single Responsibility - only manages portfolio holdings
 *
 * @author Thomas
 * @version 1.0
 */
public class Portfolio {
    private final Map<String, Integer> holdings; // stockSymbol -> quantity

    /**
     * Creates a new empty portfolio.
     */
    public Portfolio() {
        this.holdings = new HashMap<>();
    }

    /**
     * Adds stocks to the portfolio.
     * Clean Code: Method does one thing - adds stock holdings
     *
     * @param stockSymbol The stock symbol to add
     * @param quantity The quantity to add
     */
    public void addStock(String stockSymbol, int quantity) {
        if (stockSymbol == null || stockSymbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        String symbol = stockSymbol.toUpperCase();
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    /**
     * Removes stocks from the portfolio.
     *
     * @param stockSymbol The stock symbol to remove
     * @param quantity The quantity to remove
     * @throws IllegalArgumentException if quantity exceeds holdings
     */
    public void removeStock(String stockSymbol, int quantity) {
        if (stockSymbol == null || stockSymbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        String symbol = stockSymbol.toUpperCase();
        int currentQuantity = holdings.getOrDefault(symbol, 0);

        if (currentQuantity < quantity) {
            throw new IllegalArgumentException(
                    String.format("Insufficient holdings. Have: %d, Trying to remove: %d",
                            currentQuantity, quantity));
        }

        int newQuantity = currentQuantity - quantity;
        if (newQuantity == 0) {
            holdings.remove(symbol);
        } else {
            holdings.put(symbol, newQuantity);
        }
    }

    /**
     * Gets the quantity of a specific stock in the portfolio.
     *
     * @param stockSymbol The stock symbol to check
     * @return The quantity owned, or 0 if not owned
     */
    public int getQuantity(String stockSymbol) {
        if (stockSymbol == null) {
            return 0;
        }
        return holdings.getOrDefault(stockSymbol.toUpperCase(), 0);
    }

    /**
     * Checks if the portfolio contains a specific stock.
     *
     * @param stockSymbol The stock symbol to check
     * @return true if the portfolio contains this stock
     */
    public boolean hasStock(String stockSymbol) {
        if (stockSymbol == null) {
            return false;
        }
        return holdings.containsKey(stockSymbol.toUpperCase());
    }

    /**
     * Gets all holdings in the portfolio.
     * Clean Code: Returns defensive copy to prevent external modification
     *
     * @return Unmodifiable map of stock symbols to quantities
     */
    public Map<String, Integer> getHoldings() {
        return Collections.unmodifiableMap(holdings);
    }

    /**
     * Calculates the total value of the portfolio.
     *
     * @param stockMarket The stock market to get current prices from
     * @return The total portfolio value
     */
    public BigDecimal calculateTotalValue(StockMarket stockMarket) {
        BigDecimal totalValue = BigDecimal.ZERO;

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();

            Stock stock = stockMarket.getStock(symbol);
            if (stock != null) {
                BigDecimal stockValue = stock.getCurrentPrice()
                        .multiply(BigDecimal.valueOf(quantity));
                totalValue = totalValue.add(stockValue);
            }
        }

        return totalValue;
    }

    /**
     * Checks if the portfolio is empty.
     *
     * @return true if no stocks are held
     */
    public boolean isEmpty() {
        return holdings.isEmpty();
    }

    /**
     * Gets the number of different stocks in the portfolio.
     *
     * @return The count of unique stocks
     */
    public int getStockCount() {
        return holdings.size();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Portfolio is empty";
        }

        StringBuilder sb = new StringBuilder("Portfolio Holdings:\n");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            sb.append(String.format("  %s: %d shares\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}
