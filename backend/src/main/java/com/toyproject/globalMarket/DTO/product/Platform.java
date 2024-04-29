package com.toyproject.globalMarket.DTO.product;

import com.google.gson.JsonObject;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;


public interface Platform {
    abstract void JSonObjectToDTO (JsonObject jsonObject);
    abstract Object getDTO();

    abstract void setDTO(ProductRegisterVO object);

    abstract int getSalePrice();
}
