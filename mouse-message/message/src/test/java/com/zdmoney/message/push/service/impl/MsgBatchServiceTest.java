package com.zdmoney.message.push.service.impl;

import com.zdmoney.message.InitDBTestEnvironment;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgBatchDto;
import com.zdmoney.message.api.dto.push.MsgBatchSearchDto;
import com.zdmoney.message.api.dto.push.MsgPushDto;
import com.zdmoney.message.push.model.MsgBatch;
import com.zdmoney.message.push.model.PushResult;
import com.zdmoney.message.push.service.IMsgBatchService;
import com.zdmoney.message.push.service.IPushMsgUpdater;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lwf on 2016/7/21.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext.xml")
public class MsgBatchServiceTest extends InitDBTestEnvironment {

    @Autowired
    private IMsgBatchService msgBatchService;
    @Autowired
    private IPushMsgUpdater pushMsgUpdater;

    @Test
    @Ignore
    public void testGetMsgBatchByNo() throws Exception {
        MsgBatch msgBatch = msgBatchService.getMsgBatchByNo("432213");
        assertEquals(new Integer(1), msgBatch.getIsAllpush());
    }

    @Test
    @Ignore
    public void testSearch() throws Exception {
        MsgBatchSearchDto searchDto = new MsgBatchSearchDto();
        searchDto.setBatchNo("20215458553301");
        searchDto.setPushStartDate(new Date());
        MessagePageResultDto<MsgBatchDto> lists = msgBatchService.search(searchDto);
        assertEquals(lists.getDataList().size(), 2);
    }

    @Test
    public void testAddBatch() {
        MsgPushDto pushDto = new MsgPushDto();
        pushDto.setPushTime(new Date());
        pushDto.setIsInstant(true);
        pushDto.setIsAllpush(true);
        pushDto.setTitle("测试添加");
        pushDto.setContent("测试通知内容");
        String jsonData = "{'type':'1','custId':'001','title':'呵呵','content':'透传内容'}";
        pushDto.setData(jsonData);
        List<String> phoneList = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            phoneList.add("13032926611");
        }
        MsgBatch batch = new MsgBatch("20215458553301", pushDto);

        msgBatchService.insert(batch);
    }

    @Test
    public void testUpdateMsgPhones() {
        PushResult result = new PushResult();
        result.setDetails("{ed828d216b7d020891a123816272ac9c=successed_offline, " +
                "8341732f7b42383cb7c2a0436f8938bb=TokenMD5Error," +
                " 8a61039d09b8cbb61f7ac86bd5043fa3=AppidError," +
                " 1a8d10e87a0dad352734f1bb63252a7f=successed_offline}");

        pushMsgUpdater.updateMsgPhones("2046641312-2899427707", result);
    }

    @Test
    public void testGenerateBatch() {
        MsgPushDto pushDto = new MsgPushDto();
        pushDto.setPushTime(new Date());
        pushDto.setIsInstant(true);
        pushDto.setIsAllpush(false);
        pushDto.setTitle("捞财宝通知");
        pushDto.setContent("恭喜你新到一张%3加息券");
        String jsonData = "{'type':'1','custId':'123','title':'呵呵','content':'透传内容'}";
        pushDto.setData(jsonData);
        List<String> phoneList = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            phoneList.add("18121133990");
            phoneList.add("15121125673");
            phoneList.add("15121125673");
            phoneList.add("15121125673");
            phoneList.add("15121125673");
            phoneList.add("15121125673");
            phoneList.add("15121125673");
            phoneList.add("15121125673");
            phoneList.add("15121125673");
            phoneList.add("15121125673");

            phoneList.add("18650309360");
            phoneList.add("13032926611");

        }
        pushDto.setPhones(phoneList);
        MessageResultDto result = msgBatchService.generate(pushDto);
        assertTrue(result.isSuccess());
        try {
            Thread.sleep(30 * 1000);
        } catch (InterruptedException e) {
            log.error("testGenerateBatch thread sleep timeout", e);
        }
    }
}