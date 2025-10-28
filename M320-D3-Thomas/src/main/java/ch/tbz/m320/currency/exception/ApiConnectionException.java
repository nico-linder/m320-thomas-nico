package ch.tbz.m320.currency.exception;

/**
 * Exception thrown when there's a problem connecting to the currency exchange API.
 * This can occur due to network issues, API unavailability, or interrupted connections.
 */
public class ApiConnectionException extends Exception {
    /**
     * Creates a new ApiConnectionException with a message.
     *
     * @param message the error message describing the connection problem
     */
    public ApiConnectionException(String message) {
        super(message);
    }

    /**
     * Creates a new ApiConnectionException with a message and cause.
     *
     * @param message the error message
     * @param cause the underlying cause of the connection failure
     */
    public ApiConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
