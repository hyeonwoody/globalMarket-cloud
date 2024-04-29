package com.toyproject.globalMarket.DTO.product.platform.naver;

public class CustomerBenefit{
    public ImmediateDiscountPolicy immediateDiscountPolicy;
    public PurchasePointPolicy purchasePointPolicy;
    public ReviewPointPolicy reviewPointPolicy;
    public FreeInterestPolicy freeInterestPolicy;
    public GiftPolicy giftPolicy;
    public MultiPurchaseDiscountPolicy multiPurchaseDiscountPolicy;
    public ReservedDiscountPolicy reservedDiscountPolicy;

    public class DiscountMethod{
        public int value;
        public String unitType;
        public String startDate;
        public String endDate;
    }

    public class FreeInterestPolicy{
        public int value;
        public String startDate;
        public String endDate;
    }

    public class GiftPolicy{
        public String presentContent;
    }

    public class ImmediateDiscountPolicy{
        public DiscountMethod discountMethod;
        public MobileDiscountMethod mobileDiscountMethod;
    }

    public class MobileDiscountMethod{
    }

    public class MultiPurchaseDiscountPolicy{
        public DiscountMethod discountMethod;
        public int orderValue;
        public String orderValueUnitType;
    }

    public class PurchasePointPolicy{
        public int value;
        public String unitType;
        public String startDate;
        public String endDate;
    }

    public class ReservedDiscountPolicy{
        public DiscountMethod discountMethod;
    }

    public class ReviewPointPolicy{
        public int textReviewPoint;
        public int photoVideoReviewPoint;
        public int afterUseTextReviewPoint;
        public int afterUsePhotoVideoReviewPoint;
        public int storeMemberReviewPoint;
        public String startDate;
        public String endDate;
    }
}