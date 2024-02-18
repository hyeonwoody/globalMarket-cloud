package com.toyproject.globalMarket.configuration.platform;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.VO.product.NaverImageVO;
import com.toyproject.globalMarket.configuration.APIConfig;
import com.toyproject.globalMarket.libs.BCrypt;
import com.toyproject.globalMarket.libs.EventManager;
import com.toyproject.globalMarket.libs.FileManager;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


@Configuration
public class Naver extends APIConfig {
    private static int objectId = 0;
    public Naver(@Value("${naver.clientId}") String clientId,
                 @Value("${naver.clientSecret}") String clientSecret) {
        super("PlatformNaver", objectId++,clientId, clientSecret, "https://api.commerce.naver.com/external/v1/oauth2/token");
        this.kind = PlatformList.NAVER.ordinal();
        int a = 0;
    }

    public static String generateSignature(String clientId, String clientSecret, Long timestamp) {
        // 밑줄로 연결하여 password 생성
        String password = clientId + "_" + timestamp;
        // bcrypt 해싱
        String hashedPw = BCrypt.hashpw(password, clientSecret);
        // base64 인코딩
        return Base64.getUrlEncoder().encodeToString(hashedPw.getBytes(StandardCharsets.UTF_8));
    }

    public void uploadImages(Images images, String accessToken){


        String workingDirectory = images.representativeImage.url.substring(0, images.representativeImage.url.lastIndexOf("/"));
        FileManager fileManager = new FileManager();
        List<File> imageFileList = fileManager.listImageFiles(workingDirectory);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        // Add each image file to the builder
        for (File imageFile : imageFileList) {
            builder.addFormDataPart("imageFiles", imageFile.getName(),
                    RequestBody.create(MediaType.parse("image/jpeg"), imageFile));
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url("https://api.commerce.naver.com/external/v1/product-images/upload")
                .post(body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "multipart/form-data; boundary=" + ((MultipartBody) body).boundary())
                .build();
        LogOutput(LOG_LEVEL.DEBUG, ObjectName(), MethodName(), 0, "ImagefileList : {0}", imageFileList.toString());

        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                String responseBody = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                NaverImageVO naverImage = mapper.readValue(responseBody, NaverImageVO.class);
                images.representativeImage.setUrl(naverImage.images.get(0).url);
                for (int i = 1; i < naverImage.images.size(); ++i){
                    images.optionalImages.get(i-1).setUrl(naverImage.images.get(i).url);
                }
            }
            else {
                LogOutput(LOG_LEVEL.ERROR, ObjectName(), MethodName(), 2, "Error uploading files: {0} {1}",response.code(),  response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getOAuth() {
        String accessToken = null;
        try {
            // Construct URL and HttpURLConnection
            URL url = new URL("https://api.commerce.naver.com/external/v1/oauth2/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            Long timestamp = System.currentTimeMillis();

            String cryptedSecret = generateSignature (clientId, clientSecret, timestamp);
            
            EventManager.LogOutput(LOG_LEVEL.DEBUG, ObjectName(), MethodName(), 0, "secret : {0}, crypted :{1}",clientSecret, cryptedSecret);
            String requestBody = "client_id="+ this.clientId +
                    "&timestamp=" + timestamp +
                    "&client_secret_sign=" + cryptedSecret +
                    "&grant_type=client_credentials" +
                    "&type=SELF";



            EventManager.LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "Product Register Request Body : {0}", requestBody);
            connection.setDoOutput(true);
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            // Send the request and read the response
            int responseCode = connection.getResponseCode();
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            // Read response content
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();



            String jsonString = response.toString();

            // Parse the JSON string using Gson
                        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

            // Get the value of the "access_token" field as a string
            accessToken = jsonObject.has("access_token") ? jsonObject.get("access_token").getAsString() : null;

            // Print response

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (accessToken == null)
            LogOutput(LOG_LEVEL.ERROR, ObjectName(), MethodName(), 0, "Access token not found in response.");
        return accessToken;

    }
}
