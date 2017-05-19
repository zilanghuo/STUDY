package com.zdmoney.message.push.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.job.DefaultJob;
import com.zdmoney.message.push.service.IMsgTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by gaojc on 2016/8/5.
 */
@Component
@Slf4j
public class StatPushResultJob extends DefaultJob {
    @Autowired
    private IMsgTaskService msgTaskService;

    @Override
    public void process(ShardingContext shardingContext) {
        msgTaskService.statPushResult();
    }
}
