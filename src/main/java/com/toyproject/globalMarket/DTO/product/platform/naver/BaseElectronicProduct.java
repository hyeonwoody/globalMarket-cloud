package com.toyproject.globalMarket.DTO.product.platform.naver;

import lombok.Setter;

@Setter
public class BaseElectronicProduct extends BaseProduct{
    public String itemName;
    public String modelName;
    public String certificationType;

    public String releaseDate;
    public String releaseDateText;
}
