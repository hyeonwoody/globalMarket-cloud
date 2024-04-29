package com.toyproject.globalMarket.DTO.product.platform.naver;

import lombok.Setter;

import java.util.List;


@Setter
public class OpticsAppliances extends BaseReleaseProduct {

    public OpticsAppliances(List<String> additionalInfoList){
            this.itemName = additionalInfoList.get(0);
            this.modelName = additionalInfoList.get(1);
            this.certificationType = additionalInfoList.get(2);
            this.releaseDate = additionalInfoList.get(3);
            this.manufacturer = additionalInfoList.get(4);
            this.size = additionalInfoList.get(5);
            this.weight = additionalInfoList.get(6);
            this.specification = additionalInfoList.get(7);
    }

    public String weight;
    public String specification;
}
