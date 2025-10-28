package ch.tbz.m320.currency.exception;

public class ApiConnectionException extends Exception {
    public ApiConnectionException(String message) {
        super(message);
    }

    public ApiConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
