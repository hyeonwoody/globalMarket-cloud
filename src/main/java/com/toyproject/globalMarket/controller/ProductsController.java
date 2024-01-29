package com.toyproject.globalMarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.toyproject.globalMarket.DTO.Product;
import com.toyproject.globalMarket.configuration.PlatformConfig;
import com.toyproject.globalMarket.configuration.platform.Naver;

import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.libs.EventManager;
import com.toyproject.globalMarket.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsController {
    PlatformConfig platform;
    ProductService productService;


    Product product;


    @Autowired
    Naver naver;

    @PostMapping("/register")
    public ResponseEntity<Integer> register (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        int ret = -1;


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));

            ObjectMapper objectMapper = new ObjectMapper();
            ProductRegisterVO productSource = new ProductRegisterVO();
            productSource = objectMapper.readValue(requestBody, ProductRegisterVO.class);

            if (productSource.areMembersNotNull()){
                productService = new ProductService(productSource);
                productSource.setVO(productService.getNewProductInfo());
                switch (productSource.getPlatform()){
                    case 네이버:
                        platform = naver;
                        break;
                    case 알리익스프레스:
                        break;
                    default:
                        break;
                }


                productService.register(platform.getOAuth());

                //productService.search(platform.getOAuth());
            }
            else {
                EventManager.logOutput(2, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "product inputs are null.");
            }

        } catch (IOException e) {
            e.printStackTrace();;
        }




        return ResponseEntity.ok(ret);
    }


}
