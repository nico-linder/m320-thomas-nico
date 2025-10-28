package ch.tbz.m320.currency.model;

import java.util.Objects;

public class CurrencyPair {
    private final Currency fromCurrency;
    private final Currency toCurrency;

    public CurrencyPair(Currency fromCurrency, Currency toCurrency) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    @Override
    public String toString() {
        return fromCurrency.name() + "/" + toCurrency.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyPair that = (CurrencyPair) o;
        return fromCurrency == that.fromCurrency && toCurrency == that.toCurrency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromCurrency, toCurrency);
    }
}
