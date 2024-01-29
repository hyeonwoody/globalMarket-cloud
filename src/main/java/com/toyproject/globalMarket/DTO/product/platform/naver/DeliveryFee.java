package com.toyproject.globalMarket.DTO.product.platform.naver;

public class DeliveryFee{
    public String deliveryFeeType;
    public int baseFee;
    public int freeConditionalAmount;
    public int repeatQuantity;
    public int secondBaseQuantity;
    public int secondExtraFee;
    public int thirdBaseQuantity;
    public int thirdExtraFee;
    public String deliveryFeePayType;
    public DeliveryFeeByArea deliveryFeeByArea;
    public class DeliveryFeeByArea{
        public String deliveryAreaType;
        public int area2extraFee;
        public int area3extraFee;
    }
    public String differentialFeeByArea;
}