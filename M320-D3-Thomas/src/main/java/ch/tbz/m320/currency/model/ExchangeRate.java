package ch.tbz.m320.currency.model;

public class ExchangeRate {
    private final Currency fromCurrency;
    private final Currency toCurrency;
    private final double rate;

    public ExchangeRate(Currency fromCurrency, Currency toCurrency, double rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public double getRate() {
        return rate;
    }

    public double convert(double amount) {
        return amount * rate;
    }

    @Override
    public String toString() {
        return String.format("1 %s = %.4f %s", fromCurrency.name(), rate, toCurrency.name());
    }
}
