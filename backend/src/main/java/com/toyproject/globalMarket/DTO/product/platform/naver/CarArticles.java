package com.toyproject.globalMarket.DTO.product.platform.naver;

import java.util.List;

public class CarArticles extends BaseReleaseProduct {

    public CarArticles (List<String> additionalInfoList){
        this.itemName = additionalInfoList.get(0);
        this.modelName = additionalInfoList.get(1);
        this.caution = additionalInfoList.get(2);
        this.manufacturer = additionalInfoList.get(3);
        this.size = additionalInfoList.get(4);
        this.applyModel = additionalInfoList.get(5);
        this.certificationType = additionalInfoList.get(6);
        this.roadWorthyCertification = additionalInfoList.get(7);
        this.releaseDate = additionalInfoList.get(8);
    }

    public String applyModel;
    public String roadWorthyCertification;
}
