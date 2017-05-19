package com.zdmoney.message.facade;

import com.zdmoney.message.InitH2TestEnvironment;
import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.message.MsgMessageDto;
import com.zdmoney.message.api.dto.message.MsgMessageReadDto;
import com.zdmoney.message.api.dto.message.MsgMessageSearchDto;
import com.zdmoney.message.api.dto.message.MsgMessageSendDto;
import com.zdmoney.message.api.facade.IMsgMessageFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwf on 2016/8/15.
 */

@Slf4j
public class MsgMessageFacadeServiceTest extends InitH2TestEnvironment {
    @Autowired
    private IMsgMessageFacadeService msgMessageFacadeService;

    @Test
    public void testSearch() throws Exception {
        MsgMessageSearchDto searchDto = new MsgMessageSearchDto();
        searchDto.setPageNo(2);
        searchDto.setPageSize(4);
        MessagePageResultDto<MsgMessageDto> resultDto = msgMessageFacadeService.search(searchDto);
        assertTrue(resultDto.isSuccess());
        assertEquals(2, resultDto.getDataList().size());
        List<MsgMessageDto> dataList = resultDto.getDataList();
        assertEquals("题目1", dataList.get(0).getTitle());
    }

    @Test
    public void testSend() throws Exception {
        MsgMessageSendDto sendDto = new MsgMessageSendDto();
        sendDto.setMerchantSerialNo("100000000");
        sendDto.setContent("内容");
        sendDto.setTitle("题目");
        sendDto.setSummary("摘要");
        sendDto.setType(1);
        sendDto.setContent("内容");
        List<MsgMessageSendDto.UserDto> users = new ArrayList<MsgMessageSendDto.UserDto>();
        MsgMessageSendDto.UserDto userOne = new MsgMessageSendDto.UserDto("100", "100", "one", "18525852586");
        MsgMessageSendDto.UserDto userTwo = new MsgMessageSendDto.UserDto("200", "200", "two", "18525852587");
        MsgMessageSendDto.UserDto userThree = new MsgMessageSendDto.UserDto("300", "200", "three", "18525852588");
        users.add(userOne);
        users.add(userTwo);
        users.add(userThree);
        sendDto.setUsers(users);
        MessageResultDto<Boolean> resultDto = msgMessageFacadeService.send(sendDto);
        assertTrue(resultDto.isSuccess());
        Thread.sleep(1000);
        MsgMessageSearchDto searchDto = new MsgMessageSearchDto();
        List<MsgMessageDto> dataList = msgMessageFacadeService.search(searchDto).getDataList();
        assertEquals(9, dataList.size());
        assertEquals("18525852588", dataList.get(0).getUserPhone());
    }

    @Test
    public void testUnReadCount() throws Exception {
        String userId = "202";
        MessageResultDto<Integer> resultDto = msgMessageFacadeService.unReadCount(userId);
        assertTrue(resultDto.isSuccess());
        assertEquals(1, resultDto.getData().intValue());
    }

    @Test
    public void testRead() throws Exception {
        MsgMessageReadDto readDto = new MsgMessageReadDto();
        readDto.setUserNo("200");
        readDto.setUserId("202");
        readDto.setIsAllRead(false);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        readDto.setIds(ids);
        MessageResultDto<Boolean> resultDto = msgMessageFacadeService.read(readDto);
        assertTrue(resultDto.isSuccess());
        Integer count = msgMessageFacadeService.unReadCount(readDto.getUserId()).getData();
        assertEquals(1, count.intValue());
    }

    @Test
    public void testGetByIds() throws Exception {
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(15);
        ids.add(17);
        MessageResultDto<List<MsgMessageDto>> resultDto = msgMessageFacadeService.getByIds(ids);
        assertTrue(resultDto.isSuccess());
        assertEquals(2, resultDto.getData().size());
        assertEquals("18652523625", resultDto.getData().get(0).getUserPhone());
        assertEquals("xiaoming3", resultDto.getData().get(1).getUserName());
    }
}