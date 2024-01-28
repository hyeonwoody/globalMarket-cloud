package com.toyproject.globalMarket.configuration;

public abstract class PlatformConfig {
    protected String clientId;
    protected String clientSecret;
    protected String url;

    public PlatformConfig(String clientId, String clientSecret, String url) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.url = url;
    }


    protected abstract void getOAuth();
}
