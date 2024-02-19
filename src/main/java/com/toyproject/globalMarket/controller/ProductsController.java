package com.toyproject.globalMarket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.toyproject.globalMarket.configuration.APIConfig;
import com.toyproject.globalMarket.configuration.platform.APINaver;

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
    APIConfig platform;

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final ProductService productService;


    @Autowired
    APINaver naver;

    protected ProductsController(CategoryService categoryService, ProductService productService) {
        super("ProductController", 0);
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @GetMapping("/register/information")
    public ResponseEntity<ProductRegisterVO> RegisterInformation (HttpServletRequest request) {
        // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
        ProductRegisterVO productSource = new ProductRegisterVO();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String requestParamUrl = request.getParameter("url");
            String[] requestParamCategory = request.getParameter("category").split(">");

            productSource.setCategory(requestParamCategory);
            productSource.setUrl(requestParamUrl);


            LogOutput(LOG_LEVEL.DEBUG, ObjectName(), MethodName(), 0, " productSource :  {0}", productSource.toString());
            categoryService.getAdditionalInfoList(productSource);
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
    public ResponseEntity<Integer> RegisterConfirm (HttpServletRequest request) {
            // 요청을 보낸 클라이언트의 IP주소를 반환합니다.
            ProductRegisterVO productSource = new ProductRegisterVO();


            int responseCode = 0;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));


                ObjectMapper objectMapper = new ObjectMapper();
                LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "input {0}", requestBody);
                productSource = objectMapper.readValue(requestBody, ProductRegisterVO.class);


                if (productSource.areMembersNotNull()) {

                    categoryService.getNewCategoryInfo(productSource);
                    productService.getNewProductInfo(productSource);
                    productService.downloadImages(productSource);

                    switch (productSource.getPlatform()) {
                        case 네이버:
                            platform = naver;
                            ((APINaver) platform).uploadImages(productSource.getImages(), naver.getOAuth());
                            break;
                        case 알리익스프레스:
                            break;
                        default:
                            break;
                    }
                    responseCode = 401;

                    do {
                        responseCode = productService.register(productSource, naver.getOAuth());
                        LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 2, "ResponseCode : {0}", responseCode);
                    } while (responseCode == 401);

                    //productService.search(platform.getOAuth());
                } else {
                    LogOutput(LOG_LEVEL.INFO, ObjectName(), MethodName(), 0, "product inputs are null.");
                }

            } catch (IOException e) {
                e.printStackTrace();
                ;
            }

            return ResponseEntity.ok(responseCode);
        }


}
