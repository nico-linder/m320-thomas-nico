package ch.tbz.m320.currency.ui;

import ch.tbz.m320.currency.controller.CurrencyController;
import ch.tbz.m320.currency.exception.ApiConnectionException;
import ch.tbz.m320.currency.exception.InvalidAmountException;
import ch.tbz.m320.currency.exception.InvalidCurrencyException;
import ch.tbz.m320.currency.model.CurrencyPair;
import ch.tbz.m320.currency.model.ExchangeRateData;
import ch.tbz.m320.currency.service.DataValidationService;

import java.util.List;
import java.util.Scanner;

public class CurrencyUI {
    private final CurrencyController controller;
    private final Scanner scanner;
    private final DataValidationService validationService;
    private boolean running;

    public CurrencyUI(CurrencyController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
        this.validationService = new DataValidationService();
        this.running = true;
    }

    public void start() {
        printWelcomeMessage();

        while (running) {
            printMainMenu();
            handleMainMenuChoice();
        }

        printGoodbyeMessage();
        scanner.close();
    }

    private void printWelcomeMessage() {
        System.out.println("====================================");
        System.out.println("   Welcome to Currency Converter    ");
        System.out.println("           System v1.0              ");
        System.out.println("====================================");
        System.out.println();
    }

    private void printMainMenu() {
        System.out.println("\n----- Main Menu -----");
        System.out.println("1. Convert currency");
        System.out.println("2. Get exchange rate");
        System.out.println("3. Manage favorite currency pairs");
        System.out.println("4. View conversion history");
        System.out.println("5. Settings");
        System.out.println("6. Exit");
        System.out.print("\nYour choice: ");
    }

