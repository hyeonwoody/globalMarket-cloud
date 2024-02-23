package com.toyproject.globalMarket.service.product.store.aliExpress;

import com.google.gson.annotations.SerializedName;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.libs.HtmlManager;
import com.toyproject.globalMarket.libs.HtmlParser;
import com.toyproject.globalMarket.service.product.store.StoreInterface;


import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;



public class AliExpress extends BaseObject implements StoreInterface {

    private static int objectId;
    JsonObject jsonObject;


    public AliExpress() {
        super("AlieExpress", objectId++);
    }

    public interface Information {
        void getField(JsonObject jsonObject);
    }

    @Getter
    public class GeneralFreightInfo implements Information {
        public String shippingFeeText;

        public void setShippingFeeText() {
            this.shippingFeeText = this.shippingFeeText.replaceAll("[^0-9]", "");
        }

        @Override
        public void getField(JsonObject jsonObject) {
            Class<?> thisClass = this.getClass();
            for (Field field : thisClass.getDeclaredFields()) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                try {
                    if (fieldType == String.class) {
                        String value = jsonObject.get(field.getName()).getAsString();
                        field.set(thisClass, value);
                    } else if (fieldType == int.class) {
                        field.setInt(thisClass, jsonObject.get(field.getName()).getAsInt());
                    } else if (fieldType == boolean.class) {
                        field.setBoolean(thisClass, jsonObject.get(field.getName()).getAsBoolean());
                    }
                } catch (IllegalAccessException e) {
                    // Handle the exception gracefully
                    e.printStackTrace();
                    // You can choose to throw a custom exception, skip the field, etc.
                }
            }
        }
    }


    @Getter
    public class SpecificationInfo {
        @SerializedName("propertyList")
        private List<Property> propertyList;

        @SerializedName("i18n")
        private I18n i18n;
        @Getter
        public class Property {
            @SerializedName("attrValue")
            private String attrValue;

            @SerializedName("attrName")
            private String attrName;
        }


        static class I18n {

            private String title;

        }
    }
    @Getter
    private class PriceInfo {

        private Price saleMaxPrice;


        private Details details;
        static class Details {

            private Price maxAmount;

            private Price minAmount;
        }

        private Price saleMinPrice;
        static class Price {
            private String currency;
            private String formatedAmount;
            private int value;
        }
    }

    @Getter
    public class ProductInfo {
        public String productId;
        private String subject;
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
                        LogOutput(LOG_LEVEL.ERROR, ObjectName(), MethodName(), 0, "No JSON data found in the script content.");
                    }
                } else {
                    LogOutput(LOG_LEVEL.ERROR, ObjectName(), MethodName(), 1, "The first child node is not a DataNode.");
                }
            }

        } else {
            LogOutput(LOG_LEVEL.ERROR, ObjectName(), MethodName(), 2, "No <script> elements found in the document.");
        }

        return null;
    }



    @Override
    public int convert(ProductRegisterVO productRegisterVO, JsonObject jsonObject) {

        ProductInfo productInfo = new Gson().fromJson(jsonObject.get("productInfo"), ProductInfo.class);
        PriceInfo priceInfo = new Gson().fromJson(jsonObject.get("priceInfo"), PriceInfo.class);
        SpecificationInfo specificationInfo = new Gson().fromJson(jsonObject.get("specificationInfo"), SpecificationInfo.class);
        GeneralFreightInfo generalFreightInfo = new Gson().fromJson(jsonObject.get("generalFreightInfo"), GeneralFreightInfo.class);
        generalFreightInfo.setShippingFeeText();

        JsonObject descInfo = jsonObject.getAsJsonObject("descInfo");
        if (descInfo.has("productDescUrl"))
            productInfo.detailContent = descInfo.get("productDescUrl").getAsString();
        productInfo.detailContent = parseDetailContent(productInfo.detailContent);

        if (productRegisterVO.getName() == null)
            productRegisterVO.setName(productInfo.getSubject() == null ? "알 수 없는 상품": productInfo.getSubject());
        if (productRegisterVO.getDetailContent() == null){
            productRegisterVO.setDetailContent("<div class=\"product_detail\">\n" +
                    "<div class=\"product_detail_title\">\n" +
                        "<h1>스마트스토어 세계장터 제품 상세정보<h1/>\n" +
                    "</div>\n" +
                    "<div class=\"product_detail_content\">\n" +
                        "<center>\n" +
                            "<div class=\"product_detail_content_logo\">\n" +
                            "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/master/detail/template/template_01.jpg\" alt=\"세계장터 로고 및 슬로건\"/>\n" +
                            "</div>\n" +
                            "<div class=\"product_detail_content_notice\">\n" +
                            "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/master/detail/template/template_02.jpg\" alt=\"세계장터 공지사항\"/>\n" +
                            "</div>\n" +
                            "<div class=\"product_detail_content_hooking\">\n" +
                            "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/" + productRegisterVO.getDBId() + "/detail/template/template_03.jpg\" alt=\"세계장터 제품PR\"/>\n" +
                            "</div>\n" +
                            "<div class=\"product_detail_content_season\">\n" +
                            "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/master/detail/template/template_05.jpg\" alt=\"세계장터 시즌\"/>\n" +
                            "</div>\n" +
                            "<div class=\"product_detail_content_information\">" +
                            productInfo.getDetailContent() +
                            "</div>\n" +
                            "<div class=\"product_detail_content_caution\">\n" +
                            "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/master/detail/template/template_06.jpg\" alt=\"세계장터 유의사항\"/>\n" +
                            "</div>\n" +
                            "<div class=\"product_detail_content_delivery\">\n" +
                            "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/master/detail/template/template_07.jpg\" alt=\"세계장터 배송안내\"/>\n" +
                            "</div>\n" +
                            "<div class=\"product_detail_content_PCCC\">\n" +
                            "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/master/detail/template/template_08.jpg\" alt=\"세계장터 개인통관고유번호 합산과세 설명\"/>\n" +
                            "</div>\n" +
                            "<div class=\"product_detail_content_refund\">\n" +
                            "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/master/detail/template/template_09.jpg\" alt=\"세계장터 교환 환불\"/>\n" +
                            "</div>\n" +
                            "<div class=\"product_detail_content_copyright\">\n" +
                            "<img src=\"https://raw.githubusercontent.com/GlobalMarketKOR/Images/master/detail/template/template_10.jpg\" alt=\"세계장터 상표 및 저작권\"/>\n" +
                            "</div>\n" +
                        "</center>\n" +
                            "</div>\n" +
                            "</div>"
                    );
        }
        else {
            HtmlManager htmlManager = new HtmlManager();
            String detailContent = htmlManager.cleanHtml(productRegisterVO.getDetailContent());
            productRegisterVO.setDetailContent(detailContent);
        }
        if (productRegisterVO.getSalePrice() == 0){
            int price = Math.max(priceInfo.getSaleMaxPrice().value, priceInfo.getDetails().maxAmount.value);
            productRegisterVO.setSalePrice(price - (price % 10));
        }
        if (productRegisterVO.getImages() == null){
            productRegisterVO.setTmpImages(productInfo.getImageList());
        }

        //SEO
        if (productRegisterVO.getKeyword() == null){
            productRegisterVO.setKeyword(new ArrayList<>());
            for (SpecificationInfo.Property property : specificationInfo.getPropertyList()) {
                productRegisterVO.getKeyword().add(property.getAttrName());
                productRegisterVO.getKeyword().add(property.getAttrValue());
            }
        }
        return 0;
    }

    private String parseDetailContent(String detailContent) {
        URL url = null;
        String ret = null;
        try {
            url = new URL(detailContent);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        // Open a connection to the URL
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set up a BufferedReader to read from the connection's input stream
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read the HTML content line by line and store it in a StringBuilder
        StringBuilder htmlContent = new StringBuilder();
        String line;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            htmlContent.append(line);
            htmlContent.append("\n"); // Add newline character as BufferedReader.readLine() removes newline characters
        }
        ret = htmlContent.toString();
        // Close the BufferedReader
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

// Checking if the JsonElement is not null and is a primitive type

    @Override
    public int getProductInfo(ProductRegisterVO productRegisterVO){
        String html = getHtml(productRegisterVO.getUrl());
        JsonObject jsonObject = this.parseHtml(html);
        convert (productRegisterVO, jsonObject);
        return 0;
    }

}


