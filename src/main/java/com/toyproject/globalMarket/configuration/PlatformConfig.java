package com.toyproject.globalMarket.configuration;

public abstract class PlatformConfig {
    public String clientId;
    public String clientSecret;
    public String url;


    public PlatformConfig(String clientId, String clientSecret, String url) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.url = url;
    }


    public abstract String getOAuth();
}
