package com.toyproject.globalMarket.libs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class HtmlParser extends BaseObject {

    public HtmlParser() {
        super("HtmlParser", 0);
    }

    public String getHtml(String url) {
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

    public void getDetailContentImages(String html, Images images) {
        Document doc = Jsoup.parse(html);
        List<Element> imgList = doc.select("img")
                                .stream()
                                .filter(img -> !img.attr("src").contains("raw.githubusercontent"))
                                .collect(Collectors.toList());

        if (!imgList.isEmpty()) {
            // Get the first <script> element
            for (Element element : imgList){
                Images.OptionalImage optionalImage = new Images.OptionalImage();
                optionalImage.setUrl(element.attr("src"));
                images.optionalImages.add(optionalImage);
            }
        } else {
            LogOutput(EventManager.LOG_LEVEL.ERROR, ObjectName(), MethodName(), 2, "No <img> elements found in the document.");
        }
    }
}
