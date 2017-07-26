package com.mouse.study.test.rocketMq;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;

/**
 * Created by lwf on 2017/7/26.
 * use to do:consumer
 */
public class SyncConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name_5");
        consumer.setNamesrvAddr("172.17.34.136:9876");
        consumer.setInstanceName("SyncProducer-test");
        consumer.start();
        try {
            MessageQueue mq = new MessageQueue();
            mq.setQueueId(0);
            mq.setTopic("test_topic");
            mq.setBrokerName("vivedeMacBook-Pro.local");
            long offset = 26;
            long beginTime = System.currentTimeMillis();
            PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, offset, 32);
            System.out.printf("%s%n", System.currentTimeMillis() - beginTime);
            System.out.printf("%s%n", pullResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        consumer.shutdown();
    }
}
