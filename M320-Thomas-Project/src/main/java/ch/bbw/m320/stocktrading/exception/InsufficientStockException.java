package ch.bbw.m320.stocktrading.exception;

/**
 * Exception thrown when a user tries to sell more stocks than they own.
 * Custom exception for better error handling.
 *
 * @author Thomas
 * @version 1.0
 */
public class InsufficientStockException extends Exception {

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
