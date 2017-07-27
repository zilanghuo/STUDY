package com.mouse.study.test.rocketMq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by lwf on 2017/7/27.
 * use to do:
 */
public class OnewayProducer {

    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("OnewayProducer_Group");

        producer.setNamesrvAddr("172.17.34.136:9876");
        producer.setInstanceName("OnewayProducer-test");

        producer.start();
        for (int i = 0; i < 2; i++) {
            Message msg = new Message("OnewayProducer_Test" /* Topic */,
                    "OnewayProducer_Tag" /* Tag */,
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            producer.sendOneway(msg);

        }
        producer.shutdown();
    }
}
