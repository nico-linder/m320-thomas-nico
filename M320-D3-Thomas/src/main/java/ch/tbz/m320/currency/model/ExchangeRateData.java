package ch.tbz.m320.currency.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Complete exchange rate information including buy/sell rates and timestamp.
 * This immutable data object represents a snapshot of exchange rate data
 * at a specific point in time, including market spread information.
 */
public class ExchangeRateData {
    private final CurrencyPair currencyPair;
    private final ExchangeRate exchangeRate;
    private final double buyRate;
    private final double sellRate;
    private final LocalDateTime timestamp;

    /**
     * Creates a new exchange rate data object.
     *
     * @param currencyPair the currency pair for this rate
     * @param exchangeRate the base exchange rate
     * @param buyRate the rate for buying the target currency
     * @param sellRate the rate for selling the target currency
     * @param timestamp when this rate was retrieved
     */
    public ExchangeRateData(CurrencyPair currencyPair, ExchangeRate exchangeRate,
                           double buyRate, double sellRate, LocalDateTime timestamp) {
        this.currencyPair = currencyPair;
        this.exchangeRate = exchangeRate;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.timestamp = timestamp;
    }

    /**
     * Gets the currency pair.
     *
     * @return the currency pair (from/to currencies)
     */
    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    /**
     * Gets the exchange rate.
     *
     * @return the base exchange rate
     */
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Gets the buy rate.
     *
     * @return the rate for buying the target currency
     */
    public double getBuyRate() {
        return buyRate;
    }

    /**
     * Gets the sell rate.
     *
     * @return the rate for selling the target currency
     */
    public double getSellRate() {
        return sellRate;
    }

    /**
     * Gets the timestamp.
     *
     * @return when this rate was retrieved
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Converts an amount using the exchange rate.
     *
     * @param amount the amount to convert
     * @return the converted amount
     */
    public double convertAmount(double amount) {
        return exchangeRate.convert(amount);
    }

    /**
     * Returns a formatted string representation of the exchange rate data.
     *
     * @return multi-line string with all rate information
     */
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
