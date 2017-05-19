package com.zdmoney.message.message.job;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.zdmoney.job.DefaultJob;
import com.zdmoney.message.message.model.MsqMqSendFailure;
import com.zdmoney.mq.client.config.MqConfig;
import com.zdmoney.mq.client.producer.MqProducerContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * Created by rui on 16/9/21.
 */
@Slf4j
public abstract class MsgMqReSendJob extends DefaultJob {

    protected List<MsqMqSendFailure> send(List<MsqMqSendFailure> failures) {
        log.info("MsgMqReSendJob.send start");
        for (MsqMqSendFailure failure : failures) {
            DefaultMQProducer mqProducer = buildMqProducer(failure);
            Message message = new Message(failure.getMqTopic(), failure.getMqTag(),
                    failure.getMessageId(), failure.getMessageContent().getBytes());
            Integer sendFailTimes = failure.getSendFailTimes();
            failure.setModifyTime(new Date());
            try {
                mqProducer.send(message);
                failure.setSendStatus(1);
            } catch (Exception e) {
                log.error("MsgMqReSendJob send error", e);
                failure.setSendStatus(0);
                failure.setSendFailTimes(sendFailTimes + 1);
            }
        }
        log.info("MsgMqReSendJob.send end");

        return failures;
    }

    private DefaultMQProducer buildMqProducer(MsqMqSendFailure failure) {
        MqConfig mqConfig = new MqConfig(failure.getMqAddress());
        return MqProducerContainer.build(mqConfig, failure.getMqGroup());
    }


}
