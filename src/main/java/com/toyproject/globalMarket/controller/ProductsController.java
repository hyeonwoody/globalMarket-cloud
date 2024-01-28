package com.toyproject.globalMarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.globalMarket.VO.ProductRegisterVO;
import com.toyproject.globalMarket.VO.ProductVO;
import com.toyproject.globalMarket.service.product.ProductManager;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/register")
    public ResponseEntity<Integer> register (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        int ret = -1;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(requestBody);

            ObjectMapper objectMapper = new ObjectMapper();
            ProductRegisterVO productReact = new ProductRegisterVO();
            productReact = objectMapper.readValue(requestBody, ProductRegisterVO.class);

            if (productReact.areMembersNotNull()){
                ProductVO productVO = new ProductVO();
                ProductManager productManager = new ProductManager(productReact);
                productVO = productManager.getNewProductInfo();
                System.out.println("REGISTER: " + productVO.originProduct.getName());
            }



        } catch (IOException e) {
            e.printStackTrace();;
        }




        return ResponseEntity.ok(ret);
    }


}
