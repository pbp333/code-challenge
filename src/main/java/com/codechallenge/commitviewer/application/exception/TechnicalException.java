package com.codechallenge.commitviewer.application.exception;

public class TechnicalException extends RuntimeException {

    private static final long serialVersionUID = 4697456601922971697L;

    @SuppressWarnings("unused")
    private TechnicalException() {
    }

    public TechnicalException(String message, Throwable trowable) {
        super(message, trowable);
    }

    public TechnicalException(String message) {
        super(message);
    }

}
