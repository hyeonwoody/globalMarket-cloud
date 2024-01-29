package com.toyproject.globalMarket.DTO.product.platform.naver;

import java.util.ArrayList;

public class DetailAttribute{
    public NaverShoppingSearchInfo naverShoppingSearchInfo;
    public class NaverShoppingSearchInfo{
        public int modelId;
        public String modelName;
        public String manufacturerName;
        public int brandId;
        public String brandName;
    }
    public AfterServiceInfo afterServiceInfo;
    private class AfterServiceInfo{
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
    private class IsbnInfo{
        public String isbn13;
        public String issn;
        public boolean independentPublicationYn;
    }
    public BookInfo bookInfo;
    private class BookInfo {
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
    public class ProductCertificationInfo{
        public int certificationInfoId;
        public String certificationKindType;
        public String name;
        public String certificationNumber;
        public boolean certificationMark;
        public String companyName;
        public String certificationDate;
    }
    public CertificationTargetExcludeContent certificationTargetExcludeContent;
    public class CertificationTargetExcludeContent{
        public boolean childCertifiedProductExclusionYn;
        public String kcExemptionType;
        public String kcCertifiedProductExclusionYn;
        public boolean greenCertifiedProductExclusionYn;
    }

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
    //public ProductInfoProvidedNotice productInfoProvidedNotice;
    public ArrayList<ProductAttribute> productAttributes;
    public class ProductAttribute{
        public int attributeSeq;
        public int attributeValueSeq;
        public String attributeRealValue;
        public String attributeRealValueUnitCode;
    }
    public boolean cultureCostIncomeDeductionYn;
    public boolean customProductYn;
    public boolean itselfProductionProductYn;
    public boolean brandCertificationYn;
    public SeoInfo seoInfo;

    public class SeoInfo{
        public String pageTitle;
        public String metaDescription;
        public ArrayList<SellerTag> sellerTags;
        public class SellerTag{
            public int code;
            public String text;
        }
    }

}
