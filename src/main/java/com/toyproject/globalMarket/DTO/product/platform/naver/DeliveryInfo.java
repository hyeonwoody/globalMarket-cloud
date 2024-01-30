package com.toyproject.globalMarket.DTO.product.platform.naver;

import java.util.ArrayList;

public class DeliveryInfo{
    DeliveryInfo (){
        this.deliveryType = DeliveryType.DELIVERY.name();
        this.deliveryAttributeType = DeliveryAttributeType.NORMAL.name();
        this.deliveryFee = new DeliveryFee();
        this.claimDeliveryInfo = new ClaimDeliveryInfo();
    }
    public enum DeliveryType {
        DELIVERY, DIRECT
    }
    public String deliveryType;
    public enum DeliveryAttributeType {
        NORMAL, TODAY, OPTION_TODAY, HOPE, TODAY_ARRIVAL, DAWN_ARRIVAL, ARRIVAL_GUARANTEE, SELLER_GUARANTEE
    }
    public String deliveryAttributeType;
    public String deliveryCompany;
    public boolean deliveryBundleGroupUsable;
    public int deliveryBundleGroupId;
    public ArrayList<String> quickServiceAreas;
    public int visitAddressId;
    public DeliveryFee deliveryFee;
    public ClaimDeliveryInfo claimDeliveryInfo;
    public class ClaimDeliveryInfo{
        ClaimDeliveryInfo () {
            returnDeliveryFee = 2000;
            exchangeDeliveryFee = 3000;
        }

        public String returnDeliveryCompanyPriorityType;
        public int returnDeliveryFee;
        public int exchangeDeliveryFee;
        public int shippingAddressId;
        public int returnAddressId;
        public boolean freeReturnInsuranceYn;
    }
    public boolean installationFee;
    public String expectedDeliveryPeriodType;
    public String expectedDeliveryPeriodDirectInput;
    public int todayStockQuantity;
    public boolean customProductAfterOrderYn;
    public int hopeDeliveryGroupId;
}