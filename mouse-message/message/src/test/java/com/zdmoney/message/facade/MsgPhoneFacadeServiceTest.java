package com.zdmoney.message.facade;

import com.zdmoney.message.InitH2TestEnvironment;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.dto.push.MsgPhoneDto;
import com.zdmoney.message.api.dto.push.MsgPhoneSearchDto;
import com.zdmoney.message.api.facade.IMsgPhoneFacadeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lwf on 2016/7/26.
 */
public class MsgPhoneFacadeServiceTest extends InitH2TestEnvironment {
    @Autowired
    private IMsgPhoneFacadeService msgPhoneFacadeService;

    @Test
    public void testSearch() throws Exception {
        MsgPhoneSearchDto searchDto = new MsgPhoneSearchDto();
        searchDto.setBatchNo("3");
        MessagePageResultDto<MsgPhoneDto> lists =  msgPhoneFacadeService.search(searchDto);
        assertEquals(lists.getDataList().size(),3);
    }
}