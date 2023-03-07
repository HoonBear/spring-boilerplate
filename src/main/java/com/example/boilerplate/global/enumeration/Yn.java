package com.example.boilerplate.global.enumeration;

import org.springframework.util.StringUtils;

public enum Yn {
    Y, N;

    public static Yn toEnum(String string) {
        if (!StringUtils.hasText(string))
            return null;

        switch (string) {
            case "Y": return Y;
            case "N": return N;
        }
        return N;
    }

    public static Yn toEnum(boolean yn) {
        if (yn) return Y;
        return N;
    }

    public static boolean toBoolean(Yn yn) {
        return yn.equals(Y);
    }
}
