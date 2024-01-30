package com.toyproject.globalMarket.DTO.product.platform;

import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.Platform;
import com.toyproject.globalMarket.DTO.product.platform.naver.Bag;

import com.toyproject.globalMarket.DTO.product.platform.naver.Images;
import com.toyproject.globalMarket.DTO.product.platform.naver.OriginProduct;
import com.toyproject.globalMarket.DTO.product.platform.naver.ProductInfoProvidedNotice;
import com.toyproject.globalMarket.libs.BaseObject;

import java.awt.*;

public class Naver extends BaseObject implements Platform  {
    private static int objectId;
    public Naver() {
        super("Naver", objectId++);
        this.originProduct = new OriginProduct();
        this.smartstoreChannelProduct = new SmartstoreChannelProduct();
    }

    private OriginProduct originProduct;
    private SmartstoreChannelProduct smartstoreChannelProduct;

    @Override
    public void JSonObjectToDTO(JsonObject jsonObject){
        JsonObject productInfo = jsonObject.getAsJsonObject("productInfo");
    }

    @Override
    public Object getDTO(){
        return this.originProduct;
    }



    @Override
    public void setDTO(Object object) {
        this.originProduct = (OriginProduct) object;
    }
    @Override
    public void setDTO(String leafCategoryId){
        this.originProduct.setLeafCategoryId(leafCategoryId);
        this.originProduct.setName (leafCategoryId);
        this.originProduct.setDetailContent("ffffsd");
        this.originProduct.setSalePrice(2220);
        this.originProduct.setStockQuantity(99999999);
        this.originProduct.getDetailAttribute().productInfoProvidedNotice = new ProductInfoProvidedNotice();
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag = new Bag();
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag.material = "ff";
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag.type = "ff";
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag.color = "ff";
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag.size = "ff";
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag.warrantyPolicy = "ff";
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag.manufacturer = "ff";
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = "BAG";
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag.afterServiceDirector = "ff";
        this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag.caution = "ff";
    }


    public void setLeafCategoryId (String leafCategoryId){
        this.originProduct.setLeafCategoryId(leafCategoryId);
    }

    public void setTime (String time){
        this.originProduct.setSaleStartDate(time);
    }
    public void setImage (Images image){
        this.originProduct.setImages(image);
    }
}
