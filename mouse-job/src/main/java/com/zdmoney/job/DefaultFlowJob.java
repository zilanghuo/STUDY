package com.zdmoney.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 16/8/30.
 */
@Slf4j
public abstract class DefaultFlowJob<T> implements DataflowJob<T> {
    @Override
    public List<T> fetchData(ShardingContext shardingContext) {
        Date startDate = new Date();
        long startTime = startDate.getTime();
        log.info("{} fetchData start. ShardingContext:{},startTime:{}", shardingContext.getJobName(),
                shardingContext.toString(), format(startDate));

        List<T> data = this.fetch(shardingContext);

        Date endDate = new Date();
        long endTime = endDate.getTime();
        log.info("{} fetchData end. ShardingContext:{},endTime:{},cost:{}", shardingContext.getJobName(),
                shardingContext.toString(), format(endDate), (endTime - startTime));
        return data;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<T> data) {

        Date startDate = new Date();
        long startTime = startDate.getTime();
        log.info("{} processData start. ShardingContext:{},startTime:{}", shardingContext.getJobName(),
                shardingContext.toString(), format(startDate));

        this.process(shardingContext, data);

        Date endDate = new Date();
        long endTime = endDate.getTime();
        log.info("{} processData end. ShardingContext:{},endTime:{},cost:{}", shardingContext.getJobName(),
                shardingContext.toString(), format(endDate), (endTime - startTime));
    }


    public abstract List<T> fetch(ShardingContext shardingContext);

    public abstract void process(ShardingContext shardingContext, List<T> data);


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
