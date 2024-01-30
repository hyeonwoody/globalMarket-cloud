package com.toyproject.globalMarket.service.product.store;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;

public interface StoreInterface {
    abstract JsonObject parseHtml(String html);
    default String getHtml(String url){
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    abstract int convert (ProductRegisterVO productRegisterVO, JsonObject jsonObject) throws IOException;
    abstract int getProductInfo(ProductRegisterVO productRegisterVO, String url) throws IOException;
}
