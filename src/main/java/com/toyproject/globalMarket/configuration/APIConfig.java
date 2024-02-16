package com.toyproject.globalMarket.configuration;

import com.toyproject.globalMarket.libs.BaseObject;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class APIConfig extends BaseObject {
    private static int objectId;
    public String clientId;
    public String clientSecret;
    public String url;



    public enum PlatformList {
        NAVER,
        ALIEXPRESS,
        GMARKET,
        GOOGLE,
    }
    public int kind;

    public APIConfig(String objectName, int objectId, String clientId, String clientSecret, String url) {
        super(objectName, objectId);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.url = url;
    }

    public APIConfig(String clientId, String clientSecret, String url) {
        super("PlatformConfig", objectId++);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.url = url;
    }

    public abstract String getOAuth();
}
