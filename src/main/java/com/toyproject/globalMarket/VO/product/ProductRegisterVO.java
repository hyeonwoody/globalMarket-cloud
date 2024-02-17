package com.toyproject.globalMarket.VO.product;
import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.DTO.product.platform.naver.SeoInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
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
    private String wholeCategoryName;
    private String leafCategoryId;
    private String url;
    private String name;
    private String detailContent;

    private String[] category;
    private ArrayList<String> additionalInfoList;
    private ArrayList<String> keyword;

    private int salePrice;
    private int saleQuantity;

    public Images images;

    public void setTmpImages(List<String> imageList) {
        this.images = new Images();
        this.images.representativeImage.url = imageList.get(0).replaceFirst("s", "");
        for (int i = 1; i < imageList.size(); i++) {
            String image = imageList.get(i);
            Images.OptionalImage optionalImage = new Images.OptionalImage();
            optionalImage.url = image.replaceFirst("s", "");
            this.images.optionalImages.add(optionalImage);
        }
    }

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
