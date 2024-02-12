package com.toyproject.globalMarket.DTO;

import com.toyproject.globalMarket.DTO.product.Platform;
import com.toyproject.globalMarket.DTO.product.platform.Naver;

import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.libs.BaseObject;

public class Product extends BaseObject {

    private Platform platform;
    private static int objectId;
    public Product(int platform){
        super("Product", objectId++);
        switch (platform){
            case 0 :
                this.platform = new Naver();
                break;
            default:
                LogOutput(LOG_LEVEL.ERROR, ObjectName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "Unknown platform. {0}", platform);
                break;
        }
    }
    public void setDTO(ProductRegisterVO object){
        this.platform.setDTO(object);
    }
    public Object getDTO(){
        return this.platform;
    }

}