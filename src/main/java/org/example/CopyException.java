package org.example;

public class CopyException extends RuntimeException {
    public CopyException() {
        super();
    }

    public CopyException(String message) {
        super(message);
    }

    public CopyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CopyException(Throwable cause) {
        super(cause);
    }
}
