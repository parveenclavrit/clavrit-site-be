package com.clavrit.response;

import com.clavrit.Enums.ApiStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApisResponse {
    private boolean success;
    private int code;
    private String message;
    private Object data;

    public ApisResponse(ApiStatus status, Object data) {
        this.success = status.isSuccess();
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }

    public ApisResponse(ApiStatus status, String customMessage, Object data) {
        this.success = status.isSuccess();
        this.code = status.getCode();
        this.message = customMessage;
        this.data = data;
    }
}
