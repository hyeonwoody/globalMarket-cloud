package com.toyproject.globalMarket.configuration.platform;

import com.toyproject.globalMarket.configuration.PlatformConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
@Component
public class Naver extends PlatformConfig {

    public Naver(@Value("${naver.clientId}") String clientId,
                 @Value("${naver.clientSecret}") String clientSecret) {
        super(clientId, clientSecret, "https://api.commerce.naver.com/external/v1/oauth2/token");
    }

    @Override
    public void getOAuth() {
        try {
            // Construct URL and HttpURLConnection
            URL url = new URL("https://api.commerce.naver.com/external/v1/oauth2/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            long currentTimeMillis = Instant.now().toEpochMilli();
            String timestamp = String.valueOf(currentTimeMillis);
            // Set request body
            String requestBody = "client_id="+ this.clientId +
                    "&timestamp=" + timestamp +
                    "&client_secret_sign" + this.clientSecret +
                    "&grant_type=client_credentials" +
                    "&type=SELF" +
                    "&account_id";

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

            // Print response
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + response.toString());

            // Disconnect the HttpURLConnection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
