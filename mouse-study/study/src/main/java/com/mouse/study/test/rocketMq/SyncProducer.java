package com.mouse.study.test.rocketMq;

import com.mouse.study.utils.FileUtils;
import com.mouse.study.utils.JackJsonUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by lwf on 2017/7/26.
 * use to do:
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new
                DefaultMQProducer("test_group");
        producer.setNamesrvAddr("172.17.34.136:9876");
        producer.setInstanceName("SyncProducer-test");
        producer.start();
        Message msg = new Message("test_topic", "test_tag",
                ("Hello RocketMQ " ).getBytes(RemotingHelper.DEFAULT_CHARSET)
        );
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);
        FileUtils.put(JackJsonUtil.objToStr(sendResult));
        producer.shutdown();
    }
}
