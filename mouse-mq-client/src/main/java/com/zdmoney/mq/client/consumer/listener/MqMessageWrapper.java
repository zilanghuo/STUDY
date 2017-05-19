package com.zdmoney.mq.client.consumer.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.zdmoney.mq.client.MqMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rui on 16/8/25.
 */
@Slf4j
public class MqMessageWrapper implements MessageListenerConcurrently {


    private MqMessageListener mqMessageListener;

    public MqMessageWrapper(MqMessageListener mqMessageListener) {
        this.mqMessageListener = mqMessageListener;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                    ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        List<MqMessage> messages = new ArrayList<>();
        for (MessageExt messageExt : list) {
            log.info("messageWrapper messageExt {}", messageExt.toString());
            MqMessage message = new MqMessage(messageExt.getKeys(), new String(messageExt.getBody()));
            messages.add(message);
        }
        if (mqMessageListener.execute(messages)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;

    }

    public MqMessageListener getMqMessageListener() {
        return mqMessageListener;
    }
}
