package com.toyproject.globalMarket.DTO.product.platform.naver;

import lombok.Setter;

import java.util.List;

@Setter
public class Etc extends BaseProduct{

    public Etc (List<String> additionalInfoList){
        this.itemName = additionalInfoList.get(0);
        this.modelName = additionalInfoList.get(1);
        this.manufacturer = additionalInfoList.get(2);
    }
    public String itemName;
    public String modelName;

}
