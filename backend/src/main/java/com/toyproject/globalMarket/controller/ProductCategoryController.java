package com.toyproject.globalMarket.controller;

import com.toyproject.globalMarket.DTO.category.CategoryNaverDTO;
import com.toyproject.globalMarket.configuration.platform.APINaver;
import com.toyproject.globalMarket.service.category.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product/category")
public class ProductCategoryController {

    private final CategoryService categoryService;


    @Autowired
    protected ProductCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    APINaver naver;

    @GetMapping("/naver")
    public ResponseEntity<Map<String, List<String>>> Naver (HttpServletRequest request) {
        Map<String, List<String>> categoryNaver = new HashMap<>();
        List <CategoryNaverDTO> categoryNaverDTOList = new ArrayList<CategoryNaverDTO>();

        int responseCode = 401;
        do {
            //responseCode = categoryService.getCategoryNaverAPI(categoryNaverDTOList);
            responseCode = categoryService.getCategoryNaverDB(categoryNaverDTOList);
//            responseCode = categoryService.getCategoryNaverJson(categoryNaverDTOList);
        } while (responseCode == 401);

        do {
            responseCode = categoryService.getNaverMap(categoryNaver, categoryNaverDTOList);
        }while (responseCode == 401);

        return ResponseEntity.ok(categoryNaver);
    }
}
