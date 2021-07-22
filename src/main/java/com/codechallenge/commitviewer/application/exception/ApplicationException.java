package com.codechallenge.commitviewer.application.exception;

public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -4213352348123720945L;

    @SuppressWarnings("unused")
    private ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }

}
