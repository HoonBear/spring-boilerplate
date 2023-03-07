package com.example.boilerplate.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**************************************************************** auth ****************************************************************/
    PRESENT_MEMBER(400,"9101","이미 가입되어 있는 이메일입니다."),
    CAN_NOT_AUTH_MEMBER(401,"9102","회원정보가 일치하지 않습니다."),
    /**************************************************************** member ****************************************************************/
    NOT_EXIST_MEMBER(404,"9201","회원정보가 존재하지 않습니다."),
    ;

    private final int statusCode;
    private final String code;
    private final String message;
}
