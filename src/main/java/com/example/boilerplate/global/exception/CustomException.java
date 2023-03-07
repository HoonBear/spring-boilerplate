package com.example.boilerplate.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int status;
    private final String code;
    private final String message;

    public CustomException(ErrorCode errorCode) {
        this.status = errorCode.getStatusCode();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
