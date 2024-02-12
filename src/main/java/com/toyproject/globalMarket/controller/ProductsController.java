package com.toyproject.globalMarket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.toyproject.globalMarket.configuration.AuthConfig;
import com.toyproject.globalMarket.configuration.platform.Naver;

import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.libs.BaseObject;
import com.toyproject.globalMarket.service.category.CategoryService;
import com.toyproject.globalMarket.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsController extends BaseObject {
    AuthConfig platform;

    public final CategoryService categoryService;
    ProductService productService;


    @Autowired
    Naver naver;

    protected ProductsController(CategoryService categoryService) {
        super("ProductController", 0);
        this.categoryService = categoryService;
    }


    @GetMapping("/register/information")
    public ResponseEntity<ProductRegisterVO> RegisterInformation (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        ProductRegisterVO productSource = new ProductRegisterVO();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            String requestParamUrl = request.getParameter("url");
            String requestParamCategory = request.getParameter("category");


            productSource.setName("");
            productSource.setDetailContent("");
            productSource.setUrl(requestParamUrl);


            LogOutput(LOG_LEVEL.DEBUG, ObjectName(), MethodName(), 0, " productRegister URL:  {0}", productSource.getUrl());

            productService = new ProductService(productSource);
            productService.getNewProductInfo(productSource);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(productSource);
    }

        @PostMapping("/register/confirm")
    public ResponseEntity<ProductRegisterVO> RegisterConfirm (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        ProductRegisterVO productSource = new ProductRegisterVO();


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            //LogOutput(LOG_LEVEL.DEBUG, ObjectName(), MethodName(), 0, requestBody);
            ObjectMapper objectMapper = new ObjectMapper();
            productSource = objectMapper.readValue(requestBody, ProductRegisterVO.class);
            LogOutput(LOG_LEVEL.DEBUG, ObjectName(),MethodName(),0, " productRegister detailContent:  {0}", productSource.getDetailContent());

            if (productSource.areMembersNotNull()){
                productService = new ProductService(productSource);
                categoryService.getNewCategoryInfo(productSource);
                productService.getNewProductInfo(productSource);
                switch (productSource.getPlatform()){
                    case 네이버:
                        platform = naver;
                        break;
                    case 알리익스프레스:
                        break;
                    default:
                        break;
                }
                int responseCode =401;
                do {
                    responseCode = productService.register(productSource, platform.getOAuth());
                    LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 2, "ResponseCode : {0}", responseCode);
                } while (responseCode == 401);

                //productService.search(platform.getOAuth());
            }
            else {
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "product inputs are null.");
            }

        } catch (IOException e) {
            e.printStackTrace();;
        }

        return ResponseEntity.ok(productSource);
    }


}
