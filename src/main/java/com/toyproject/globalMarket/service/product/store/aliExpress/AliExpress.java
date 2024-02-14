package com.toyproject.globalMarket.service.product.store.aliExpress;

import com.google.gson.annotations.SerializedName;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.DTO.product.platform.naver.SeoInfo;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.configuration.platform.Google;
import com.toyproject.globalMarket.libs.BaseObject;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AliExpress extends BaseObject implements StoreInterface {

    private static int objectId;
    JsonObject jsonObject;


    public AliExpress() {
        super("AlieExpress", objectId++);
    }

    @Getter
    public class SpecificationInfo {
        @SerializedName("propertyList")
        private List<Property> propertyList;

        @SerializedName("i18n")
        private I18n i18n;

        @Getter
        static class Property {
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
    private class ProductInfo {
        private String productId;
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
                        LogOutput(LOG_LEVEL.ERROR, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "No JSON data found in the script content.");
                    }
                } else {
                    LogOutput(LOG_LEVEL.ERROR, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 1, "The first child node is not a DataNode.");
                }
            }

        } else {
            LogOutput(LOG_LEVEL.ERROR, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 2, "No <script> elements found in the document.");
        }
        return null;
    }



    @Override
    public int convert(ProductRegisterVO productRegisterVO, JsonObject jsonObject) {


        ProductInfo productInfo = new Gson().fromJson(jsonObject.get("productInfo"), ProductInfo.class);
        PriceInfo priceInfo = new Gson().fromJson(jsonObject.get("priceInfo"), PriceInfo.class);
        SpecificationInfo specificationInfo = new Gson().fromJson(jsonObject.get("specificationInfo"), SpecificationInfo.class);


        JsonObject descInfo = jsonObject.getAsJsonObject("descInfo");
        if (descInfo.has("productDescUrl"))
            productInfo.detailContent = descInfo.get("productDescUrl").getAsString();
        productInfo.detailContent = parseDetailContent(productInfo.detailContent);


        if (productRegisterVO.getName() == null)
            productRegisterVO.setName(productInfo.getSubject() == null ? "알 수 없는 상품": productInfo.getSubject());
        if (productRegisterVO.getDetailContent() == null)


            productRegisterVO.setDetailContent("<a href='https://ifh.cc/v-6Klwkr' target='_blank'><img src='https://ifh.cc/g/6Klwkr.jpg' border='0'></a>" + productInfo.getDetailContent());
        if (productRegisterVO.getSalePrice() == 0)
            productRegisterVO.setSalePrice(priceInfo.getDetails().minAmount.value - (priceInfo.getDetails().minAmount.value % 10));

        downloadImages(productInfo);
        if (productRegisterVO.getImages() == null)
            productRegisterVO.setImages(productInfo.getImageList());




        //Converting Images

        //SEO



        if (specificationInfo == null){
            Map<String, String> propertyMap = new HashMap<>();

            for (SpecificationInfo.Property property : specificationInfo.getPropertyList()) {
                propertyMap.put(property.getAttrName(), property.getAttrValue());
                productRegisterVO.getSeoInfo().sellerTags.add(new SeoInfo.SellerTag(property.getAttrValue()));
            }
            //productRegisterVO.getSeoInfo().setPageTitle(productInfo.getSubject());
        }
        return 0;
    }

    private void downloadImages(ProductInfo productInfo) {
        final String destinationDirectory = Google.uploadDirectory;
        for (int i = 0; i < productInfo.getImageList().size(); ++i){
            try {
                URL url = new URL(productInfo.getImageList().get(i));
                String fileName = "image" + i + ".webp";
                Path destinationPath = Paths.get(destinationDirectory, fileName);
                try (InputStream inputStream = url.openStream()) {
                    Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Image Downloaded successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
        JsonObject jsonObject = parseHtml(html);
        convert (productRegisterVO, jsonObject);
        return 0;
    }

}
