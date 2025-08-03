package com.sopnolikhi.booksmyfriend.Services.Models.Common.ApiResponse;

public abstract class ApiResponseModel<T> {
    private final T data;
    private final String message;
    private final int errorCode;
    private final int successCode;

    private ApiResponseModel(T data, String message, int successCode, int errorCode) {
        this.data = data;
        this.message = message;
        this.successCode = successCode;
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getSuccessCode() {
        return successCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
    public static final class SuccessCode<T> extends ApiResponseModel<T> {
        public SuccessCode(int successCode, String message, T data) {
            super(data, message, successCode, 0);
        }
    }

    public static final class ErrorCode<T> extends ApiResponseModel<T> {
        public ErrorCode(int errorCode, String message, T data) {
            super(data, message, 0, errorCode);
        }
    }

    public static final class Loading<T> extends ApiResponseModel<T> {
        public Loading() {
            super(null, null, 0, 0);
        }
    }
}