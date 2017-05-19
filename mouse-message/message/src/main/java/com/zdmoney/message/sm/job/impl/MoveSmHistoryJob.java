package com.zdmoney.message.sm.job.impl;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.job.DefaultJob;
import com.zdmoney.message.sm.job.IMoveSmHistoryJob;
import com.zdmoney.message.sm.service.ISmMarketMoveService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/4.
 */
@Component
public class MoveSmHistoryJob extends DefaultJob implements IMoveSmHistoryJob {
    @Autowired
    private ISmMarketMoveService smMoveService;

    @Override
    public void move(Date startTime, Date endTime) {
        smMoveService.moveSm(startTime, endTime);
        smMoveService.moveSmVerifyCode(startTime, endTime);
        smMoveService.moveSmNotify(startTime, endTime);
    }

    @Override
    public void process(ShardingContext shardingContext) {
        Date date = new Date();//迁移
        move(DateUtils.addDays(date, -8), DateUtils.addDays(date, -7));
    }
}
