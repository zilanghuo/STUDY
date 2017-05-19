package com.zdmoney.message.facade;

import com.zdmoney.message.InitDBTestEnvironment;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.log.LogDataReqDto;
import com.zdmoney.message.api.dto.log.LogDataRspDto;
import com.zdmoney.message.api.dto.log.LogDataSearchDto;
import com.zdmoney.message.api.facade.ILogFacadeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by user on 2017/2/6.
 */
public class LogFacadeServiceTest extends InitDBTestEnvironment {
    @Autowired
    private ILogFacadeService logFacadeService;

    @Test
    public void testStorageLog() throws Exception {
        Date startTime =new Date();
//        DateUtils.plusSeconds(date, -1);
        Thread.sleep(123);
        /*for(int i=0;i<50000;i++){
            System.out.println("================================="+i);
        }*/
        Date date = new Date();

        LogDataReqDto reqDto = new LogDataReqDto("Laocaibao测试系统", "LianLianWebPay@得测试", startTime, date,"成功了");
        MessageResultDto<LogDataRspDto> resultDto = logFacadeService.accessLog(reqDto);

        assertTrue(resultDto.isSuccess());

        Thread.sleep(200*1000L);
    }

    @Test
    public void testSearch() throws Exception {
        LogDataSearchDto searchDto=new LogDataSearchDto();
        searchDto.setStartTime(new Date());
        searchDto.setSystemName("Laocaibao");
        logFacadeService.search(searchDto);
    }

    @Test
    public void testSearchStat() throws Exception {
    }

    @Test
    public void testOn() throws Exception {
        logFacadeService.on();
    }

    @Test
    public void testOff() throws Exception {
        logFacadeService.off();
    }
}