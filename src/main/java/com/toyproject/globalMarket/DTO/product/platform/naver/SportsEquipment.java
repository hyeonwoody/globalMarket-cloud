package com.toyproject.globalMarket.DTO.product.platform.naver;

import java.util.List;

public class SportsEquipment extends BaseProduct{
    public SportsEquipment (List<String> additionalInfoList){
        this.itemName = additionalInfoList.get(0);
        this.modelName = additionalInfoList.get(1);
        this.certificationType = additionalInfoList.get(2);
        this.size = additionalInfoList.get(3);
        this.weight = additionalInfoList.get(4);
        this.color = additionalInfoList.get(5);
        this.material = additionalInfoList.get(6);
        this.components = additionalInfoList.get(7);
        this.releaseDateText = additionalInfoList.get(8);
        this.manufacturer = additionalInfoList.get(9);
        this.detailContent = additionalInfoList.get(10);
    }
    public String itemName;
    public String modelName;
    public String certificationType;
    public String weight;
    public String components;
    public String releaseDate;
    public String releaseDateText;
    public String detailContent;
}
