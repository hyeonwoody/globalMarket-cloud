package com.toyproject.globalMarket.DTO.product.platform.naver;

import java.util.ArrayList;

public class DeliveryInfo{
    public String deliveryType;
    public String deliveryAttributeType;
    public String deliveryCompany;
    public boolean deliveryBundleGroupUsable;
    public int deliveryBundleGroupId;
    public ArrayList<String> quickServiceAreas;
    public int visitAddressId;
    public DeliveryFee deliveryFee;
    public ClaimDeliveryInfo claimDeliveryInfo;
    public class ClaimDeliveryInfo{
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