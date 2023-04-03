package it.unibo.ds.presentation;

public class BadContentException extends Exception {
    public BadContentException() {
    }

    public BadContentException(String message) {
        super(message);
    }

    public BadContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadContentException(Throwable cause) {
        super(cause);
    }
}
