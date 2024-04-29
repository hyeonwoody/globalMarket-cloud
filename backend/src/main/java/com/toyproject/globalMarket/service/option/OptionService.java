package com.toyproject.globalMarket.service.option;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toyproject.globalMarket.VO.option.StandardOptionVO;
import com.toyproject.globalMarket.VO.response.ResponseVO;
import com.toyproject.globalMarket.configuration.platform.APINaver;
import com.toyproject.globalMarket.libs.BaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService extends BaseObject {
    private static int objectId = 0;
    public OptionService() {
        super("CategoryService", objectId++);
    }
    @Autowired
    APINaver naver;

    public int getNaverProductStandardOption (StandardOptionVO standardOption){
        ResponseVO response = null;
        response = naver.getStandardOptions(standardOption);
        if (response.code() == 200){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                StandardOptionVO tmp = objectMapper.readValue(response.body(), StandardOptionVO.class);
                standardOption.set(tmp);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return 0;
        }
        return -1;
    }
}
