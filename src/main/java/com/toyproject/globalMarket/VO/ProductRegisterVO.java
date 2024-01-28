package com.toyproject.globalMarket.VO;
import lombok.Getter;


public class ProductRegisterVO {



    public enum Platform{
        네이버,
        알리익스프레스,
        지마켓

    }
    @Getter
    private Platform platform;
    @Getter
    private String url;


    @Getter
    private String name;


    @Getter
    private String detailContent;
    public boolean areMembersNotNull() {
        return !url.isEmpty();
    }
}
