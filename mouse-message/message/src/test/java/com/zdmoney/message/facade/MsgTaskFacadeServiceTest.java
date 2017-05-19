package com.zdmoney.message.facade;

import com.zdmoney.message.InitH2TestEnvironment;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgTaskDto;
import com.zdmoney.message.api.dto.push.MsgTaskSearchDto;
import com.zdmoney.message.api.facade.IMsgTaskFacadeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by lwf on 2016/7/26.
 */
public class MsgTaskFacadeServiceTest extends InitH2TestEnvironment {
    @Autowired
    private IMsgTaskFacadeService msgTaskFacadeService;

    @Test
    public void testSearch() throws Exception {
        MsgTaskSearchDto searchDto = new MsgTaskSearchDto();
        searchDto.setBatchNo("00");
        searchDto.setPushStartDate(new Date());
        MessagePageResultDto<MsgTaskDto> lists =  msgTaskFacadeService.search(searchDto);
        assertEquals(lists.getDataList().size(),1);
    }
}