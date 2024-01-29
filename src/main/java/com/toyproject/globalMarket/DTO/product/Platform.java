package com.toyproject.globalMarket.DTO.product;

import com.google.gson.JsonObject;


public interface Platform {
    abstract void JSonObjectToDTO (JsonObject jsonObject);
    abstract Object getDTO();

    abstract void setDTO(Object object);
}
