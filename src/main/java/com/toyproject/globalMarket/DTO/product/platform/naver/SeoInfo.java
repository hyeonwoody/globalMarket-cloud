package com.toyproject.globalMarket.DTO.product.platform.naver;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class SeoInfo{
    private String pageTitle;
    public String metaDescription;
    public ArrayList<SellerTag> sellerTags;

    public SeoInfo(String pageTitle, String metaDescription, ArrayList<String> keyword){
        this.pageTitle = pageTitle;
        this.metaDescription = metaDescription;
        this.sellerTags = new ArrayList<>();
        for (int i = 2; i < keyword.size(); ++i){
            SellerTag sellerTag = new SellerTag(keyword.get(i));
            this.sellerTags.add(sellerTag);
        }
    }
    public class SellerTag {
        public SellerTag(String text) {
            this.text = text;
        }
        public String text;
    }
}