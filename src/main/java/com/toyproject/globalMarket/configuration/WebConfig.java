package com.toyproject.globalMarket.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Value("${my.ipAddress}")
    String ipAddress;

    @Value("${my.frontEndPort}")
    String frontEndPort;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/test/ip")
                .allowedMethods("POST")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);

        registry.addMapping("/test/ia")
                .allowedMethods("POST")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);

        registry.addMapping("/config/main")
                .allowedMethods("PUT")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);

        registry.addMapping("/textarea/created")
                .allowedMethods("PUT", "GET")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);
    }
}
