package com.zdmoney.message.facade;

import com.zdmoney.message.InitH2TestEnvironment;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgBatchDto;
import com.zdmoney.message.api.dto.push.MsgBatchSearchDto;
import com.zdmoney.message.api.facade.IMsgBatchFacadeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lwf on 2016/7/20.
 */
public class MsgBatchFacadeServiceTest extends InitH2TestEnvironment {

    @Autowired
    private IMsgBatchFacadeService msgBatchFacadeServices;

    @Test
    public void testSearch() throws Exception {
        MsgBatchSearchDto msgBatchSearchDto = new MsgBatchSearchDto();
        msgBatchSearchDto.setBatchNo("3");
        MessagePageResultDto<MsgBatchDto> msglists = msgBatchFacadeServices.search(msgBatchSearchDto);
        assertEquals(msglists.getDataList().size(),3);
    }

}