package com.zdmoney.message.log.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.dto.log.LogStatRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 日志统计
 * Created by user on 2017/2/4.
 */
@Slf4j
@Component
public class LogDataHourJob extends ALogDataJob {
    @Override
    public void process(ShardingContext shardingContext) {
        logStat();
    }

    //日志统计
    public void logStat() {
        //每小时统计一次
        Date endDate = new Date();
        String date = DateUtils.format(endDate, "yyyy-MM-dd HH:00:00");
        endDate = DateUtils.parse(date);
        Date startDate = DateUtils.plusHours(endDate, -1);

        logDataStatService.logStat(startDate, endDate, LogStatRate.HOUR);
    }
}
