package com.zdmoney.mq.client.consumer.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.zdmoney.mq.client.MqByteMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rui on 16/8/25.
 */
@Slf4j
public class MqByteMessageWrapper implements MessageListenerConcurrently {


    private MqByteMessageListener mqByteMessageListener;

    public MqByteMessageWrapper(MqByteMessageListener mqByteMessageListener) {
        this.mqByteMessageListener = mqByteMessageListener;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                    ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        List<MqByteMessage> messages = new ArrayList<>();
        for (MessageExt messageExt : list) {
            log.info("messageWrapper messageExt {}", messageExt.toString());
            MqByteMessage message = new MqByteMessage(messageExt.getKeys(), messageExt.getBody());
            messages.add(message);
        }
        if (mqByteMessageListener.onMessage(messages)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;

    }

    public MqByteMessageListener getMqByteMessageListener() {
        return mqByteMessageListener;
    }
}
