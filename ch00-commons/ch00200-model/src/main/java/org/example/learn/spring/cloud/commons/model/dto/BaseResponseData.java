package org.example.learn.spring.cloud.commons.model.dto;

public class BaseResponseData<T> {

    private String errorCode;

    private String message;

    private T data;

    public BaseResponseData() {
    }

    public BaseResponseData(String errorCode, String message, T data) {
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
