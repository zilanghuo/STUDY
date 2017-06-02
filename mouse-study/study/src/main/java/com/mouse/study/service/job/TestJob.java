package com.mouse.study.service.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.job.DefaultJob;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lwf on 2017/6/2.
 */
@Slf4j
public class TestJob extends DefaultJob {

    @Override
    public void process(ShardingContext shardingContext) {
        System.out.println("testJob listener");
        log.info("testJob listener");
    }
}
