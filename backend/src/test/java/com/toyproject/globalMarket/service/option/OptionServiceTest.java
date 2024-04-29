package com.toyproject.globalMarket.service.option;

import com.toyproject.globalMarket.VO.option.StandardOptionVO;
import com.toyproject.globalMarket.configuration.platform.APINaver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionServiceTest {

    @Test
    public void getNaverProductStandardOption (){
        APINaver naver = new APINaver();
        StandardOptionVO standardOption = new StandardOptionVO();
        standardOption.setCategoryId(50000837L);
        naver.getStandardOptions(standardOption);

    }

}