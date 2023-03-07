package com.example.boilerplate.domain.member.exception;

import com.example.boilerplate.global.exception.CustomException;
import com.example.boilerplate.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends CustomException{

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
