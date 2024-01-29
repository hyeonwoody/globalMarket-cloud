package com.toyproject.globalMarket.DTO.product.platform;

import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.Platform;
import com.toyproject.globalMarket.DTO.product.platform.naver.OriginProduct;

public class Naver implements Platform {

    private OriginProduct originProduct;
    @Override
    public void JSonObjectToDTO(JsonObject jsonObject){
        JsonObject productInfo = jsonObject.getAsJsonObject("productInfo");
    }

    @Override
    public Object getDTO(){
        return originProduct;
    }

    @Override
    public void setDTO(Object object) {
        this.originProduct = (OriginProduct) object;
    }
}
