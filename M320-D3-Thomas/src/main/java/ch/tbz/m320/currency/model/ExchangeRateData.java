package ch.tbz.m320.currency.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExchangeRateData {
    private final CurrencyPair currencyPair;
    private final ExchangeRate exchangeRate;
    private final double buyRate;
    private final double sellRate;
    private final LocalDateTime timestamp;

    public ExchangeRateData(CurrencyPair currencyPair, ExchangeRate exchangeRate,
                           double buyRate, double sellRate, LocalDateTime timestamp) {
        this.currencyPair = currencyPair;
        this.exchangeRate = exchangeRate;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.timestamp = timestamp;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double convertAmount(double amount) {
        return exchangeRate.convert(amount);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format(
            "Exchange Rate %s:\n" +
            "  Current Rate: %s\n" +
            "  Buy Rate: %.4f %s\n" +
            "  Sell Rate: %.4f %s\n" +
            "  Last Updated: %s",
            currencyPair, exchangeRate,
            buyRate, currencyPair.getToCurrency().name(),
            sellRate, currencyPair.getToCurrency().name(),
            timestamp.format(formatter)
        );
    }
}
