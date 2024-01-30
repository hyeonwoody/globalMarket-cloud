package com.toyproject.globalMarket.VO.product;
import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.DTO.product.platform.naver.SeoInfo;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductRegisterVO {


    public void setCurrentTime(String time) {
        saleStartDate = time;
    }

    public enum Platform{
        네이버,
        알리익스프레스,
        지마켓

    }

    private Platform platform;
    private String url;
    private String name;
    private String detailContent;

    private int price;

    private Images images;

    private String saleStartDate;
    private int returnDeliveryFee;
    private int exchageDeliveryFee;

    private SeoInfo seoInfo;
    private String brandName;
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
