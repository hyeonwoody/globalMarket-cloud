package com.toyproject.globalMarket.VO;

import java.util.List;

public class ProductVO {
    public enum StatusType {
        WAIT, SALE, OUTOFSTOCK, UNADMISSION, REJECTION, SUSPENSION, CLOSE, PROHIBITION, DELETE
    }

    public enum SaleType {
        NEW, OLD
    }





    private StatusType statusType; //REQUIRED 상품 API에서 상품의 판매 상태를 나타내기 위해 사용하는 코드입니다. 상품 등록 시에는 SALE(판매 중)만 입력할 수 있으며, 상품 수정 시에는 SALE(판매 중), SUSPENSION(판매 중지)만 입력할 수 있습니다. StockQuantity의 값이 0인 경우 상품 상태는 OUTOFSTOCK(품절)으로 저장됩니다. 품절 상태의 상품을 판매 중으로 변경할 경우, StockQuantity(재고 수량)와 함께 statusType을 SALE(판매 중)로 입력해야 합니다.
    // WAIT(판매 대기), SALE(판매 중), OUTOFSTOCK(품절), UNADMISSION(승인 대기), REJECTION(승인 거부), SUSPENSION(판매 중지), CLOSE(판매 종료), PROHIBITION(판매 금지)
    private SaleType saleType; //상품 API에서 상품의 판매 유형을 나타내기 위해 사용하는 코드입니다. 미입력 시 NEW(새 상품)로 저장됩니다.
    // NEW(새 상품), OLD(중고 상품)
    private String leafCategoryId; //상품 등록 시 필수입니다. 상품 수정 시 카탈로그 ID(modelId)를 입력한 경우 필수입니다. 표준형 옵션 카테고리 상품 수정 요청의 경우 CategoryId 변경 요청은 무시됩니다.
    private String name; //REQUIRED 상품명
    private String detailContent; //REQUIRED 상품 수정 시에만 생략할 수 있습니다. 이 경우 기존에 저장된 상품 상세 정보 값이 유지됩니다.

    private Images images; //REQUIRED 상품 이미지로 대표 이미지(1000x1000픽셀 권장)와 최대 9개의 추가 이미지 목록을 제공할 수 있습니다. 대표 이미지는 필수이고 추가 이미지는 선택 사항입니다. 이미지 URL은 반드시 상품 이미지 다건 등록 API로 이미지를 업로드하고 반환받은 URL 값을 입력해야 합니다.
    private class Images {
        private RepresentativeImage representativeImage; //REQUIRED
        private class RepresentativeImage {
            private String url;
        }
        private OptionalImages optionalImages; //추가 이미지 목록. 최대 9개. 이미지 URL은 반드시 상품 이미지 다건 등록 API로 이미지를 업로드하고 반환받은 URL 값을 입력해야 합니다.
        private class OptionalImages{
            private List<String> url; //REQUIRED
        }
    }
    private String saleStartDate; //매 시각 00분으로만 설정 가능합니다. 'yyyy-MM-dd'T'HH:mm[:ss][.SSS]XXX' 형식으로 입력합니다.
    private String saleEndDate; //매 시각 59분으로만 설정 가능합니다. 'yyyy-MM-dd'T'HH:mm[:ss][.SSS]XXX' 형식으로 입력합니다.
    private int salePrice; //REQUIRED
    private int stockQuantity;
    private DeliveryInfo deliveryInfo; // Assuming you have a class or structure for delivery info
    private static class DeliveryInfo {
        public enum DeliveryType {
            DELIVERY, DIRECT
        }

        public enum DeliveryAttributeType {
            NORMAL, TODAY, OPTION_TODAY, HOPE, TODAY_ARRIVAL, DAWN_ARRIVAL, ARRIVAL_GUARANTEE, SELLER_GUARANTEE
        }
        private enum DeliveryFeeType {
            FREE, CONDITIONAL_FREE, PAID, UNIT_QUANTITY_PAID,RANGE_QUANTITY_PAID
        }
        private DeliveryType deliveryType;
        private DeliveryAttributeType deliveryAttributeType;
        private DeliveryFee deliveryFee;
        private static class DeliveryFee {
            private DeliveryFeeType deliveryFeeType;
            private int baseFee;
            private int freeConditionalAmount;
            private int repeatQuantity;
            private int secondBaseQuantity;
            private int thirdBaseQuantity;
            private int thirdExtraFee;
            private DeliveryAttributeType deliveryFeePayType;
            private DeliveryFeeByArea deliveryFeeByArea;
            private class DeliveryFeeByArea {
                private enum DeliveryAreaType{
                    AREA_2, AREA_3
                }
                private DeliveryAreaType deliveryAreaType;
                private int area2extraFee;
                private int area3extraFee;
            }
            private String differentialFeeByArea;
        }

