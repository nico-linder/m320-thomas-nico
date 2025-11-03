package ch.bbw.m320.stocktrading.service;

import ch.bbw.m320.stocktrading.exception.InsufficientBalanceException;
import ch.bbw.m320.stocktrading.exception.InsufficientStockException;
import ch.bbw.m320.stocktrading.exception.StockNotFoundException;
import ch.bbw.m320.stocktrading.model.*;

import java.math.BigDecimal;

/**
 * Service class that handles trading operations (buying and selling stocks).
 * This class demonstrates Delegation pattern - it delegates work to appropriate objects.
 * Clean Code: Single Responsibility - handles only trading business logic
 *
 * @author Thomas
 * @version 1.0
 */
public class TradingService {
    private final StockMarket stockMarket;

    /**
     * Creates a new TradingService.
     * Clean Code: Dependency injection through constructor
     */
    public TradingService() {
        this.stockMarket = StockMarket.getInstance(); // Using Singleton
    }

    /**
     * Executes a buy order for a user.
     * Clean Code: Method name clearly describes what it does
     *
     * @param user The user making the purchase
     * @param stockSymbol The stock symbol to buy
     * @param quantity The number of shares to buy
     * @return The created transaction
     * @throws StockNotFoundException if the stock doesn't exist
     * @throws InsufficientBalanceException if user doesn't have enough money
     */
    public BuyTransaction buyStock(User user, String stockSymbol, int quantity)
            throws StockNotFoundException, InsufficientBalanceException {

        // Validate inputs
        validateUser(user);
        validateQuantity(quantity);

        // Get stock from market
        Stock stock = stockMarket.getStock(stockSymbol);
        if (stock == null) {
            throw new StockNotFoundException("Stock not found: " + stockSymbol);
        }

        // Calculate total cost
        BigDecimal totalCost = stock.getCurrentPrice().multiply(BigDecimal.valueOf(quantity));

        // Check if user has sufficient balance
        if (!user.hasSufficientBalance(totalCost)) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient balance. Required: $%.2f, Available: $%.2f",
                            totalCost, user.getBalance()));
        }

        // Execute the transaction
        // Clean Code: Small steps, each with clear purpose
        user.withdraw(totalCost);
        user.getPortfolio().addStock(stockSymbol, quantity);

        // Create and record transaction
        BuyTransaction transaction = new BuyTransaction(
                stockSymbol,
                quantity,
                stock.getCurrentPrice()
        );
        user.addTransaction(transaction);

        return transaction;
    }

    /**
     * Executes a sell order for a user.
     *
     * @param user The user making the sale
     * @param stockSymbol The stock symbol to sell
     * @param quantity The number of shares to sell
     * @return The created transaction
     * @throws StockNotFoundException if the stock doesn't exist
     * @throws InsufficientStockException if user doesn't have enough shares
     */
    public SellTransaction sellStock(User user, String stockSymbol, int quantity)
            throws StockNotFoundException, InsufficientStockException {

        // Validate inputs
        validateUser(user);
        validateQuantity(quantity);

        // Get stock from market
        Stock stock = stockMarket.getStock(stockSymbol);
        if (stock == null) {
            throw new StockNotFoundException("Stock not found: " + stockSymbol);
        }

        // Check if user has sufficient stock
        int ownedQuantity = user.getPortfolio().getQuantity(stockSymbol);
        if (ownedQuantity < quantity) {
            throw new InsufficientStockException(
                    String.format("Insufficient stock. Have: %d shares, Trying to sell: %d shares",
                            ownedQuantity, quantity));
        }

        // Execute the transaction
        BigDecimal totalRevenue = stock.getCurrentPrice().multiply(BigDecimal.valueOf(quantity));
        user.getPortfolio().removeStock(stockSymbol, quantity);
        user.deposit(totalRevenue);

        // Create and record transaction
        SellTransaction transaction = new SellTransaction(
                stockSymbol,
                quantity,
                stock.getCurrentPrice()
        );
        user.addTransaction(transaction);

        return transaction;
    }

    /**
     * Gets the current price quote for a stock.
     * Clean Code: Small method with single purpose
     *
     * @param stockSymbol The stock symbol
     * @return The current price
     * @throws StockNotFoundException if stock doesn't exist
     */
    public BigDecimal getStockPrice(String stockSymbol) throws StockNotFoundException {
        Stock stock = stockMarket.getStock(stockSymbol);
        if (stock == null) {
            throw new StockNotFoundException("Stock not found: " + stockSymbol);
        }
        return stock.getCurrentPrice();
    }

    /**
     * Calculates the total cost for buying a specific quantity of stock.
     *
     * @param stockSymbol The stock symbol
     * @param quantity The quantity to buy
     * @return The total cost
     * @throws StockNotFoundException if stock doesn't exist
     */
    public BigDecimal calculatePurchaseCost(String stockSymbol, int quantity)
            throws StockNotFoundException {
        validateQuantity(quantity);
        BigDecimal price = getStockPrice(stockSymbol);
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Calculates the potential revenue from selling a specific quantity of stock.
     *
     * @param stockSymbol The stock symbol
     * @param quantity The quantity to sell
     * @return The total revenue
     * @throws StockNotFoundException if stock doesn't exist
     */
    public BigDecimal calculateSaleRevenue(String stockSymbol, int quantity)
            throws StockNotFoundException {
        validateQuantity(quantity);
        BigDecimal price = getStockPrice(stockSymbol);
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    // Private validation methods (Clean Code: DRY principle)

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
}
