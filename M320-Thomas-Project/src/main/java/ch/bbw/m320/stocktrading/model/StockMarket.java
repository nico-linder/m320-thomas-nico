package ch.bbw.m320.stocktrading.model;

import java.math.BigDecimal;
import java.util.*;

/**
 * Represents the stock market that manages all available stocks.
 * DESIGN PATTERN: Singleton Pattern
 * Reason: There should only be one instance of the stock market in the application.
 * This ensures all users trade on the same market with consistent prices.
 *
 * Clean Code: Single Responsibility - manages available stocks and their prices
 *
 * @author Thomas
 * @version 1.0
 */
public class StockMarket {
    // Singleton instance
    private static StockMarket instance;

    private final Map<String, Stock> availableStocks;

    /**
     * Private constructor to prevent direct instantiation.
     * This is part of the Singleton pattern.
     */
    private StockMarket() {
        this.availableStocks = new HashMap<>();
        initializeDefaultStocks();
    }

    /**
     * Gets the singleton instance of StockMarket.
     * DESIGN PATTERN: Singleton Pattern - thread-safe lazy initialization
     *
     * @return The single instance of StockMarket
     */
    public static synchronized StockMarket getInstance() {
        if (instance == null) {
            instance = new StockMarket();
        }
        return instance;
    }

    /**
     * Initializes the market with some default stocks.
     * Clean Code: Private helper method for initialization
     * AI-Generated: Stock data initialization
     */
    private void initializeDefaultStocks() {
        // Technology stocks
        addStock(new Stock("AAPL", "Apple Inc.", new BigDecimal("175.50")));
        addStock(new Stock("GOOGL", "Alphabet Inc.", new BigDecimal("140.25")));
        addStock(new Stock("MSFT", "Microsoft Corporation", new BigDecimal("378.90")));
        addStock(new Stock("AMZN", "Amazon.com Inc.", new BigDecimal("155.75")));

        // Financial stocks
        addStock(new Stock("JPM", "JPMorgan Chase & Co.", new BigDecimal("156.80")));
        addStock(new Stock("BAC", "Bank of America Corp.", new BigDecimal("34.50")));

        // Other sectors
        addStock(new Stock("TSLA", "Tesla Inc.", new BigDecimal("242.15")));
        addStock(new Stock("NVDA", "NVIDIA Corporation", new BigDecimal("495.25")));
        addStock(new Stock("DIS", "The Walt Disney Company", new BigDecimal("92.40")));
        addStock(new Stock("NKE", "Nike Inc.", new BigDecimal("108.75")));
    }

    /**
     * Adds a new stock to the market.
     *
     * @param stock The stock to add
     */
    public void addStock(Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        availableStocks.put(stock.getSymbol(), stock);
    }

    /**
     * Gets a stock by its symbol.
     *
     * @param symbol The stock symbol
     * @return The stock, or null if not found
     */
    public Stock getStock(String symbol) {
        if (symbol == null) {
            return null;
        }
        return availableStocks.get(symbol.toUpperCase());
    }

    /**
     * Checks if a stock exists in the market.
     *
     * @param symbol The stock symbol to check
     * @return true if the stock exists
     */
    public boolean hasStock(String symbol) {
        if (symbol == null) {
            return false;
        }
        return availableStocks.containsKey(symbol.toUpperCase());
    }

    /**
     * Gets all available stocks.
     * Clean Code: Returns defensive copy to prevent external modification
     *
     * @return Collection of all available stocks
     */
    public Collection<Stock> getAllStocks() {
        return Collections.unmodifiableCollection(availableStocks.values());
    }

    /**
     * Gets all stock symbols.
     *
     * @return Set of all stock symbols
     */
    public Set<String> getAllStockSymbols() {
        return Collections.unmodifiableSet(availableStocks.keySet());
    }

    /**
     * Updates a stock's price.
     * This method demonstrates delegation to the Stock object.
     *
     * @param symbol The stock symbol
     * @param newPrice The new price
     */
    public void updateStockPrice(String symbol, BigDecimal newPrice) {
        Stock stock = getStock(symbol);
        if (stock == null) {
            throw new IllegalArgumentException("Stock not found: " + symbol);
        }
        stock.updatePrice(newPrice);
    }

    /**
     * Simulates random price changes for all stocks.
     * AI-Generated: Price simulation logic
     * This could be used for demonstration purposes.
     */
    public void simulatePriceChanges() {
        Random random = new Random();

        for (Stock stock : availableStocks.values()) {
            // Random change between -5% and +5%
            double changePercent = (random.nextDouble() * 10) - 5;
            BigDecimal currentPrice = stock.getCurrentPrice();
            BigDecimal change = currentPrice.multiply(BigDecimal.valueOf(changePercent / 100));
            BigDecimal newPrice = currentPrice.add(change);

            // Ensure price doesn't go below $1
            if (newPrice.compareTo(BigDecimal.ONE) < 0) {
                newPrice = BigDecimal.ONE;
            }

            stock.updatePrice(newPrice);
        }
    }

    /**
     * Gets the number of stocks in the market.
     *
     * @return The count of available stocks
     */
    public int getStockCount() {
        return availableStocks.size();
    }

    @Override
    public String toString() {
        return String.format("Stock Market with %d available stocks", availableStocks.size());
    }
}