        private ClaimDeliveryInfo claimDeliveryInfo;
        private static class ClaimDeliveryInfo{
            public enum ReturnDeliveryCompanyPriorityType {
                PRIMARY, SECONDARY_1, SECONDARY_2, SECONDARY_3, SECONDARY_4,
                SECONDARY_5, SECONDARY_6, SECONDARY_7, SECONDARY_8, SECONDARY_9
            }

            private ReturnDeliveryCompanyPriorityType returnDeliveryCompanyPriorityType;
            private int returnDeliveryFee;
            private int exchangeDeliveryFee;
            private long shippingAddressId;
            private long returnAddressId;
            private boolean freeReturnInsuranceYn;
        }
    }
    // Constructors, getters, and setters...
    private DetailAttribute detailAttribute;
    private static class DetailAttribute {
        private NaverShoppingSearchInfo naverShoppingSearchInfo;
        private static class NaverShoppingSearchInfo{
            private long modelId;
            private String modelName;
            private String manufacturerName;
            private long brandId;
            private String brandName;
        }

        private AfterServiceInfo afterServiceInfo;
        private static class AfterServiceInfo {
            private String afterServiceTelephoneNumber;
            private String afterServiceGuideContent;
        }
        private PurchaseQuantityInfo purchaseQuantityInfo;

        private static class PurchaseQuantityInfo{
            private int minPurchaseQuantity;
            private int maxPurchaseQuantityPerId;
            private int maxPurchaseQuantityPerOrder;
        }

        private OriginAreaInfo originAreaInfo;

        private static class OriginAreaInfo{
            private String originAreaCode;
            private String importer;
            private String content;
            private boolean plural;
        }
        private SellerCodeInfo sellerCodeInfo;
        private static class SellerCodeInfo{
                private String sellerManagementCode;
                private String sellerBarcode;
                private String sellerCustomCode1;
                private String sellerCustomCode2;
        }
        private OptionInfo optionInfo;
        private static class OptionInfo {
            private enum SimpleOptionSortType {
                CREATE,
                ABC,
                LOW_PRICE,
                HIGH_PRICE
            }
            private SimpleOptionSortType simpleOptionSortType;
            private List<Object> optionSimple;
            private List<Object> optionCustom;
            private String optionCombinationSortType;
            private Object optionCombinationGroupNames;
            private List<Object> optionCombinations;
            private List<Object> standardOptionGroups;
            private List<Object> optionStandards;
            private boolean useStockManagement;
            private List<String> optionDeliveryAttributes;
        }
        private boolean MinorPurchasable;


        private ProductInfoProvidedNotice productInfoProvidedNotice;
        private static class ProductInfoProvidedNotice {
            private enum ProductInfoProvidedNoticeType {
                WEAR,
                SHOES,
                BAG,
                FASHION_ITEMS,
                SLEEPING_GEAR,
                FURNITURE,
                IMAGE_APPLIANCES,
                HOME_APPLIANCES,
                SEASON_APPLIANCES,
                OFFICE_APPLIANCES,
                OPTICS_APPLIANCES,
                MICROELECTRONICS,
                CELLPHONE,
                NAVIGATION,
                CAR_ARTICLES,
                MEDICAL_APPLIANCES,
                KITCHEN_UTENSILS,
                COSMETIC,
                JEWELLERY,
                FOOD,
                GENERAL_FOOD,
                DIET_FOOD,
                KIDS,
                MUSICAL_INSTRUMENT,
                SPORTS_EQUIPMENT,
                BOOKS,
                LODGMENT_RESERVATION,
                TRAVEL_PACKAGE,
                AIRLINE_TICKET,
                RENT_CAR,
                RENTAL_HA,
                RENTAL_ETC,
                DIGITAL_CONTENTS,
                GIFT_CARD,
                MOBILE_COUPON,
                MOVIE_SHOW,
                ETC_SERVICE,
                BIOCHEMISTRY,
                BIOCIDAL,
                ETC
            }
        }
    }
    // Add other necessary methods as needed
}