package ch.tbz.m320.currency.model;

public enum Currency {
    USD("US Dollar", "$"),
    EUR("Euro", "€"),
    CHF("Swiss Franc", "CHF"),
    GBP("British Pound", "£"),
    JPY("Japanese Yen", "¥"),
    CNY("Chinese Yuan", "¥"),
    INR("Indian Rupee", "₹"),
    AUD("Australian Dollar", "A$"),
    CAD("Canadian Dollar", "C$"),
    BRL("Brazilian Real", "R$");

    private final String fullName;
    private final String symbol;

    Currency(String fullName, String symbol) {
        this.fullName = fullName;
        this.symbol = symbol;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return this.name() + " (" + fullName + ")";
    }
}
