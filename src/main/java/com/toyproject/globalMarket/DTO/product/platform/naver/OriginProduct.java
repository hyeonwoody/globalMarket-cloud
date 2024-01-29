package com.toyproject.globalMarket.DTO.product.platform.naver;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class OriginProduct {
    public enum StatusType {
        WAIT, SALE, OUTOFSTOCK, UNADMISSION, REJECTION, SUSPENSION, CLOSE, PROHIBITION, DELETE
    }


    public enum SaleType {
        NEW, OLD
    }

    public enum DeliveryType {
        DELIVERY, DIRECT
    }

    public enum DeliveryAttributeType {
        NORMAL, TODAY, OPTION_TODAY, HOPE, TODAY_ARRIVAL, DAWN_ARRIVAL, ARRIVAL_GUARANTEE, SELLER_GUARANTEE
    }
    private String statusType;
    private String saleType;
    private String leafCategoryId;
    private String name;
    private String detailContent;
    private Images images;
    private String saleStartDate; //'yyyy-MM-dd'T'HH:mm[:ss][.SSS]XXX' 형식으로 입력합니다.
    private String saleEndDate; //'yyyy-MM-dd'T'HH:mm[:ss][.SSS]XXX' 형식으로 입력합니다.
    private int salePrice;
    private int stockQuantity;
    private DeliveryInfo deliveryInfo;
    private ArrayList<ProductLogistic> productLogistics;
    public class ProductLogistic {
        public int attributeSeq;
        public int attributeValueSeq;
        public String attributeRealValue;
        public String attributeRealValueUnitCode;
    }

    private DetailAttribute detailAttribute;
    private CustomerBenefit customerBenefit;
}

