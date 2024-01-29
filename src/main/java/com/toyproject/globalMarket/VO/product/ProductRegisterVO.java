package com.toyproject.globalMarket.VO.product;
import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import lombok.Getter;
import lombok.Setter;


public class ProductRegisterVO {




    public enum Platform{
        네이버,
        알리익스프레스,
        지마켓

    }
    @Getter
    private Platform platform;
    @Getter
    private String url;

    @Setter
    @Getter
    private String name;


    @Getter
    private String detailContent;

    private Images images;
    public boolean areMembersNotNull() {
        return !url.isEmpty();
    }

    public void setVO (ProductRegisterVO productRegisterVO){
        if (this.name == null){
            this.name = productRegisterVO.getName();
        }
    }

    public void getFromJsonObject(JsonObject jsonObject) {
    }
}
