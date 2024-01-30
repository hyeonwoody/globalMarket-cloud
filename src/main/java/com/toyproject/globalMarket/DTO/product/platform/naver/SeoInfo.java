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
    public static class SellerTag {
        public SellerTag(String text) {
            this.text = text;
        }

        public int code;
        public String text;
    }
}