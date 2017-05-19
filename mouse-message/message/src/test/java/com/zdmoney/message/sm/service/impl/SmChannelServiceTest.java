package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.InitDBTestEnvironment;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.ChannelReqDto;
import com.zdmoney.message.api.dto.sm.ChannelRspDto;
import com.zdmoney.message.sm.service.ISmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by gaojc on 2016/11/16.
 */
@Slf4j
public class SmChannelServiceTest extends InitDBTestEnvironment{

    @Autowired
    private ISmChannelService smChannelService;

    @Test
    public void testStart() throws Exception {
        ChannelReqDto reqDto=new ChannelReqDto("BST");
        MessageResultDto<ChannelRspDto> channelRspDtoMessageResultDto = smChannelService.start(reqDto);
        assertTrue(channelRspDtoMessageResultDto.isSuccess());
    }

    @Test
    public void testStop() throws Exception {
        ChannelReqDto reqDto=new ChannelReqDto("BST_1");
        MessageResultDto<ChannelRspDto> channelRspDtoMessageResultDto = smChannelService.stop(reqDto);
        assertTrue(channelRspDtoMessageResultDto.isSuccess());
        //assertFalse(channelRspDtoMessageResultDto.isSuccess());
    }

    @Test
    public void testSearchChannel() throws Exception {

    }

    @Test
    public void testGetSmChannelByNo() throws Exception {

    }

    @Test
    public void testSearchStartChannel() throws Exception {

    }
}