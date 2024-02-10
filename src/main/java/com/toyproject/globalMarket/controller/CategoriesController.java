package com.toyproject.globalMarket.controller;

import com.toyproject.globalMarket.DTO.category.CateogryNaverDTO;
import com.toyproject.globalMarket.configuration.platform.Naver;
import com.toyproject.globalMarket.libs.BaseObject;
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
@RequestMapping("/categories")
public class CategoriesController extends BaseObject {

    protected CategoriesController() {
        super("CategoriesController", 0);
    }

    @Autowired
    Naver naver;

    @GetMapping("/naver")
    public ResponseEntity<Map<String, List<String>>> Naver (HttpServletRequest request) {
        Map<String, List<String>> categoryNaver = new HashMap<>();
        List <CateogryNaverDTO> categoryNaverDTOList = new ArrayList<CateogryNaverDTO>();
        CategoryService categoryService = new CategoryService();

        int responseCode = 401;
        do {
            responseCode = categoryService.getCategoryNaver(categoryNaverDTOList, naver.getOAuth());
        } while (responseCode == 401);

        do {
            responseCode = categoryService.getNaverMap(categoryNaver);
        }while (responseCode == 401);

        return ResponseEntity.ok(categoryNaver);
    }
}
