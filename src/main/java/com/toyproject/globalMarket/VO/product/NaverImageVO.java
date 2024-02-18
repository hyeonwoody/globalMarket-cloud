package com.toyproject.globalMarket.VO.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


import java.util.List;



@Data
public class NaverImageVO {
    @JsonProperty("images")
    public List<ImageVO> images;

}

