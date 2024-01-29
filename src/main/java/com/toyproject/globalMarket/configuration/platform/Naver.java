package com.toyproject.globalMarket.configuration.platform;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toyproject.globalMarket.configuration.PlatformConfig;
import com.toyproject.globalMarket.libs.BCrypt;
import com.toyproject.globalMarket.libs.EventManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;




@Configuration
public class Naver extends PlatformConfig {

    public Naver(@Value("${naver.clientId}") String clientId,
                 @Value("${naver.clientSecret}") String clientSecret) {
        super(clientId, clientSecret, "https://api.commerce.naver.com/external/v1/oauth2/token");
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
            this.clientSecret = generateSignature (clientId, clientSecret, timestamp);
            // Set request body
            String requestBody = "client_id="+ this.clientId +
                    "&timestamp=" + timestamp +
                    "&client_secret_sign=" + this.clientSecret +
                    "&grant_type=client_credentials" +
                    "&type=SELF";



            EventManager.logOutput(2, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "Request Code : {0}", requestBody);
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
            accessToken = jsonObject.get("access_token").getAsString();

            // Print response
            EventManager.logOutput(2, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "Response Code : {0}", responseCode);
            EventManager.logOutput(2, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "Response Body : {0}", response.toString());
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }
}
