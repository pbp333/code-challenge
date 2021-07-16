package com.codechallenge.commitviewer.application.exception;

public class BussinessException extends RuntimeException {

    private static final long serialVersionUID = -4213352348123720945L;

    @SuppressWarnings("unused")
    private BussinessException() {
    }

    public BussinessException(String message, Throwable trowable) {
        super(message, trowable);
    }

    public BussinessException(String message) {
        super(message);
    }

}
