package com.toyproject.globalMarket.service.product;

import com.toyproject.globalMarket.VO.ProductRegisterVO;
import com.toyproject.globalMarket.VO.ProductVO;
import com.toyproject.globalMarket.configuration.platform.Naver;
import com.toyproject.globalMarket.service.product.store.Store;
import com.toyproject.globalMarket.service.product.store.aliExpress.AliExpress;


public class ProductManager {

    private ProductRegisterVO productRegister;
    public ProductManager (ProductRegisterVO productRegister){
        this.productRegister = productRegister;
    }

    public ProductVO getNewProductInfo (){


        ProductVO productVO = new ProductVO();
        String url = productRegister.getUrl();
        Store store = null;
        if (url.contains("aliexpress")){
            store = new AliExpress();
        }

        if (store != null){
            store.getProductInfo(productVO, url);
        }

        productVO.originProduct = new ProductVO.OriginProduct();
        productVO.originProduct.setName(productRegister.getName());
        return productVO;
    }
}
