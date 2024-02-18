package com.toyproject.globalMarket.DTO;

import com.toyproject.globalMarket.DTO.product.Platform;
import com.toyproject.globalMarket.DTO.product.platform.Naver;

import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.libs.BaseObject;
import lombok.Data;

@Data
public class Product {

    private static int objectId;


    private ProductRegisterVO.Platform platformType;
    private Platform platform;


    public Product(){}
    public void setDTO(ProductRegisterVO object){
        this.platformType = object.getPlatform();
        switch (this.platformType.toString()){
            case "네이버" :
                this.platform = new Naver();
                break;
            default:
                break;
        }
        this.platform.setDTO(object);
    }
    public Object getDTO(){
        return this.platform;
    }

}