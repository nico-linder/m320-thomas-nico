package ch.bbw.m320.stocktrading.exception;

/**
 * Exception thrown when a user tries to buy stocks without sufficient balance.
 * Custom exception for better error handling.
 *
 * @author Thomas
 * @version 1.0
 */
public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
