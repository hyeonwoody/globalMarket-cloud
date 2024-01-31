package com.toyproject.globalMarket.service.category;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.globalMarket.DTO.category.CateogryNaverDTO;
import com.toyproject.globalMarket.libs.BaseObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;



public class CategoryService extends BaseObject {
        private static int objectId = 0;
        public CategoryService() {
            super("CategoryService", objectId++);
        }

    public int getCategoryNaver (List<CateogryNaverDTO> cateogryNaverDTOList, String accessToken) {
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url("https://api.commerce.naver.com/external/v1/categories?last=false")
                    .get()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
            Response response = client.newCall(request).execute();

            ObjectMapper objectMapper = new ObjectMapper();
            cateogryNaverDTOList.addAll(objectMapper.readValue(response.body().string(), new TypeReference<List<CateogryNaverDTO>>() {
            })); // Add elements to the original list
            return response.code();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


