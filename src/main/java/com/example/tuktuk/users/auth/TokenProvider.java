package com.example.tuktuk.users.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
@Component
public class TokenProvider {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String tokenEndpoint;

    public TokenProvider(@Value("${COGNITO_CLIENT_ID}") String clientId,
                         @Value("${COGNITO_CLIENT_SECRET}") String clientSecret,
                         @Value("${COGNITO_REDIRECT_URI}") String redirectUri,
                         @Value("${COGNITO_TOKEN_ENDPOINT}") String tokenEndpoint) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.tokenEndpoint = tokenEndpoint;
    }

    public TokenInfo getToken(String code) {

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        return WebClient.create()
                .post()
                .uri(tokenEndpoint)
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(TokenInfo.class)
                .block();
    }
}
