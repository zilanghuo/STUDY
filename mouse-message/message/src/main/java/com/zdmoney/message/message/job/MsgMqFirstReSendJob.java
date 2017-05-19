package com.zdmoney.message.message.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.zdmoney.message.message.model.MsqMqSendFailure;
import com.zdmoney.message.message.service.IMsqMqSendFailureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by rui on 16/9/21.
 */
@Component
public class MsgMqFirstReSendJob extends MsgMqReSendJob {


    @Autowired
    protected IMsqMqSendFailureService msqMqSendFailureService;

    @Override
    public void process(ShardingContext shardingContext) {
        List<MsqMqSendFailure> failures = msqMqSendFailureService.searchFailedMqMsgs(1, 2);
        failures = this.send(failures);
        msqMqSendFailureService.update(failures);
    }
}
