package ch.tbz.m320.currency.model;

/**
 * Enum representing supported currencies in the application.
 * Each currency has a full name and a symbol for display purposes.
 * This enum provides type-safe currency representation.
 */
public enum Currency {
    /** US Dollar */
    USD("US Dollar", "$"),
    /** Euro */
    EUR("Euro", "€"),
    /** Swiss Franc */
    CHF("Swiss Franc", "CHF"),
    /** British Pound */
    GBP("British Pound", "£"),
    /** Japanese Yen */
    JPY("Japanese Yen", "¥"),
    /** Chinese Yuan */
    CNY("Chinese Yuan", "¥"),
    /** Indian Rupee */
    INR("Indian Rupee", "₹"),
    /** Australian Dollar */
    AUD("Australian Dollar", "A$"),
    /** Canadian Dollar */
    CAD("Canadian Dollar", "C$"),
    /** Brazilian Real */
    BRL("Brazilian Real", "R$");

    private final String fullName;
    private final String symbol;

    /**
     * Constructor for Currency enum.
     *
     * @param fullName the full name of the currency
     * @param symbol the symbol used to represent the currency
     */
    Currency(String fullName, String symbol) {
        this.fullName = fullName;
        this.symbol = symbol;
    }

    /**
     * Gets the full name of the currency.
     *
     * @return the full name (e.g., "US Dollar")
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Gets the symbol of the currency.
     *
     * @return the currency symbol (e.g., "$")
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns a string representation of the currency.
     *
     * @return currency code and full name (e.g., "USD (US Dollar)")
     */
    @Override
    public String toString() {
        return this.name() + " (" + fullName + ")";
    }
}
