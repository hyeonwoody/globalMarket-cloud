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


        registry.addMapping("/test/ia")
                .allowedMethods("POST")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);

        registry.addMapping("/product-register/information")
                .allowedMethods("GET")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);

        registry.addMapping("/product-register/information/additional")
                .allowedMethods("GET")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);

        registry.addMapping("/product-register/confirm")
                .allowedMethods("POST")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);

        registry.addMapping("/product/option/naver/standard-options")
                .allowedMethods("GET")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);

        registry.addMapping("/product/category/naver")
                .allowedMethods("GET")
                .allowCredentials(true)
                .allowedOrigins("http://" + this.ipAddress + ":" + this.frontEndPort);

    }
}