    private void handleMainMenuChoice() {
        try {
            String input = scanner.nextLine();
            int choice = validationService.validateMenuChoice(input, 1, 6);

            switch (choice) {
                case 1:
                    convertCurrency();
                    break;
                case 2:
                    getExchangeRate();
                    break;
                case 3:
                    manageFavorites();
                    break;
                case 4:
                    viewHistory();
                    break;
                case 5:
                    settings();
                    break;
                case 6:
                    running = false;
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void convertCurrency() {
        System.out.println("\n----- Currency Conversion -----");
        System.out.print("Enter amount: ");
        String amountStr = scanner.nextLine();

        try {
            double amount = Double.parseDouble(amountStr);
            validationService.validateAmount(amount);

            System.out.print("From currency (e.g., USD): ");
            String fromCurrency = scanner.nextLine().trim().toUpperCase();

            System.out.print("To currency (e.g., EUR): ");
            String toCurrency = scanner.nextLine().trim().toUpperCase();

            System.out.println("\nFetching exchange rate...");
            String result = controller.convertCurrency(fromCurrency, toCurrency, amount);

            System.out.println("\n========================================");
            System.out.println("Conversion Result:");
            System.out.println(result);
            System.out.println("========================================");

        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid amount. Please enter a valid number.");
        } catch (InvalidAmountException | InvalidCurrencyException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ApiConnectionException e) {
            System.err.println("Connection error: " + e.getMessage());
            System.err.println("Please check your internet connection and try again.");
        }
    }

    private void getExchangeRate() {
        System.out.println("\n----- Exchange Rate Lookup -----");
        showAvailableCurrencies();

        System.out.print("From currency (e.g., USD): ");
        String fromCurrency = scanner.nextLine().trim().toUpperCase();

        System.out.print("To currency (e.g., EUR): ");
        String toCurrency = scanner.nextLine().trim().toUpperCase();

        try {
            System.out.println("\nFetching exchange rate...");
            ExchangeRateData rateData = controller.getExchangeRate(fromCurrency, toCurrency);
            displayExchangeRateData(rateData);
        } catch (InvalidCurrencyException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (ApiConnectionException e) {
            System.err.println("Connection error: " + e.getMessage());
            System.err.println("Please check your internet connection and try again.");
        }
    }

    private void showAvailableCurrencies() {
        System.out.println("\nAvailable currencies:");
        String[] currencies = controller.getAllCurrencyCodes();
        for (int i = 0; i < currencies.length; i++) {
            System.out.print(currencies[i]);
            if (i < currencies.length - 1) {
                System.out.print(", ");
            }
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println("\n");
    }

    private void displayExchangeRateData(ExchangeRateData rateData) {
        System.out.println("\n========================================");
        System.out.println(rateData.toString());
        System.out.println("========================================");
    }

    private void manageFavorites() {
        boolean inFavoritesMenu = true;

        while (inFavoritesMenu) {
            System.out.println("\n----- Favorite Currency Pairs -----");
            System.out.println("1. View favorite pairs");
            System.out.println("2. Add favorite pair");
            System.out.println("3. Remove favorite pair");
            System.out.println("4. Get rates for all favorites");
            System.out.println("5. Back to main menu");
            System.out.print("\nYour choice: ");

            try {
                String input = scanner.nextLine();
                int choice = validationService.validateMenuChoice(input, 1, 5);

                switch (choice) {
                    case 1:
                        viewFavorites();
                        break;
                    case 2:
                        addFavorite();
                        break;
                    case 3:
                        removeFavorite();
                        break;
                    case 4:
                        getRatesForAllFavorites();
                        break;
                    case 5:
                        inFavoritesMenu = false;
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void viewFavorites() {
        List<CurrencyPair> favorites = controller.getFavoritePairs();

        if (favorites.isEmpty()) {
            System.out.println("\nNo favorite currency pairs added yet.");
        } else {
            System.out.println("\nYour favorite currency pairs:");
            for (int i = 0; i < favorites.size(); i++) {
                System.out.println((i + 1) + ". " + favorites.get(i));
            }
        }
    }

    private void addFavorite() {
        System.out.print("Enter from currency (e.g., USD): ");
        String fromCurrency = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter to currency (e.g., EUR): ");
        String toCurrency = scanner.nextLine().trim().toUpperCase();

        try {
            controller.addFavoritePair(fromCurrency, toCurrency);
            System.out.println("Currency pair '" + fromCurrency + "/" + toCurrency + "' added to favorites.");
        } catch (InvalidCurrencyException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void removeFavorite() {
        List<CurrencyPair> favorites = controller.getFavoritePairs();

        if (favorites.isEmpty()) {
            System.out.println("\nNo favorite currency pairs to remove.");
            return;
        }

        viewFavorites();
        System.out.print("\nEnter the number of the pair to remove (0 to cancel): ");

        try {
            String input = scanner.nextLine();
            int choice = Integer.parseInt(input);

            if (choice == 0) {
                return;
            }

            if (choice >= 1 && choice <= favorites.size()) {
                CurrencyPair pairToRemove = favorites.get(choice - 1);
                controller.removeFavoritePair(pairToRemove);
                System.out.println("Currency pair '" + pairToRemove + "' removed from favorites.");
            } else {
                System.err.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input. Please enter a number.");
        }
    }

    private void getRatesForAllFavorites() {
        List<CurrencyPair> favorites = controller.getFavoritePairs();

        if (favorites.isEmpty()) {
            System.out.println("\nNo favorite currency pairs added yet.");
            return;
        }

        System.out.println("\nFetching exchange rates for all favorite pairs...\n");

        for (CurrencyPair pair : favorites) {
            try {
                ExchangeRateData rateData = controller.getExchangeRate(
                        pair.getFromCurrency().name(),
                        pair.getToCurrency().name()
                );
                displayExchangeRateData(rateData);
                System.out.println();
            } catch (Exception e) {
                System.err.println("Error fetching rate for " + pair + ": " + e.getMessage());
            }
        }
    }

    private void viewHistory() {
        List<ExchangeRateData> history = controller.getConversionHistory();

        if (history.isEmpty()) {
            System.out.println("\nNo conversion history available.");
        } else {
            System.out.println("\n----- Conversion History -----");
            System.out.println("(Most recent first)\n");

            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i).getCurrencyPair() +
                                 " - " + history.get(i).getTimestamp());
                System.out.println("   Rate: " + history.get(i).getExchangeRate());
                System.out.println();
            }
        }
    }

    private void settings() {
        boolean inSettingsMenu = true;

        while (inSettingsMenu) {
            System.out.println("\n----- Settings -----");
            System.out.println("Current base currency: " + controller.getPreferredBaseCurrency());
            System.out.println("\n1. Change base currency");
            System.out.println("2. Clear conversion history");
            System.out.println("3. View all available currencies");
            System.out.println("4. Back to main menu");
            System.out.print("\nYour choice: ");

            try {
                String input = scanner.nextLine();
                int choice = validationService.validateMenuChoice(input, 1, 4);

                switch (choice) {
                    case 1:
                        changeBaseCurrency();
                        break;
                    case 2:
                        clearHistory();
                        break;
                    case 3:
                        showAllCurrencies();
                        break;
                    case 4:
                        inSettingsMenu = false;
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void changeBaseCurrency() {
        showAvailableCurrencies();
        System.out.print("Enter new base currency code: ");
        String currencyCode = scanner.nextLine().trim().toUpperCase();

        try {
            controller.setPreferredBaseCurrency(currencyCode);
            System.out.println("Base currency changed to " + currencyCode);
        } catch (InvalidCurrencyException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void showAllCurrencies() {
        System.out.println("\n----- All Available Currencies -----");
        String[] currencies = controller.getAllCurrencyCodes();
        for (String currency : currencies) {
            System.out.println("- " + currency);
        }
    }

    private void clearHistory() {
        System.out.print("Are you sure you want to clear all conversion history? (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
            controller.clearHistory();
            System.out.println("Conversion history cleared.");
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    private void printGoodbyeMessage() {
        System.out.println("\n====================================");
        System.out.println("   Thank you for using Currency     ");
        System.out.println("       Converter System!            ");
        System.out.println("         Have a nice day!           ");
        System.out.println("====================================");
    }
}
