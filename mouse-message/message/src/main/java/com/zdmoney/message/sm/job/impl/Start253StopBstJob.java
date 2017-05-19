package com.zdmoney.message.sm.job.impl;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.job.DefaultJob;
import com.zdmoney.message.api.dto.sm.ChannelReqDto;
import com.zdmoney.message.api.dto.sm.SmChannelType;
import com.zdmoney.message.sm.job.IStart253StopBstJob;
import com.zdmoney.message.sm.service.ISmChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/4.
 */
@Component
public class Start253StopBstJob extends DefaultJob implements IStart253StopBstJob {

    @Autowired
    private ISmChannelService smChannelService;

    public void start253() {
        ChannelReqDto channelReqDto = new ChannelReqDto(SmChannelType.BLUE.getNo(), true);
        smChannelService.start(channelReqDto);
    }

    @Override
    public void start253Job() {
        start253();
    }

    @Override
    public void process(ShardingContext shardingContext) {
        start253Job();
    }
}
