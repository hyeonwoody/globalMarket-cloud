package com.toyproject.globalMarket.service.category;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.globalMarket.DTO.category.CategoryNaverDTO;
import com.toyproject.globalMarket.entity.CategoryNaverEntity;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.repository.CategoryRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CategoryService extends BaseObject {
    private static int objectId = 0;
    private static List <CategoryNaverDTO> m_cateogryNaverDTOList;
    private final CategoryRepository categoryRepository;

    public static List<CategoryNaverDTO> getCateogryNaverDTOList() {
        return m_cateogryNaverDTOList;
    }

    public CategoryService(CategoryRepository categoryRepository) {
        super("CategoryService", objectId++);
        this.categoryRepository = categoryRepository;
    }

    public int getCategoryNaver(List<CategoryNaverDTO> cateogryNaverDTOList, String accessToken) {
        OkHttpClient client = new OkHttpClient();
        int ret = 200;

        if (m_cateogryNaverDTOList == null || m_cateogryNaverDTOList.isEmpty()) {
            m_cateogryNaverDTOList = categoryRepository.getCategoryNaverList();
//                Request request = new Request.Builder()
//                        .url("https://api.commerce.naver.com/external/v1/categories?last=false")
//                        .get()
//                        .addHeader("Authorization", "Bearer " + accessToken)
//                        .build();
//                Response response = client.newCall(request).execute();
//                ObjectMapper objectMapper = new ObjectMapper();
//                m_cateogryNaverDTOList = new ArrayList<>();
//                m_cateogryNaverDTOList.addAll(objectMapper.readValue(response.body().string(), new TypeReference<List<CategoryNaverDTO>>() {
//                }));
//                ret = response.code();
        }
        //categoryRepository.APItoSave(m_cateogryNaverDTOList);


        cateogryNaverDTOList.addAll(m_cateogryNaverDTOList);
        return ret;


    }

    public int getNaverMap(Map<String, List<String>> categoryNaver) {
        for (CategoryNaverDTO cateogryNaverDTO : m_cateogryNaverDTOList) {
            String[] ret = cateogryNaverDTO.getWholeCategoryName().split(">");
            String key = null;
            String value = null;
            if (ret.length == 1){
                key = ret[0];
                List<String> tmp = categoryNaver.getOrDefault("FIRST", new ArrayList<>());
                tmp.add(key);
                categoryNaver.put("FIRST", tmp);
            }

            else {
                key = ret[ret.length-2];
                value = ret[ret.length-1];
            }
            List<String> tmp = categoryNaver.getOrDefault(key, new ArrayList<>());
            if (ret.length != 1)
                tmp.add(value);
            categoryNaver.put(key, tmp);
        }
        return 0;
    }

    public String findNaverLeafCategoryId(String category) {
        for (CategoryNaverDTO cateogryNaverDTO : m_cateogryNaverDTOList){
            if (category.equals(cateogryNaverDTO.getName()))
                return cateogryNaverDTO.getId();
        }
        return "50000000";
    }
}


