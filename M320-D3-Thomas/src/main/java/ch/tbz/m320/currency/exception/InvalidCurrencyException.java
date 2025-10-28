package ch.tbz.m320.currency.exception;

/**
 * Exception thrown when an invalid currency code is provided.
 * This occurs when a currency code doesn't match any supported currency
 * or when the currency code format is invalid.
 */
public class InvalidCurrencyException extends Exception {
    /**
     * Creates a new InvalidCurrencyException with a message.
     *
     * @param message the error message explaining why the currency is invalid
     */
    public InvalidCurrencyException(String message) {
        super(message);
    }

    /**
     * Creates a new InvalidCurrencyException with a message and cause.
     *
     * @param message the error message
     * @param cause the underlying cause of this exception
     */
    public InvalidCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
