package ch.bbw.m320.stocktrading.ui;

import ch.bbw.m320.stocktrading.exception.InsufficientBalanceException;
import ch.bbw.m320.stocktrading.exception.InsufficientStockException;
import ch.bbw.m320.stocktrading.exception.StockNotFoundException;
import ch.bbw.m320.stocktrading.model.*;
import ch.bbw.m320.stocktrading.repository.UserRepository;
import ch.bbw.m320.stocktrading.service.TradingService;

import java.math.BigDecimal;
import java.util.*;

/**
 * Console-based user interface for the stock trading application.
 * Clean Code: Single Responsibility - handles only user interaction
 * Separation of Concerns: UI is separate from business logic
 *
 * @author Thomas
 * @version 1.0
 */
public class ConsoleUI {
    private final Scanner scanner;
    private final TradingService tradingService;
    private final UserRepository userRepository;
    private final StockMarket stockMarket;
    private User currentUser;

    /**
     * Creates a new ConsoleUI.
     * Clean Code: Dependency injection through constructor
     */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.tradingService = new TradingService();
        this.userRepository = new UserRepository();
        this.stockMarket = StockMarket.getInstance();
    }

    /**
     * Starts the application main loop.
     */
    public void start() {
        printWelcome();

        while (true) {
            if (currentUser == null) {
                handleLoginMenu();
            } else {
                handleMainMenu();
            }
        }
    }

    /**
     * Prints welcome message.
     * Clean Code: Small method with single purpose
     */
    private void printWelcome() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   STOCK TRADING SIMULATOR");
        System.out.println("=".repeat(50));
    }

    /**
     * Handles the login/registration menu.
     */
    private void handleLoginMenu() {
        System.out.println("\n--- LOGIN ---");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        try {
            int choice = readInt();
            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleRegistration();
                case 3 -> handleExit();
                default -> System.out.println("Invalid option. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear buffer
        }
    }

    /**
     * Handles user login.
     * Exception Handling: Validates user input
     */
    private void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            currentUser = userOpt.get();
            System.out.println("Welcome back, " + currentUser.getUsername() + "!");
        } else {
            System.out.println("User not found. Please register first.");
        }
    }

    /**
     * Handles new user registration.
     */
    private void handleRegistration() {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine().trim();

        // Validate username
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        if (userRepository.existsByUsername(username)) {
            System.out.println("Username already exists. Please choose another.");
            return;
        }

        System.out.print("Initial balance (default 10000): ");
        BigDecimal initialBalance;
        try {
            String input = scanner.nextLine().trim();
            initialBalance = input.isEmpty() ? new BigDecimal("10000") : new BigDecimal(input);

            if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Balance cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Using default balance of $10,000.");
            initialBalance = new BigDecimal("10000");
        }

        // Create and save new user
        currentUser = new User(username, initialBalance);
        userRepository.save(currentUser);
        System.out.println("Registration successful! Welcome, " + username + "!");
    }

    /**
     * Handles the main application menu.
     */
    private void handleMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("User: " + currentUser.getUsername() +
                " | Balance: $" + String.format("%.2f", currentUser.getBalance()));
        System.out.println("\n1. View available stocks");
        System.out.println("2. Buy stock");
        System.out.println("3. Sell stock");
        System.out.println("4. View my portfolio");
        System.out.println("5. View transaction history");
        System.out.println("6. View stock price history");
        System.out.println("7. View account summary");
        System.out.println("8. Simulate price changes");
        System.out.println("9. Logout");
        System.out.print("Choose an option: ");

        try {
            int choice = readInt();
            switch (choice) {
                case 1 -> handleViewStocks();
                case 2 -> handleBuyStock();
                case 3 -> handleSellStock();
                case 4 -> handleViewPortfolio();
                case 5 -> handleViewTransactionHistory();
                case 6 -> handleViewPriceHistory();
                case 7 -> handleAccountSummary();
                case 8 -> handleSimulatePriceChanges();
                case 9 -> handleLogout();
                default -> System.out.println("Invalid option. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear buffer
        }
    }

    /**
     * Displays all available stocks.
     */
    private void handleViewStocks() {
        System.out.println("\n--- AVAILABLE STOCKS ---");
        Collection<Stock> stocks = stockMarket.getAllStocks();

        System.out.printf("%-10s %-30s %10s%n", "SYMBOL", "NAME", "PRICE");
        System.out.println("-".repeat(52));

        for (Stock stock : stocks) {
            System.out.printf("%-10s %-30s $%9.2f%n",
                    stock.getSymbol(),
                    stock.getName(),
                    stock.getCurrentPrice());
        }
    }

    /**
     * Handles stock purchase.
     * Exception Handling: Catches and displays appropriate error messages
     */
    private void handleBuyStock() {
        System.out.println("\n--- BUY STOCK ---");
        System.out.print("Stock symbol: ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        try {
            BigDecimal price = tradingService.getStockPrice(symbol);
            System.out.printf("Current price: $%.2f%n", price);
            System.out.printf("Your balance: $%.2f%n", currentUser.getBalance());

            System.out.print("Quantity to buy: ");
            int quantity = readInt();

            if (quantity <= 0) {
                System.out.println("Quantity must be positive.");
                return;
            }

            BigDecimal totalCost = tradingService.calculatePurchaseCost(symbol, quantity);
            System.out.printf("Total cost: $%.2f%n", totalCost);
            System.out.print("Confirm purchase? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("yes") || confirm.equals("y")) {
                BuyTransaction transaction = tradingService.buyStock(currentUser, symbol, quantity);
                userRepository.save(currentUser); // Persist changes
                System.out.println("\nPurchase successful!");
                System.out.println(transaction.getTransactionDetails());
            } else {
                System.out.println("Purchase cancelled.");
            }

        } catch (StockNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid quantity. Please enter a valid number.");
            scanner.nextLine(); // Clear buffer
        }
    }

    /**
     * Handles stock sale.
     */
    private void handleSellStock() {
        System.out.println("\n--- SELL STOCK ---");

        if (currentUser.getPortfolio().isEmpty()) {
            System.out.println("Your portfolio is empty. You don't own any stocks to sell.");
            return;
        }

        System.out.print("Stock symbol: ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        try {
            int ownedQuantity = currentUser.getPortfolio().getQuantity(symbol);
            if (ownedQuantity == 0) {
                System.out.println("You don't own any shares of " + symbol);
                return;
            }

            BigDecimal price = tradingService.getStockPrice(symbol);
            System.out.printf("Current price: $%.2f%n", price);
            System.out.printf("You own: %d shares%n", ownedQuantity);

            System.out.print("Quantity to sell: ");
            int quantity = readInt();

            if (quantity <= 0) {
                System.out.println("Quantity must be positive.");
                return;
            }

            BigDecimal totalRevenue = tradingService.calculateSaleRevenue(symbol, quantity);
            System.out.printf("Total revenue: $%.2f%n", totalRevenue);
            System.out.print("Confirm sale? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("yes") || confirm.equals("y")) {
                SellTransaction transaction = tradingService.sellStock(currentUser, symbol, quantity);
                userRepository.save(currentUser); // Persist changes
                System.out.println("\nSale successful!");
                System.out.println(transaction.getTransactionDetails());
            } else {
                System.out.println("Sale cancelled.");
            }

        } catch (StockNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid quantity. Please enter a valid number.");
            scanner.nextLine(); // Clear buffer
        }
    }

    /**
     * Displays user's portfolio.
     */
    private void handleViewPortfolio() {
        System.out.println("\n--- MY PORTFOLIO ---");
        Portfolio portfolio = currentUser.getPortfolio();

        if (portfolio.isEmpty()) {
            System.out.println("Your portfolio is empty.");
            return;
        }

        System.out.printf("%-10s %-30s %10s %10s %15s%n",
                "SYMBOL", "NAME", "QUANTITY", "PRICE", "TOTAL VALUE");
        System.out.println("-".repeat(77));

        BigDecimal totalPortfolioValue = BigDecimal.ZERO;

        for (Map.Entry<String, Integer> entry : portfolio.getHoldings().entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            Stock stock = stockMarket.getStock(symbol);

            if (stock != null) {
                BigDecimal price = stock.getCurrentPrice();
                BigDecimal totalValue = price.multiply(BigDecimal.valueOf(quantity));
                totalPortfolioValue = totalPortfolioValue.add(totalValue);

                System.out.printf("%-10s %-30s %10d $%9.2f $%14.2f%n",
                        symbol,
                        stock.getName(),
                        quantity,
                        price,
                        totalValue);
            }
        }

        System.out.println("-".repeat(77));
        System.out.printf("Total Portfolio Value: $%.2f%n", totalPortfolioValue);
        System.out.printf("Cash Balance: $%.2f%n", currentUser.getBalance());
        System.out.printf("Total Account Value: $%.2f%n",
                totalPortfolioValue.add(currentUser.getBalance()));
    }

    /**
     * Displays transaction history.
     */
    private void handleViewTransactionHistory() {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        List<Transaction> transactions = currentUser.getTransactionHistory();

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

        System.out.println("\nTotal transactions: " + transactions.size());
    }

    /**
     * Displays stock price history.
     */
    private void handleViewPriceHistory() {
        System.out.print("\nEnter stock symbol: ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        Stock stock = stockMarket.getStock(symbol);
        if (stock == null) {
            System.out.println("Stock not found: " + symbol);
            return;
        }

        System.out.println("\n--- PRICE HISTORY FOR " + stock.getName() + " ---");
        List<Stock.PricePoint> history = stock.getPriceHistory();

        if (history.isEmpty()) {
            System.out.println("No price history available.");
            return;
        }

        for (Stock.PricePoint point : history) {
            System.out.println(point);
        }
    }

    /**
     * Displays account summary.
     */
    private void handleAccountSummary() {
        System.out.println("\n--- ACCOUNT SUMMARY ---");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.printf("Cash Balance: $%.2f%n", currentUser.getBalance());

        BigDecimal portfolioValue = currentUser.getPortfolio().calculateTotalValue(stockMarket);
        System.out.printf("Portfolio Value: $%.2f%n", portfolioValue);

        BigDecimal totalValue = currentUser.getTotalAccountValue(stockMarket);
        System.out.printf("Total Account Value: $%.2f%n", totalValue);

        System.out.println("Number of Stock Holdings: " + currentUser.getPortfolio().getStockCount());
        System.out.println("Number of Transactions: " + currentUser.getTransactionHistory().size());
    }

    /**
     * Simulates random price changes for demonstration.
     */
    private void handleSimulatePriceChanges() {
        System.out.println("\nSimulating price changes...");
        stockMarket.simulatePriceChanges();
        System.out.println("Prices updated!");
    }

    /**
     * Handles user logout.
     */
    private void handleLogout() {
        System.out.println("Logging out...");
        userRepository.save(currentUser); // Save before logout
        currentUser = null;
        System.out.println("Goodbye!");
    }

    /**
     * Handles application exit.
     */
    private void handleExit() {
        System.out.println("Thank you for using Stock Trading Simulator!");
        scanner.close();
        System.exit(0);
    }

    /**
     * Reads an integer from user input with proper error handling.
     * Clean Code: DRY principle - extracted common input reading logic
     */
    private int readInt() {
        int value = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return value;
    }
}
