package com.mouse.study.test.rocketMq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by lwf on 2017/7/27.
 * use to do:
 */
public class BroadcastProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("BroadcastProducer_group");
        producer.setNamesrvAddr("172.17.34.136:9876");
        producer.setInstanceName("BroadcastProducer-test");
        producer.start();

        for (int i = 0; i < 2; i++) {
            Message msg = new Message("BroadcastProducer_topic",
                    "BroadcastProducer_tag",
                    "BroadcastProducer_001",
                    "BroadcastProducer:Hello world03".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }

}
