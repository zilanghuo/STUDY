package com.zdmoney.message.push.service.impl;

import com.zdmoney.message.InitH2TestEnvironment;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgDeviceDto;
import com.zdmoney.message.api.dto.push.MsgDeviceSearchDto;
import com.zdmoney.message.push.model.MsgDevice;
import com.zdmoney.message.push.service.IMsgDeviceService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by lwf on 2016/7/21.
 */
public class MsgDeviceServiceTest  extends InitH2TestEnvironment {

    @Autowired
    private IMsgDeviceService msgDeviceService;

    @Test
    @Ignore
    public void testGet() throws Exception {
        Long id = 0L;
        MsgDevice msgDevice =  msgDeviceService.get(id);
        assertEquals(msgDevice.getCellphoneNum(),"13681846837");
    }
    @Test
    @Ignore
    public void testSearch() throws Exception{
        MsgDeviceSearchDto searchDto = new MsgDeviceSearchDto();
        searchDto.setStartDate(new Date());
        MessagePageResultDto<MsgDeviceDto> lists =  msgDeviceService.search(searchDto);
        assertEquals(lists.getDataList().size(),2);
    }

    @Test
    @Ignore
    public void testGetByCellphoneNum() throws Exception {
        MsgDevice msgDevice = msgDeviceService.getByCellphoneNum("13681846837");
        assertEquals(msgDevice.getId().intValue(),0);
    }
}