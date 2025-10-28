package ch.tbz.m320.currency.exception;

/**
 * Exception thrown when an invalid amount is provided for currency conversion.
 * This occurs when the amount is negative, zero, not a number, or exceeds limits.
 */
public class InvalidAmountException extends Exception {
    /**
     * Creates a new InvalidAmountException with a message.
     *
     * @param message the error message explaining why the amount is invalid
     */
    public InvalidAmountException(String message) {
        super(message);
    }

    /**
     * Creates a new InvalidAmountException with a message and cause.
     *
     * @param message the error message
     * @param cause the underlying cause of this exception
     */
    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
