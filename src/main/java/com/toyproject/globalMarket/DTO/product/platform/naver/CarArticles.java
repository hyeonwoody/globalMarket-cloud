package com.toyproject.globalMarket.DTO.product.platform.naver;

import java.util.List;

public class CarArticles extends BaseProduct{

    public CarArticles (List<String> additionalInfoList){
        this.itemName = additionalInfoList.get(0);
        this.modelName = additionalInfoList.get(1);
        this.caution = additionalInfoList.get(2);
        this.manufacturer = additionalInfoList.get(3);
        this.size = additionalInfoList.get(4);
        this.applyModel = additionalInfoList.get(5);
    }

    public String itemName;
    public String modelName;
    public String certificationType;

    public String applyModel;
}
