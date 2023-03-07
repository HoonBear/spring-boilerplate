package com.example.boilerplate.domain.member.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.example.boilerplate.global.logger.request.RequestLoggerSlack;
import com.example.boilerplate.global.response.Response;
import io.sentry.Sentry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.example.boilerplate.domain.member")
public class MemberAdvice {
    private final HttpServletRequest request;
    private final RequestLoggerSlack requestLoggerSlack;

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<Response> memberExceptionHandler(MemberException exception) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Map<String, Object> params = new HashMap<>();

        ResponseEntity<Response> result = ResponseEntity.status(exception.getStatus())
                                                        .body(Response.error(exception));

        params.put("request-url", request.getRequestURI());
        params.put("http-method", request.getMethod());
        params.put("controller", "MemberController");
        params.put("authorization", request.getHeader("Authorization"));
        params.put("params", getParams());
        params.put("query-string", request.getQueryString());
        params.put("time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        String httpRequest = objectWriter.writeValueAsString(params);
        String httpResponse = objectWriter.writeValueAsString(result);

        log.error("[MemberException] requestLog : {}", objectWriter.writeValueAsString(params));
        log.error("[MemberException] responseLog : {}", objectWriter.writeValueAsString(result));

        requestLoggerSlack.sendMessage(httpRequest, httpResponse, false);
        Sentry.captureException(exception);
        return result;
    }

    private JSONObject getParams() {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String replaceParam = param.replace("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }
}
