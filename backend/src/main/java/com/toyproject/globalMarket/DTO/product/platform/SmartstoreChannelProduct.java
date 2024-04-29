package com.toyproject.globalMarket.DTO.product.platform;

public class SmartstoreChannelProduct {
    SmartstoreChannelProduct (){
        this.naverShoppingRegistration = false;
        this.channelProductDisplayStatusType = ChannelProductDisplayStatusType.ON.name();
    }

    public enum ChannelProductDisplayStatusType{
        WAIT, ON, SUSPENSION
    }
    private boolean naverShoppingRegistration;
    private String channelProductDisplayStatusType;
}
