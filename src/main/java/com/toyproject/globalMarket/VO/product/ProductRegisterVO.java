package com.toyproject.globalMarket.VO.product;
import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.DTO.product.platform.naver.SeoInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


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
    private String leafCategoryId;
    private String url;
    private String name;
    private String detailContent;

    private String category;

    private int salePrice;
    private int saleQuantity;

    public Images images;

    public void setImages(List<String> imageList) {
        this.images = new Images();
        this.images.representativeImage.url = imageList.get(0).replaceFirst("s", "");
        this.images.representativeImage.url = "http://shop1.phinf.naver.net/20231205_176/1701755146110mAklL_JPEG/704920915873314_63287095.jpg";
        for (String image : imageList) {
            Images.OptionalImage optionalImage = new Images.OptionalImage();
            //optionalImage.url = image.replaceFirst("s", "");
            optionalImage.url = "http://shop1.phinf.naver.net/20231205_176/1701755146110mAklL_JPEG/704920915873314_63287095.jpg";
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
