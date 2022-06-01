package com.instagram.config.properties;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.security")
@Setter
public class SecurityProperties {
    private ClientSettings clientSettings;
    private TokenSettings tokenSettings;

    @Setter
    static class ClientSettings {
        private String clientId;
        private String clientSecret;
    }

    @Setter
    static class TokenSettings {
        private Integer expirationTimeInMinutes;
    }

    public String getClientId() {
        return clientSettings.clientId;
    }

    public String getClientSecret() {
        return clientSettings.clientSecret;
    }

    public Integer getExpirationTimeInMinutes() {
        return tokenSettings.expirationTimeInMinutes;
    }
}