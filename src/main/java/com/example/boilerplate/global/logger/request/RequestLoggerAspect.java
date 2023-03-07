package com.example.boilerplate.global.logger.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequestLoggerAspect {
    private final HttpServletRequest request;
    private final RequestLoggerSlack requestLoggerSlack;

    @Pointcut("bean(*Controller)") // 이런 패턴이 실행될 경우 수행
    public void loggerPointCut() {
    }

    @Around("loggerPointCut()")
    public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Object result = proceedingJoinPoint.proceed();
        String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();

        Map<String, Object> params = new HashMap<>();

        try {
            params.put("request-url", request.getRequestURI());
            params.put("http-method", request.getMethod());
            params.put("controller", controllerName);
            params.put("authorization", request.getHeader("Authorization"));
            params.put("params", getParams());
            params.put("query-string", request.getQueryString());
            params.put("time", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        } catch (Exception e) {
            log.error("LoggerAspect error", e);
        }

        String httpRequest = objectWriter.writeValueAsString(params);
        String httpResponse = objectWriter.writeValueAsString(result);

        log.info("[success] requestLog : {}", httpRequest);
        log.info("[success] responseLog : {}", httpResponse);

        requestLoggerSlack.sendMessage(httpRequest, httpResponse, true);
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
