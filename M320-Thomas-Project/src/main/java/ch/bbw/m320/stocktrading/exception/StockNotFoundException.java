package ch.bbw.m320.stocktrading.exception;

/**
 * Exception thrown when a requested stock is not found in the market.
 * Custom exception for better error handling.
 *
 * @author Thomas
 * @version 1.0
 */
public class StockNotFoundException extends Exception {

    public StockNotFoundException(String message) {
        super(message);
    }
}
