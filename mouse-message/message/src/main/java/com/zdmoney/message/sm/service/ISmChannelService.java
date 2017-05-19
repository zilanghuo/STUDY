package com.zdmoney.message.sm.service;

import com.zdmoney.message.api.common.dto.MessagePageResultDto;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.sm.ChannelDto;
import com.zdmoney.message.api.dto.sm.ChannelReqDto;
import com.zdmoney.message.api.dto.sm.ChannelRspDto;
import com.zdmoney.message.api.dto.sm.ChannelSearchDto;
import com.zdmoney.message.sm.model.SmChannel;
import com.zdmoney.zdqd.service.EntityService;

/**
 * Created by Administrator on 2016/11/7.
 */
public interface ISmChannelService extends EntityService<SmChannel>{

    /**
     * 启用通道
     */
    MessageResultDto<ChannelRspDto> start(ChannelReqDto reqDto);

    /**
     * 停用通道
     */
    MessageResultDto<ChannelRspDto> stop(ChannelReqDto reqDto);

    /**
     * 查询渠道
     * @param reqDto
     * @return
     */
    MessagePageResultDto<ChannelDto> searchChannel(ChannelSearchDto reqDto);


    SmChannel getSmChannelByNo(String channelNo);

    //查询开关开启
    MessageResultDto<ChannelDto> searchStartChannel();
}
