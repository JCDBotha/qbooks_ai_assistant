package qbooks_ai_assistant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QuickBooksConfig {

    @Value("${quickbooks.client-id}")
    private String clientId;

    @Value("${quickbooks.client-secret}")
    private String clientSecret;

    @Value("${quickbooks.redirect-uri}")
    private String redirectUri;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
