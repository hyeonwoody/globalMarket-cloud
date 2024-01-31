package com.toyproject.globalMarket.configuration;

import com.toyproject.globalMarket.libs.BaseObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class AuthConfig extends BaseObject {
    private static int objectId;
    public String clientId;
    public String clientSecret;
    public String url;

    public enum PlatformList {
        NAVER,
        ALIEXPRESS,
        GMARKET,
    }
    public int kind;

    public AuthConfig(String clientId, String clientSecret, String url) {
        super("PlatformConfig", objectId++);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.url = url;
    }


    public abstract String getOAuth();
}
