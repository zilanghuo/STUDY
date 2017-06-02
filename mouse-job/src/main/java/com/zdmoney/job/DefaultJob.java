package com.zdmoney.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rui on 16/8/29.
 */
@Slf4j
public abstract class DefaultJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        Date startDate = new Date();
        long startTime = startDate.getTime();
        log.info("{} start. ShardingContext:{},startTime:{}", shardingContext.getJobName(),
                shardingContext.toString(), format(startDate));

        this.process(shardingContext);

        Date endDate = new Date();
        long endTime = endDate.getTime();
        log.info("{} end. ShardingContext:{},endTime:{},cost:{}", shardingContext.getJobName(),
                shardingContext.toString(), format(endDate), (endTime - startTime));
    }

    public abstract void process(ShardingContext shardingContext);


    public static String format(Date inputDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDate = null;
        try {
            stringDate = df.format(inputDate);
        } catch (Exception e) {
            log.error("", e);
        }
        return stringDate;
    }


}
