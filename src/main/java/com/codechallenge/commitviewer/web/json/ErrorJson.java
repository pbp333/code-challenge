package com.codechallenge.commitviewer.web.json;

public class ErrorJson {

    private final String message;

    private ErrorJson(String message) {
        this.message = message;
    }

    public static ErrorJson from(String message) {
        return new ErrorJson(message);
    }

    public String getMessage() {
        return message;
    }

}
