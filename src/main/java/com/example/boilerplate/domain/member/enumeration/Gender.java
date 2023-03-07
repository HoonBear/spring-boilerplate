package com.example.boilerplate.domain.member.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    F("female"), M("male");

    private String type;
}
