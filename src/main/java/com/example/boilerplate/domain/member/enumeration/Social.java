package com.example.boilerplate.domain.member.enumeration;

import lombok.Getter;

@Getter
public enum Social {
    NONE("일반", "none"),
    NAVER("네이버", "naver"),
    KAKAO("카카오", "kakao"),
    APPLE("애플", "apple"),
    FACEBOOK("페이스북", "facebook"),
    ;
    private String name;
    private String code;

    Social(final String name, final String code) {
        this.name = name;
        this.code = code;
    }
}
