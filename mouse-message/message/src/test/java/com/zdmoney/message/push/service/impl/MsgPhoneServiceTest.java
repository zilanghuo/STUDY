package com.zdmoney.message.push.service.impl;

import com.zdmoney.message.InitH2TestEnvironment;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgPhoneDto;
import com.zdmoney.message.api.dto.push.MsgPhoneSearchDto;
import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.message.push.service.IMsgPhoneService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by lwf on 2016/7/22.
 */
public class MsgPhoneServiceTest extends InitH2TestEnvironment {

    @Autowired
    private IMsgPhoneService msgPhoneService;

    @Test
    @Ignore
    public void testSearch() throws Exception {
        MsgPhoneSearchDto searchDto = new MsgPhoneSearchDto();
        searchDto.setBatchNo("13");
        MessagePageResultDto<MsgPhoneDto> lists =msgPhoneService.search(searchDto);
        assertEquals(lists.getDataList().size(),3);

    }

    @Test
    @Ignore
    public void testAddPhone() throws Exception {
        MsgPhoneDto msgPhoneDto = new MsgPhoneDto();
        msgPhoneDto.setPushTime(new Date());
        msgPhoneDto.setCellphoneNum("18852252365");
        msgPhoneDto.setDeviceType("1");
        msgPhoneDto.setBatchNo("32432432");
        MessageResultDto resultDto =  msgPhoneService.addPhone(msgPhoneDto);
        assertEquals(resultDto.getCode(),"0000");
    }

    @Test
    @Ignore
    public void testUpdatePhone() throws Exception {
        MsgPhone msgPhone = new MsgPhone();
        msgPhone.setId("3");
        msgPhone.setBatchNo("123");
        MessageResultDto resultDto = msgPhoneService.updatePhone(msgPhone);
        assertEquals(resultDto.getCode(),"0000");

    }
}