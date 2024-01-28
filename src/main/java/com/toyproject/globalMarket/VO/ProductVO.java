package com.toyproject.globalMarket.VO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProductVO {

    public OriginProduct originProduct;
    public static class OriginProduct {
        public enum StatusType {
            WAIT, SALE, OUTOFSTOCK, UNADMISSION, REJECTION, SUSPENSION, CLOSE, PROHIBITION, DELETE
        }


        private StatusType statusType;

        public enum SaleType {
            NEW, OLD
        }
        private String saleType;
        private String leafCategoryId;

        @Getter
        @Setter
        private String name; //REQUIRED 상품명
        @Getter
        @Setter
        private String detailContent; //REQUIRED 상품 수정 시에만 생략할 수 있습니다. 이 경우 기존에 저장된 상품 상세 정보 값이 유지됩니다.

        @Getter
        @Setter
        private Images images; //REQUIRED 상품 이미지로 대표 이미지(1000x1000픽셀 권장)와 최대 9개의 추가 이미지 목록을 제공할 수 있습니다. 대표 이미지는 필수이고 추가 이미지는 선택 사항입니다. 이미지 URL은 반드시 상품 이미지 다건 등록 API로 이미지를 업로드하고 반환받은 URL 값을 입력해야 합니다.
        private class Images {
            @Getter
            @Setter
            private RepresentativeImage representativeImage; //REQUIRED
            private class RepresentativeImage {
                private String url;
            }
            @Getter
            @Setter
            private OptionalImages optionalImages; //추가 이미지 목록. 최대 9개. 이미지 URL은 반드시 상품 이미지 다건 등록 API로 이미지를 업로드하고 반환받은 URL 값을 입력해야 합니다.
            private class OptionalImages{
                private List<String> url; //REQUIRED

                public void add (String url){
                    this.url.add(url);
                }
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
            private DeliveryType deliveryType;

            public enum DeliveryAttributeType {
                NORMAL, TODAY, OPTION_TODAY, HOPE, TODAY_ARRIVAL, DAWN_ARRIVAL, ARRIVAL_GUARANTEE, SELLER_GUARANTEE
            }
            private DeliveryAttributeType deliveryAttributeType;
            private String deliveryCompany;

            private DeliveryFee deliveryFee;


            private static class DeliveryFee {
                private enum DeliveryFeeType {
                    FREE, CONDITIONAL_FREE, PAID, UNIT_QUANTITY_PAID,RANGE_QUANTITY_PAID
                }
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

                @Getter
                @Setter
                private long modelId;

                @Getter
                @Setter
                private String modelName;
                @Getter
                @Setter
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
            }//**

            private ProductCertificationInfo[] productCertificationInfos;
            private static class ProductCertificationInfo{
                private enum CertificationKindType {
                    KC_CERTIFICATION,
                    CHILD_CERTIFICATION,
                    GREEN_PRODUCTS,
                    PARALLEL_IMPORT,
                    OVERSEAS,
                    ETC
                }
                private CertificationKindType certificationInfoId;
                private String name;
                private String certificationNumber;

            }

            private CertificationTargetExcludeContent certificationTargetExcludeContent;
            private static class CertificationTargetExcludeContent{
                private boolean childCertifiedProductExclusionYn;

                private enum KCExemptionType {
                    OVERSEAS,
                    SAFE_CRITERION,
                    PARALLEL_IMPORT
                }
                private KCExemptionType kcExemptionType;

                private enum KCCertifiedProductExclusionType {
                    FALSE,
                    KC_EXEMPTION_OBJECT,
                    TRUE
                }
                private KCCertifiedProductExclusionType kcCertifiedProductExclusionType;
            }
            private boolean MinorPurchasable;


            private ProductInfoProvidedNotice productInfoProvidedNotice; //**
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
                private ProductInfoProvidedNoticeType productInfoProvidedNoticeType;
                private Wear wear;

                interface Rules{
                    String returnCostReason = null;
                    String noRefundReason = null;
                    String qualityAssuranceStandard = null;
                    String compensationProcedure = null;
                    String troubleShootingContents = null;
                    String manufacture = null;
                    String warrantyPolicy = null;
                    String afterServiceDirector = null;
                }
                private static class Wear implements Rules{

                    private String material;
                    private String color;
                    private String size;

                    private String caution;

                }
                private static class OpticsAppliances implements Rules{

                    private String itemName;
                    private String modelName;
                    private String certificationType;
                    private String size;
                    private String weight;
                    private String specification;

                }
            }
        }
    }









    // Add other necessary methods as needed
}