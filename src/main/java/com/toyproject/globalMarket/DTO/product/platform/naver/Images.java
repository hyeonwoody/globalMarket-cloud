package com.toyproject.globalMarket.DTO.product.platform.naver;

import java.util.ArrayList;

public class Images{
    public RepresentativeImage representativeImage;
    public static class RepresentativeImage{
        public String url;
    }
    public ArrayList<OptionalImage> optionalImages;
    public static class OptionalImage{
        public String url;
    }
}
