package com.toyproject.globalMarket.VO.response;

import lombok.Data;

@Data
public class ResponseVO {
    public ResponseVO(int responseCode, String responseBody){
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }
    private int responseCode;
    private String responseBody;

    public int code (){
        return responseCode;
    }
    public String body(){
        return responseBody;
    }
}
