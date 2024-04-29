package com.toyproject.globalMarket.service.product.store.aliExpress;

import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AliExpressTest {

    @Test
    void parseHtml() {
    }

    @Test
    void randos() {
        final String projectPath = System.getProperty("user.dir");
        System.out.println("Project path: " + projectPath);
    }

    @Test
    void getProductInfo() {
        AliExpress aliExpress = new AliExpress();
        ProductRegisterVO productRegisterVO = new ProductRegisterVO();
        productRegisterVO.setPlatform(ProductRegisterVO.Platform.네이버);
        productRegisterVO.setDetailContent("");
        productRegisterVO.setName("");
        productRegisterVO.setUrl("https://ko.aliexpress.com/item/1005004939154940.html");


        aliExpress.getProductInfo(productRegisterVO);
        System.out.println(productRegisterVO.images.optionalImages.get(1).url);
        System.out.println("Product name: " + productRegisterVO.getName());
        assertEquals("", productRegisterVO.getName());
    }

}