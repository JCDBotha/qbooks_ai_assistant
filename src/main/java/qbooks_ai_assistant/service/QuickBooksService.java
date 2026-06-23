package qbooks_ai_assistant.service;

import qbooks_ai_assistant.config.QuickBooksConfig;
import qbooks_ai_assistant.dto.QuickBooksTokenResponse;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Service
public class QuickBooksService {

    private final QuickBooksConfig config;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://oauth.platform.intuit.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build();

    public QuickBooksService(QuickBooksConfig config) {
        this.config = config;
    }

    public QuickBooksTokenResponse exchangeCodeForToken(String code) {

        System.out.println("Exchanging code for token...");

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("code", code);
        form.add("redirect_uri", config.getRedirectUri());

        String auth = Base64.getEncoder().encodeToString(
                (config.getClientId() + ":" + config.getClientSecret()).getBytes());

        return webClient.post()
                .uri("/oauth2/v1/tokens/bearer")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + auth)
                .bodyValue(form)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("QuickBooks error: " + body)))
                .bodyToMono(QuickBooksTokenResponse.class)
                .block();
    }
}