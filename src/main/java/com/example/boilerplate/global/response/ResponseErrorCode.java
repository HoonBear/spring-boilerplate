package com.example.boilerplate.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ResponseErrorCode {
    BAD_REQUEST(400, "BAD-REQUEST-401", "BAD REQUEST"),
    TOKEN_INVALID(401, "TOKEN-INVALID-401", "UNAUTHORIZED"),
    TOKEN_EXPIRED(401, "TOKEN-EXPIRED-401", "EXPIRED TOKEN"),
    FORBIDDEN(403, "FORBIDDEN-ERROR-401", "FORBIDDEN PAGE"),
    NOT_FOUND(404, "COMMON-ERR-404", "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "COMMON-ERR-500", "INTER SERVER ERROR"),
    SERVICE_UNAVAILABLE(503, "COMMON-ERR-503", "SERVICE UNAVAILABLE ERROR"),

    ;

    private int status;
    private String code;
    private String message;
}