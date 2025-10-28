package ch.tbz.m320.currency.model;

/**
 * Represents an exchange rate between two currencies.
 * This immutable value object stores the conversion rate and provides
 * a method to convert amounts from one currency to another.
 */
public class ExchangeRate {
    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final double rate;

    /**
     * Creates a new exchange rate.
     *
     * @param fromCurrency the source currency
     * @param toCurrency the target currency
     * @param rate the exchange rate (1 fromCurrency = rate * toCurrency)
     */
    public ExchangeRate(Currency fromCurrency, Currency toCurrency, double rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }

    /**
     * Gets the source currency.
     *
     * @return the currency to convert from
     */
    public Currency getFromCurrency() {
        return fromCurrency;
    }

    /**
     * Gets the target currency.
     *
     * @return the currency to convert to
     */
    public Currency getToCurrency() {
        return toCurrency;
    }

    /**
     * Gets the exchange rate.
     *
     * @return the conversion rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Converts an amount using this exchange rate.
     *
     * @param amount the amount in the source currency
     * @return the converted amount in the target currency
     */
    public double convert(double amount) {
        return amount * rate;
    }

    /**
     * Returns a string representation of the exchange rate.
     *
     * @return formatted as "1 USD = 0.9215 EUR"
     */
    @Override
    public String toString() {
        return String.format("1 %s = %.4f %s", fromCurrency.name(), rate, toCurrency.name());
    }
}
