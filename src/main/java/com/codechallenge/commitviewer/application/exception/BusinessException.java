package com.codechallenge.commitviewer.application.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -4213352348123720945L;

    @SuppressWarnings("unused")
    private BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

}
