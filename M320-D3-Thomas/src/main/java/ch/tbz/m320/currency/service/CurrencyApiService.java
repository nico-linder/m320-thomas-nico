package ch.tbz.m320.currency.service;

import ch.tbz.m320.currency.exception.ApiConnectionException;
import ch.tbz.m320.currency.model.Currency;
import ch.tbz.m320.currency.model.CurrencyPair;
import ch.tbz.m320.currency.model.ExchangeRate;
import ch.tbz.m320.currency.model.ExchangeRateData;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CurrencyApiService {
    private final Map<String, Double> baseRates; // All rates relative to USD
    private final Random random;

    public CurrencyApiService() {
        this.baseRates = new HashMap<>();
        this.random = new Random();
        initializeBaseRates();
    }

    private void initializeBaseRates() {
        // Base rates: 1 USD = X currency
        baseRates.put("USD", 1.0);
        baseRates.put("EUR", 0.92);
        baseRates.put("CHF", 0.88);
        baseRates.put("GBP", 0.79);
        baseRates.put("JPY", 149.50);
        baseRates.put("CNY", 7.24);
        baseRates.put("INR", 83.12);
        baseRates.put("AUD", 1.53);
        baseRates.put("CAD", 1.36);
        baseRates.put("BRL", 4.97);
    }

    public ExchangeRateData fetchExchangeRate(Currency fromCurrency, Currency toCurrency)
            throws ApiConnectionException {
        simulateNetworkDelay();

        CurrencyPair pair = new CurrencyPair(fromCurrency, toCurrency);

        // Calculate exchange rate with small random variation
        double baseRate = calculateExchangeRate(fromCurrency, toCurrency);
        double variation = 1.0 + (random.nextDouble() - 0.5) * 0.02; // ±1% variation
        double currentRate = baseRate * variation;

        ExchangeRate exchangeRate = new ExchangeRate(fromCurrency, toCurrency, currentRate);

        // Buy and sell rates (slight spread)
        double spread = 0.015; // 1.5% spread
        double buyRate = currentRate * (1 - spread);
        double sellRate = currentRate * (1 + spread);

        return new ExchangeRateData(pair, exchangeRate, buyRate, sellRate, LocalDateTime.now());
    }

    private double calculateExchangeRate(Currency from, Currency to) {
        // Convert from -> USD -> to
        double fromToUsd = 1.0 / baseRates.get(from.name());
        double usdToTarget = baseRates.get(to.name());
        return fromToUsd * usdToTarget;
    }

    private void simulateNetworkDelay() throws ApiConnectionException {
        try {
            Thread.sleep(200 + random.nextInt(300)); // 200-500ms delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiConnectionException("Connection interrupted", e);
        }

        // 5% chance of connection failure
        if (random.nextDouble() < 0.05) {
            throw new ApiConnectionException("Failed to connect to currency exchange API. Please try again later.");
        }
    }

    public boolean isServiceAvailable() {
        return true;
    }
}
