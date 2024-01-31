package com.toyproject.globalMarket.DTO.product;

import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;


public interface Platform {
    abstract void JSonObjectToDTO (JsonObject jsonObject);
    abstract Object getDTO();

    abstract void setDTO(Object object);

    void setDTO(String leafCategoryId);
    abstract void setTime(String time);
    abstract void setImage(Images images);

    abstract void setDetailContent(String detailContent);
}
