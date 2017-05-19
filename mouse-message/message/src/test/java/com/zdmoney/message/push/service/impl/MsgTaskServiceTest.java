package com.zdmoney.message.push.service.impl;

import com.zdmoney.message.InitDBTestEnvironment;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgTaskDto;
import com.zdmoney.message.api.dto.push.MsgTaskSearchDto;
import com.zdmoney.message.push.service.IMsgBatchService;
import com.zdmoney.message.push.service.IMsgTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by lwf on 2016/7/22.
 */
@Slf4j
public class MsgTaskServiceTest extends InitDBTestEnvironment {

    @Autowired
    private IMsgTaskService msgTaskService;
    @Autowired
    private IMsgBatchService msgBatchService;

    @Test
    @Ignore
    public void testSearch() throws Exception {
        MsgTaskSearchDto searchDto = new MsgTaskSearchDto();
        searchDto.setBatchNo("00");
        searchDto.setPushStartDate(new Date());
        MessagePageResultDto<MsgTaskDto> lists = msgTaskService.search(searchDto);
        assertEquals(lists.getDataList().size(), 2);
    }

    @Test
    @Ignore
    public void testSearchByMap() throws Exception {
        MsgTaskSearchDto searchDto = new MsgTaskSearchDto();
        searchDto.setPageNo(0);
        searchDto.setPageSize(100);
        MessagePageResultDto<MsgTaskDto> lists = msgTaskService.search(searchDto);
        assertEquals(lists.getDataList().size(), 3);
    }

    @Test
    //即时推送
    public void testExecuteTask() throws Exception {
        MessageResultDto resultDto = msgTaskService.executeTask("8205607960");

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("testExecuteTask.resultDto=" + resultDto);
        assertTrue(resultDto.isSuccess());
    }

    //定时推送
    @Test
    public void testExecuteTimerTask() throws Exception {
        MessageResultDto resultDto = msgTaskService.executeTimerTask();

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("testExecuteTimerTask.resultDto=" + resultDto);
        assertTrue(resultDto.isSuccess());
    }

    //统计推送结果
    @Test
    public void testStatPushResult() throws Exception {
        msgTaskService.statPushResult();

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}