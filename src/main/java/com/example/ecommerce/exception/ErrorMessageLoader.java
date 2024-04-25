package com.example.ecommerce.exception;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
public class ErrorMessageLoader {
    private static final Logger log = LoggerFactory.getLogger(ErrorMessageLoader.class);
    private static Map<String, ErrorMessage> errorMessageMap;

    public ErrorMessageLoader() {
    }

    @PostConstruct
    public static void loadConfig() {
        log.info("-----load error config message------");
        Charset charset = StandardCharsets.UTF_8;
        try(var enResourceStream = new InputStreamReader(Objects.requireNonNull(ErrorMessageLoader.class.getResourceAsStream("/message_en.properties")), charset);
            var vnResourceStream = new InputStreamReader(Objects.requireNonNull(ErrorMessageLoader.class.getResourceAsStream("/message_vn.properties")), charset)) {
            Properties englishMessages = new Properties();
            englishMessages.load(enResourceStream);
            errorMessageMap = englishMessages.entrySet().stream().collect(
                    Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> {
                                ErrorMessage errorMessage = new ErrorMessage();
                                errorMessage.setEn(e.getValue().toString());
                                return errorMessage;
                            }
                    )
            );
            Properties vietnameseMessages = new Properties();
            vietnameseMessages.load(vnResourceStream);
            errorMessageMap.forEach((key, value) -> value.setVn(vietnameseMessages.getProperty(key)));
        } catch (IOException e) {
            log.error("{} load config error {}", ErrorMessageLoader.class.getSimpleName(), e);
        }
    }

    public static ErrorMessage getMessage(String errorCode) {
        return errorMessageMap.get(errorCode) != null ? errorMessageMap.get(errorCode) : new ErrorMessage("Server is temporarily unavailable, please come back in a few minutes", "Server tạm thời không khả dụng, vui long quay lại sau ít phút");
    }
}
