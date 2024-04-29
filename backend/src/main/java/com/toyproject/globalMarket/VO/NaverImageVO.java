package com.toyproject.globalMarket.VO;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.toyproject.globalMarket.VO.ImageVO;
import lombok.Data;


import java.util.List;



@Data
public class NaverImageVO {
    @JsonProperty("images")
    public List<ImageVO> images;

}

