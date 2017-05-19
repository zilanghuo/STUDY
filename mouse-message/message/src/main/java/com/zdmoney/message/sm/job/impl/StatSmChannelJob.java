package com.zdmoney.message.sm.job.impl;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.job.DefaultJob;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.sm.job.IStatSmChannelJob;
import com.zdmoney.message.sm.service.ISmStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/4.
 */
@Component
public class StatSmChannelJob extends DefaultJob implements IStatSmChannelJob {

    @Autowired
    private ISmStatService smStatService;

    @Override
    public void process(ShardingContext shardingContext) {
        statSmChannelDay();
    }

    /**
     * 每日统计通道发送短信的数量
     */
    @Override
    public void statSmChannelDay() {
        Date day = DateUtils.plusDays(new Date(), -1);
        String startTime = DateUtils.getDateBegin(day);
        String endTime = DateUtils.getDateEnd(day);
        MessageResultDto messageResultDto = smStatService.statSmChannelDay(startTime, endTime);
        if(messageResultDto.isSuccess()) {
            smStatService.changeBstChannel();
        }
    }

}
