package com.toyproject.globalMarket.configuration;

import com.toyproject.globalMarket.libs.BaseObject;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class APIConfig extends BaseObject {
    protected Client client;

    public class Client {
        public String id;
        public String secret;
    }




    public enum PlatformList {
        NAVER,
        ALIEXPRESS,
        GMARKET,
        GOOGLE,
    }
    public int kind;

    public APIConfig(String objectName, int objectId) {
        super(objectName, objectId);
    }


    public abstract String getOAuth();
}
