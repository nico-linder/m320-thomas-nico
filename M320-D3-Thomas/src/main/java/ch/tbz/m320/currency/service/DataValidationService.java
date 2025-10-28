package ch.tbz.m320.currency.service;

import ch.tbz.m320.currency.exception.InvalidAmountException;
import ch.tbz.m320.currency.exception.InvalidCurrencyException;
import ch.tbz.m320.currency.model.Currency;

public class DataValidationService {

    public void validateAmount(double amount) throws InvalidAmountException {
        if (amount < 0) {
            throw new InvalidAmountException("Amount cannot be negative");
        }

        if (amount == 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }

        if (Double.isInfinite(amount) || Double.isNaN(amount)) {
            throw new InvalidAmountException("Amount must be a valid number");
        }

        if (amount > 1_000_000_000) {
            throw new InvalidAmountException("Amount cannot exceed 1 billion");
        }
    }

    public Currency validateCurrency(String currencyCode) throws InvalidCurrencyException {
        if (currencyCode == null || currencyCode.trim().isEmpty()) {
            throw new InvalidCurrencyException("Currency code cannot be empty");
        }

        try {
            return Currency.valueOf(currencyCode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyException("Invalid currency code: " + currencyCode + ". Valid currencies are: USD, EUR, CHF, GBP, JPY, CNY, INR, AUD, CAD, BRL");
        }
    }

    public int validateMenuChoice(String input, int min, int max) throws NumberFormatException {
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid input. Please enter a number.");
        }

        if (choice < min || choice > max) {
            throw new NumberFormatException("Please enter a number between " + min + " and " + max);
        }

        return choice;
    }
}
