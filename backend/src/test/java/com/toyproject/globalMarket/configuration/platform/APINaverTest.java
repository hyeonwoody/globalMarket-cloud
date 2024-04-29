package com.toyproject.globalMarket.configuration.platform;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.configuration.platform.APINaver;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class APINaverTest {
    public class Client{
        private String id;
        private String secret;
    }

    @Test
    public void getAuth() {
        APINaver naver = new APINaver();

        String accessToken = naver.getOAuth();
    }



    @Test
    public void uploadImages() throws IOException {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(System.getProperty("user.dir") + "/src/main/resources/naverCredentials.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(fileReader);
        Client client = new Client();
        client.id = jsonNode.get("client_id").asText();
        client.secret = jsonNode.get("client_secret").asText();



        APINaver naver = new APINaver();

        Images images = new Images();
        String url = System.getProperty("user.dir");
        images.representativeImage.setUrl(url + "/src/main/resources/detail/template/template_01.jpg");
        images.optionalImages = new java.util.ArrayList<>();
        Images.OptionalImage optionalImage = new Images.OptionalImage();
        optionalImage.setUrl(url + "/src/main/resources/detail/template/template_02.jpg");
        images.optionalImages.add(optionalImage);

        naver.uploadImages(images);
        assertNotEquals(url, images.representativeImage.url);
    }
}