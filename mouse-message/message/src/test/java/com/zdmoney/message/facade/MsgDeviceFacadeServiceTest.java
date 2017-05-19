package com.zdmoney.message.facade;

import com.zdmoney.message.InitH2TestEnvironment;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgDeviceDto;
import com.zdmoney.message.api.dto.push.MsgDeviceSearchDto;
import com.zdmoney.message.api.facade.IMsgDeviceFacadeService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class MsgDeviceFacadeServiceTest extends InitH2TestEnvironment {

    @Autowired
    private IMsgDeviceFacadeService msgDeviceFacadeService;

    @Test
    @Ignore
    public void testGet() throws Exception {
        MessageResultDto<MsgDeviceDto> messageResultDto = msgDeviceFacadeService.get(0L);
        assertEquals(messageResultDto.getCode(), MessageResultDto.SUCCESS_CODE);
        MsgDeviceDto data = messageResultDto.getData();
        assertEquals(data.getCellphoneNum(), "13681846837");
    }
    @Test
    @Ignore
    public void testSearch() throws Exception {
        MsgDeviceSearchDto searchDto = new MsgDeviceSearchDto();
        searchDto.setStartDate(new Date());
        MessagePageResultDto<MsgDeviceDto> lists =  msgDeviceFacadeService.search(searchDto);
        assertEquals(lists.getDataList().size(),2);
    }

}