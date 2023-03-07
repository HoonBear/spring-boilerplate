package com.example.boilerplate.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.example.boilerplate.global.exception.CustomException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Response<T> {

    private String code;
    private String message;
    private T data;

    @Builder
    public Response(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Response success() {
        return Response.builder()
                       .code("success")
                       .message("")
                       .data(null)
                       .build();
    }

    public static Response success(String message) {
        return Response.builder()
                       .code("OK")
                       .message(message)
                       .data(null)
                       .build();
    }

    public static <T> Response success(T data) {
        return Response.builder()
                       .code("OK")
                       .message("success")
                       .data(data)
                       .build();
    }

    public static <T> Response success(T data, String message) {
        return Response.builder()
                       .code("OK")
                       .message(message)
                       .data(data)
                       .build();
    }

    public static <T extends CustomException> Response error(T exception) {
        return Response.builder()
                     .code(exception.getCode())
                     .message(exception.getMessage())
                     .data(null)
                     .build();
    }
}
