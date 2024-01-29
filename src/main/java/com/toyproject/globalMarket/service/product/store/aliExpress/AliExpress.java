package com.toyproject.globalMarket.service.product.store.aliExpress;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.libs.EventManager;
import com.toyproject.globalMarket.service.product.store.StoreInterface;


import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

public class AliExpress implements StoreInterface {

    JsonObject jsonObject;

    public class SpecificationInfo {
        @SerializedName("propertyList")
        private List<Property> propertyList;

        @SerializedName("i18n")
        private I18n i18n;

        static class Property {
            @SerializedName("attrValue")
            private String attrValue;

            @SerializedName("attrName")
            private String attrName;
        }

        static class I18n {
            @SerializedName("title")
            private String title;

        }
    }
    class PriceInfo {
        @SerializedName("saleMaxPrice")
        private Price saleMaxPrice;

        @SerializedName("saleMinPrice")
        private Price saleMinPrice;
        static class Price {
            private String currency;
            private String formatedAmount;
            private double value;
        }
    }
    class ProductInfo {
        @SerializedName("productId")
        private String productId;

        @SerializedName("subject")
        private String subject;

        @SerializedName("imageList")
        private List<String> imageList;

        private String detailContent;
    }

    @Override
    public JsonObject parseHtml(String html) {
        Document doc = Jsoup.parse(html);
        Elements scripts = doc.select("script");

        if (!scripts.isEmpty()) {
            // Get the first <script> element
            Element firstScript = scripts.first();
            if (firstScript.childNodeSize() > 0) {
                // Get the first child node
                org.jsoup.nodes.Node firstChildNode = firstScript.childNode(0);

                // Check if it's a TextNode
                if (firstChildNode instanceof DataNode) {
                    String scriptContent = ((DataNode) firstChildNode).getWholeData();

                    // Check if the script content contains JSON data
                    if (scriptContent.contains("window.runParams")) {
                        int dataIndex = scriptContent.indexOf("data: ");
                        String json = scriptContent.substring(dataIndex + "data: ".length());
                        json = json.substring(0, json.length() - 9);
                        // Extract the JSON string from the script content

                        // Parse the JSON string into a JsonObject using Gson
                        jsonObject = new Gson().fromJson(json, JsonObject.class);
                        return jsonObject;
                        // Access the properties of the JsonObject as needed
                    } else {
                        EventManager.logOutput(2, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "No JSON data found in the script content.");
                    }
                } else {
                    EventManager.logOutput(2, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 1, "The first child node is not a DataNode.");
                }
            }

        } else {
            EventManager.logOutput(2, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 2, "No <script> elements found in the document.");
        }
        return null;
    }
    @Override
    public ProductRegisterVO translate(JsonObject jsonObject){
        ProductRegisterVO productRegisterVO = new ProductRegisterVO();
        ProductInfo productInfo = new Gson().fromJson(jsonObject.get("productInfo"), ProductInfo.class);
        PriceInfo priceInfo = new Gson().fromJson(jsonObject.get("priceInfo"), PriceInfo.class);
        SpecificationInfo specificationInfo = new Gson().fromJson(jsonObject.get("priceInfo"), SpecificationInfo.class);

        JsonObject descInfo = jsonObject.getAsJsonObject("descInfo");
        if (descInfo.has("productDescUrl")) {
            productInfo.detailContent = descInfo.get("productDescUrl").getAsString();
        }

// Checking if the JsonElement is not null and is a primitive type




        return productRegisterVO;
    }
    @Override
    public ProductRegisterVO getProductInfo(String url){
        String html = getHtml(url);
        JsonObject jsonObject = parseHtml(html);
        return translate(jsonObject);
    }

}
