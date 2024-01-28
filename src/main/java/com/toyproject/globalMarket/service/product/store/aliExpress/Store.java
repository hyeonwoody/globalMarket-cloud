package com.toyproject.globalMarket.service.product.store;

import com.toyproject.globalMarket.VO.ProductVO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface Store {
    abstract void parseHtml(ProductVO productVO, String html);
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
    abstract void getProductInfo(ProductVO productVO, String url);


}
