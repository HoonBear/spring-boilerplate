package com.example.boilerplate;

import com.example.boilerplate.domain.member.enumeration.Status;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumStringCreator {
    private static String wrappedEnum(String str) {
        return String.format("[%s]", str) + " 중 택1";
    }

    public static String memberStatus() {
        String enumToString = Arrays.stream(Status.values())
                                    .map(value -> String.format("%s", value) + ": " + value.getName())
                                    .collect(Collectors.joining(", "));
        return wrappedEnum(enumToString);
    }
}
