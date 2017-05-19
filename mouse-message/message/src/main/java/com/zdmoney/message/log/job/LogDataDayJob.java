package com.zdmoney.message.log.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.api.dto.log.LogStatRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by user on 2017/2/22.
 */
@Slf4j
@Component
public class LogDataDayJob extends ALogDataJob {

    public void logStat() {
        //每天统计一次
        Date day = DateUtils.plusDays(new Date(), -1);
        Date startTime = DateUtils.parse(DateUtils.getDateBegin(day));
        Date endTime = DateUtils.parse(DateUtils.getDateEnd(day));
        logDataStatService.logStat(startTime, endTime, LogStatRate.DAY);
    }

    @Override
    public void process(ShardingContext shardingContext) {
        logStat();
        logRemove();
    }

    //清理数据
    public void logRemove() {
        String curDateStr = DateUtils.getDateBegin(new Date());
        Date date = DateUtils.parse(curDateStr);
        logDataService.removeLogData(DateUtils.plusDays(date, -rate - 1), DateUtils.plusDays(date, -rate));
    }
}
