package org.henick.lottoapi.exception;

public class UnknownGameException extends RuntimeException {
    public UnknownGameException(String message) {
        super(message);
    }
}
