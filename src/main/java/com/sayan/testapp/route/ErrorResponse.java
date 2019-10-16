package com.sayan.testapp.route;

public class ErrorResponse {
    private String message;

    public ErrorResponse(Exception e) {
        this.message = e.getMessage();
    }

    public String getMessage() {
        return this.message;
    }
}
