package com.toyproject.globalMarket.DTO;

import com.toyproject.globalMarket.DTO.product.Platform;
import com.toyproject.globalMarket.DTO.product.platform.Naver;

import com.toyproject.globalMarket.configuration.PlatformConfig;
import com.toyproject.globalMarket.libs.EventManager;

public class Product {

    private Platform platform;
    public Product(int platform){
        switch (platform){
            case 0 :
                this.platform = new Naver();
                break;
            default:
                EventManager.logOutput(2, this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), 0, "Unknown platform. {0}", platform);
                break;
        }
    }

    public void setDTO(Object object){
        this.platform.setDTO(object);
    }
    public Object getDTO(){
        return this.platform.getDTO();
    }

}