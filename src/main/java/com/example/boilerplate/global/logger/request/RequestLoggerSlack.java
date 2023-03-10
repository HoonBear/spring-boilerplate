package com.example.boilerplate.global.logger.request;


import com.example.boilerplate.global.config.ConfigProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestLoggerSlack {
    private final ConfigProvider configProvider;

    public void sendMessage(String request, String response, boolean success) {
        String url = configProvider.getSlackChannel();

        if (url.equals("")) return;

        SlackApi api = new SlackApi(configProvider.getSlackChannel());
        String message = "";

        if (success) {
            message = "πΏοΈ ------------------------------  BoilerPlate μλ²μ μ±κ³΅λ‘κ·Έμλλ€  ----------------------------- π\n"
                + "\n"
                + "LoggingDatetime : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T"," ")
                + "\n"
                + "\n"
                + "request : " + request
                + "\n"
                + "\n"
                + "response : " + response;
        } else {
            message = "β ------------------------------  BoilerPlate μλ²μ μλ¬λ‘κ·Έμλλ€  ----------------------------- βοΈ\n"
                + "\n"
                + "LoggingDatetime : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T"," ")
                + "\n"
                + "\n"
                + "request : " + request
                + "\n"
                + "\n"
                + "response : " + response;
        }
        api.call(new SlackMessage(message));
    }

}
