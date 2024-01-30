package com.toyproject.globalMarket.DTO.product.platform;

import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.Platform;
import com.toyproject.globalMarket.DTO.product.platform.naver.OriginProduct;
import com.toyproject.globalMarket.libs.BaseObject;

public class Naver extends BaseObject implements Platform  {
    private static int objectId;
    public Naver() {
        super("Naver", objectId++);
    }

    private OriginProduct originProduct;
    private SmartstoreChannelProduct smartstoreChannelProduct;

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
