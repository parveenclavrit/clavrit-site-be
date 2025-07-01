package com.clavrit.Enums;

public enum ApiStatus {
	
    OK(true, 200, "Success"),
    CREATED(true, 201, "Resource created successfully"),
    BAD_REQUEST(false, 400, "Bad request"),
    NOT_FOUND(false, 404, "Resource not found"),
    INTERNAL_ERROR(false, 500, "Internal server error");

    private final boolean success;
    private final int code;
    private final String message;

    ApiStatus(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public int getCode() { return code; }
    public String getMessage() { return message; }
}

