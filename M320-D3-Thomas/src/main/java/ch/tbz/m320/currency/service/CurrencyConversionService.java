package ch.tbz.m320.currency.service;

import ch.tbz.m320.currency.model.Currency;
import ch.tbz.m320.currency.model.ExchangeRate;

public class CurrencyConversionService {

    public double convertAmount(double amount, ExchangeRate rate) {
        return rate.convert(amount);
    }

    public String formatAmount(double amount, Currency currency, boolean includeSymbol) {
        if (includeSymbol) {
            return String.format("%s %.2f", currency.getSymbol(), amount);
        } else {
            return String.format("%.2f %s", amount, currency.name());
        }
    }

    public String formatConversion(double fromAmount, Currency fromCurrency, double toAmount, Currency toCurrency) {
        return String.format("%.2f %s = %.2f %s",
                fromAmount, fromCurrency.name(),
                toAmount, toCurrency.name());
    }
}
