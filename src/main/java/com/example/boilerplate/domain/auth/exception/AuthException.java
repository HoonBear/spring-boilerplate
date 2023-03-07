package com.example.boilerplate.domain.auth.exception;

import com.example.boilerplate.global.exception.CustomException;
import com.example.boilerplate.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends CustomException{

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
