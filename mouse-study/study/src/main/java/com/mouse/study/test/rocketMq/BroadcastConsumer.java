package com.mouse.study.test.rocketMq;

import com.mouse.study.utils.FileUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * Created by lwf on 2017/7/27.
 * use to do:
 */
public class BroadcastConsumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("BroadcastProducer_group");
        consumer.setNamesrvAddr("172.17.34.136:9876");
        consumer.setInstanceName("BroadcastProducer-test");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //set to broadcast mode
         consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.subscribe("BroadcastProducer_topic", "BroadcastProducer_tag");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                StringBuilder msg = new StringBuilder();
                for (int i = 0; i < msgs.size(); i++) {
                    msg = msg.append(msgs.get(i) + new String(msgs.get(i).getBody()));
                }
                System.out.printf("BroadcastConsumerOne:" + msg + "%n");
                FileUtils.put("BroadcastConsumerOne:" + msg + "%n");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.printf("Broadcast Consumer Started.%n");
    }
}
