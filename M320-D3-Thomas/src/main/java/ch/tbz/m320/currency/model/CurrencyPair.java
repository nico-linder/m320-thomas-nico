package ch.tbz.m320.currency.model;

import java.util.Objects;

/**
 * Represents a currency conversion pair (from currency to target currency).
 * This is an immutable value object used to identify exchange rate lookups.
 * Implements equals and hashCode for use in collections.
 */
public class CurrencyPair {
    private final Currency fromCurrency;
    private final Currency toCurrency;

    /**
     * Creates a new currency pair.
     *
     * @param fromCurrency the source currency
     * @param toCurrency the target currency
     */
    public CurrencyPair(Currency fromCurrency, Currency toCurrency) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
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
     * Returns a string representation of the currency pair.
     *
     * @return formatted as "USD/EUR"
     */
    @Override
    public String toString() {
        return fromCurrency.name() + "/" + toCurrency.name();
    }

    /**
     * Checks equality based on both currencies.
     *
     * @param o the object to compare with
     * @return true if both currencies match
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyPair that = (CurrencyPair) o;
        return fromCurrency == that.fromCurrency && toCurrency == that.toCurrency;
    }

    /**
     * Generates hash code based on both currencies.
     *
     * @return hash code for this currency pair
     */
    @Override
    public int hashCode() {
        return Objects.hash(fromCurrency, toCurrency);
    }
}
