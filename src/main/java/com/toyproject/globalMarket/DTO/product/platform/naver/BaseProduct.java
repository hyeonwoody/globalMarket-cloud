package com.toyproject.globalMarket.DTO.product.platform.naver;

import lombok.Getter;
import lombok.Setter;


@Setter
public class BaseProduct {
    public BaseProduct(){
        warrantyPolicy = "제품 이상시 공정거래 위원회 고시 소비자분쟁해결 기준에 의해 보상합니다.";
        afterServiceDirector = "010-5154-3975";
    }
    public String returnCostReason;
    public String noRefundReason;
    public String qualityAssuranceStandard;
    public String compensationProcedure;
    public String troubleShootingContents;

    public String warrantyPolicy;
    public String afterServiceDirector;




    public String material;
    public String color;
    public String size;
    public String manufacturer;
    public String caution;
}