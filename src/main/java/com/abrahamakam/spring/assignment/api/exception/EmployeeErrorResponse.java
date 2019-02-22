package com.abrahamakam.spring.assignment.api.exception;

public class EmployeeErrorResponse {

    private int status;

    private String message;

    public EmployeeErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
