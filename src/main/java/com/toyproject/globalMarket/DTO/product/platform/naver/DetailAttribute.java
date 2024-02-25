package com.toyproject.globalMarket.DTO.product.platform.naver;

import java.util.ArrayList;

public class DetailAttribute{
    DetailAttribute (){
        this.afterServiceInfo = new AfterServiceInfo();
        this.minorPurchasable = true;

        this.originAreaInfo = new OriginAreaInfo();

        this.seoInfo = new SeoInfo();
    }
    public NaverShoppingSearchInfo naverShoppingSearchInfo;
    public class NaverShoppingSearchInfo{
        public int modelId;
        public String modelName;
        public String manufacturerName;
        public int brandId;
        public String brandName;
    }
    public AfterServiceInfo afterServiceInfo;
    public class AfterServiceInfo{
        AfterServiceInfo (){
            afterServiceTelephoneNumber = "000-000-0000";
            afterServiceGuideContent = "평일 오전10시 ~ 오후 5시";
        }
        public String afterServiceTelephoneNumber;
        public String afterServiceGuideContent;
    }
    public PurchaseQuantityInfo purchaseQuantityInfo;
    public class PurchaseQuantityInfo{
        public int minPurchaseQuantity;
        public int maxPurchaseQuantityPerId;
        public int maxPurchaseQuantityPerOrder;
    }

    public OriginAreaInfo originAreaInfo;
    public class OriginAreaInfo{
        OriginAreaInfo() {
            originAreaCode = "04";
            content = "국내 및 기타국가";
        }
        public String originAreaCode;
        public String importer;
        public String content;
        public boolean plural;
    }

    public SellerCodeInfo sellerCodeInfo;
    public class SellerCodeInfo{
        public String sellerManagementCode;
        public String sellerBarcode;
        public String sellerCustomCode1;
        public String sellerCustomCode2;
    }
    public OptionInfo optionInfo;
    public SupplementProductInfo supplementProductInfo;
    public class SupplementProductInfo{
        public String sortType;
        public ArrayList<SupplementProduct> supplementProducts;
        public class SupplementProduct{
            public int id;
            public String groupName;
            public String name;
            public int price;
            public int stockQuantity;
            public String sellerManagementCode;
            public boolean usable;
        }
    }
    public PurchaseReviewInfo purchaseReviewInfo;
    public IsbnInfo isbnInfo;
    public class IsbnInfo{
        public String isbn13;
        public String issn;
        public boolean independentPublicationYn;
    }
    public BookInfo bookInfo;
    public class BookInfo {
            public String publishDay;
            public Publisher publisher;
            public class Publisher{
                public String code;
                public String text;
            }
            public ArrayList<Author> authors;
            public class Author{
                public String code;
                public String text;
            }
            public ArrayList<Illustrator> illustrators;
            public class Illustrator{
                public String code;
                public String text;
            }
            public ArrayList<Translator> translators;
            public class Translator{
                public String code;
                public String text;
            }
    }
    public String eventPhraseCont;
    public String manufactureDate;
    public String releaseDate;
    public String validDate;
    public String taxType;
    public ArrayList<ProductCertificationInfo> productCertificationInfos;

    public CertificationTargetExcludeContent certificationTargetExcludeContent;

    public String sellerCommentContent;
    public boolean sellerCommentUsable;
    public boolean minorPurchasable;
    public Ecoupon ecoupon;
    public class Ecoupon{
        public String periodType;
        public String validStartDate;
        public String validEndDate;
        public int periodDays;
        public String publicInformationContents;
        public String contactInformationContents;
        public String usePlaceType;
        public String usePlaceContents;
        public boolean restrictCart;
        public String siteName;
    }
    public ProductInfoProvidedNotice productInfoProvidedNotice;
    public ArrayList<ProductAttribute> productAttributes;
    public class ProductAttribute{
        public int attributeSeq;
        public int attributeValueSeq;
        public String attributeRealValue;
        public String attributeRealValueUnitCode;
    }
    //public boolean cultureCostIncomeDeductionYn;
    public boolean customProductYn;
    public boolean itselfProductionProductYn;
    public boolean brandCertificationYn;
    public SeoInfo seoInfo;
}
