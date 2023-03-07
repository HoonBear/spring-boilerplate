package com.example.boilerplate.domain.member.enumeration;

import lombok.Getter;

@Getter
public enum Status {
    NORMAL("정상"), LIMITS("이용제한"), STOP("이용정지"), SLEEP("휴면회원");

    private String name;

    Status(final String name) {
        this.name = name;
    }
}
