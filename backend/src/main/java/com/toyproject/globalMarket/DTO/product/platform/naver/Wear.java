package com.toyproject.globalMarket.DTO.product.platform.naver;

import lombok.Setter;

import java.util.List;

@Setter
public class Wear extends BaseProduct{

    public Wear (List<String> additionalInfoList){
        this.material = additionalInfoList.get(0);
        this.color = additionalInfoList.get(1);
        this.size = additionalInfoList.get(2);
        this.manufacturer = additionalInfoList.get(3);
        this.caution = additionalInfoList.get(4);
        this.packDate = additionalInfoList.get(5);
    }
    public String packDate;
    public String packDateText;




}
