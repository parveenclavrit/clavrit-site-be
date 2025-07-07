package com.clavrit.response;

import com.clavrit.Enums.ApiStatus;


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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
    
}
