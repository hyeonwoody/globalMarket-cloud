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
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController extends BaseObject {

    protected CategoriesController() {
        super("CategoriesController", 0);
    }

    @Autowired
    Naver naver;

    @GetMapping("/naver")
    public ResponseEntity<List<CateogryNaverDTO>> Naver (HttpServletRequest request) {
        List <CateogryNaverDTO> cateogryNaverDTOList = new ArrayList<CateogryNaverDTO>();
        CategoryService categoryService = new CategoryService();

        int responseCode = 401;
        do {
            responseCode = categoryService.getCategoryNaver(cateogryNaverDTOList, naver.getOAuth());
        } while (responseCode == 401);

        return ResponseEntity.ok(cateogryNaverDTOList);
    }
}
