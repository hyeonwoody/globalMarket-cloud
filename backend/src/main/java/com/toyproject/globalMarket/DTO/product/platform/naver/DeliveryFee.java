package com.toyproject.globalMarket.DTO.product.platform.naver;

public class DeliveryFee{
    DeliveryFee (){
        deliveryFeeType = DeliveryFeeType.FREE.name();
        deliveryFeeByArea = new DeliveryFeeByArea();
    }
    public enum DeliveryFeeType {
        FREE, CONDITIONAL_FREE, PAID, UNIT_QUANTITY_PAID, RANGE_QUANTITY_PAID
    }
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
        DeliveryFeeByArea () {
            this.deliveryAreaType = DeliveryAreaType.AREA_3.name();
        }
        public enum DeliveryAreaType {
            AREA_2, AREA_3
        }
        public String deliveryAreaType;
        public int area2extraFee;
        public int area3extraFee;
    }
    public String differentialFeeByArea;
}