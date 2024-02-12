package com.toyproject.globalMarket.DTO.product.platform;

import com.google.gson.JsonObject;
import com.toyproject.globalMarket.DTO.product.Platform;
import com.toyproject.globalMarket.DTO.product.platform.naver.*;

import com.toyproject.globalMarket.VO.product.ProductRegisterVO;
import com.toyproject.globalMarket.libs.BaseObject;

import java.awt.*;
import java.util.ArrayList;

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
    public void setDTO(ProductRegisterVO object) {

        this.originProduct.setLeafCategoryId(object.getLeafCategoryId());
        this.originProduct.setName(object.getName());
        this.originProduct.setDetailContent(object.getDetailContent());
        this.originProduct.setSalePrice(object.getSalePrice());
        this.originProduct.setStockQuantity(Math.max (1, object.getSaleQuantity()));
        this.originProduct.setImages(object.getImages());
        this.originProduct.setSaleStartDate(object.getSaleStartDate());

        this.originProduct.getDetailAttribute().productInfoProvidedNotice = new ProductInfoProvidedNotice();
        String[] productCategory = object.getCategory();


        BaseProduct product;
        switch (productCategory[0]) {
            case "패션의류":
                this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.WEAR.name();
                this.originProduct.getDetailAttribute().productInfoProvidedNotice.wear = new Wear();
                product = this.originProduct.getDetailAttribute().productInfoProvidedNotice.wear;
                product.setMaterial("가죽");
                product.setColor("검정");
                product.setSize("FREE");
                product.setManufacturer("마음");
                ((Wear) product).setPackDate ("2010-12");
                product.setCaution("f");
                break;
            case "패션잡화":
                if (productCategory[1].contains("신발")) {
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.SHOES.name();
                } else if (productCategory[1].contains("가방")) {
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.BAG.name();
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag = new Bag();
                    product = this.originProduct.getDetailAttribute().productInfoProvidedNotice.bag;
                    ((Bag) product).setType("");
                    ((Bag) product).setType("");
                    product.setMaterial("");
                    product.setColor("");
                    product.setSize("");
                    product.setManufacturer("");
                    product.setCaution("");
                } else if (productCategory[1].contains("주얼리")) {
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.JEWELLERY.name();
                } else {
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.FASHION_ITEMS.name();
                }
                break;
            case "가구/인테리어":
                if (productCategory[1].contains("침구") || productCategory[1].contains("솜류") || productCategory[1].contains("커튼/블라인드")) {
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.SLEEPING_GEAR.name();
                } else {
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.FURNITURE.name();
                }
                break;
            case "디지털/가전":

                if (productCategory[1].contains("영상가전")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.IMAGE_APPLIANCES.name();
                }
                else if (productCategory[1].contains("주방가전") || productCategory[1].contains("생활가전")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.HOME_APPLIANCES.name();
                }
                else if (productCategory[1].contains("계절가전")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.SEASON_APPLIANCES.name();
                }
                else if (productCategory[1].contains("PC") || productCategory[1].contains("노트북") || productCategory[1].contains("주변기기")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.OFFICE_APPLIANCES.name();
                }
                else if (productCategory[1].contains("카메라")){
                    ProductCertificationInfo productCertificationInfo = new ProductCertificationInfo();
                    productCertificationInfo.setCertificationKindType("ETC");
                    this.originProduct.getDetailAttribute().productCertificationInfos = new ArrayList<ProductCertificationInfo>();
                    this.originProduct.getDetailAttribute().productCertificationInfos.add (productCertificationInfo);

                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.OPTICS_APPLIANCES.name();
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.opticsAppliances = new OpticsAppliances();
                    product = this.originProduct.getDetailAttribute().productInfoProvidedNotice.opticsAppliances;
                    ((OpticsAppliances) product).setItemName("");
                    ((OpticsAppliances) product).setModelName("");
                    ((OpticsAppliances) product).setCertificationType("");
                    product.setManufacturer("");
                    product.setSize("");
                    ((OpticsAppliances) product).setWeight("");
                    ((OpticsAppliances) product).setSpecification("");
                    
                }
                else if (productCategory[2].contains("MP3") || productCategory[2].contains("PMP")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.MICROELECTRONICS.name();
                }
                else if (productCategory[1].contains("휴대폰") || productCategory[1].contains("태블릿")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.CELLPHONE.name();
                }
                else if (productCategory[2].contains("내비게이션")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.NAVIGATION.name();
                }
                else if (productCategory[1].contains("자동차")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.CAR_ARTICLES.name();
                }
                break;
            case "생활/건강":
                if (productCategory[1].contains("자동차")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.CAR_ARTICLES.name();
                }
                else if (productCategory[1].contains("악기")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.MUSICAL_INSTRUMENT.name();
                }
                else if (productCategory[1].contains("의료") || productCategory[1].contains("재활")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.MEDICAL_APPLIANCES.name();
                }
                else if (productCategory[1].contains("주방용품")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.KITCHEN_UTENSILS.name();
                }
                break;
            case "화장품/미용":
                this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.COSMETIC.name();
                break;
            case "식품":
                if (productCategory[1].contains("다이어트")){
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.DIET_FOOD.name();
                }
                else {
                    this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.FOOD.name();
                }
                break;
            case "스포츠/레저":
                this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.SPORTS_EQUIPMENT.name();
                break;
            case "도서":
                this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.BOOKS.name();
                break;
            default:
                this.originProduct.getDetailAttribute().productInfoProvidedNotice.productInfoProvidedNoticeType = ProductInfoProvidedNotice.ProductCategory.ETC.name();
                // Handle unknown category if necessary
                break;
        }

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

    public void setImage (Images image){
        this.originProduct.setImages(image);
    }


}
