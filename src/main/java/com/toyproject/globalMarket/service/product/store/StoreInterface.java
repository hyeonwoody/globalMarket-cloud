package com.toyproject.globalMarket.service.product.store;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.libs.HtmlParser;

public interface StoreInterface {

    default String getHtml (String url){
        HtmlParser htmlParser = new HtmlParser();
        return htmlParser.getHtml(url);
    }
    abstract JsonObject parseHtml(String html);
    abstract int convert (ProductRegisterVO productRegisterVO, JsonObject jsonObject);
    abstract int getProductInfo(ProductRegisterVO productRegisterVO) throws IOException;
}
