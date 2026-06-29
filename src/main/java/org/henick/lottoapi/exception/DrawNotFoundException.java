package org.henick.lottoapi.exception;

public class DrawNotFoundException extends RuntimeException {
    public DrawNotFoundException(String message) {
        super(message);
    }
}
