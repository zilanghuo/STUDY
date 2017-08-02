package com.mouse.study.test.rocketMq;

import com.mouse.study.utils.FileUtils;
import com.mouse.study.utils.JackJsonUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * Created by lwf on 2017/7/26.
 * use to do:
 */
public class OrderedProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("OrderedProducer_group");
        producer.setNamesrvAddr("172.17.34.136:9876");
        producer.setInstanceName("OrderedProducer-test");

        producer.start();
        String[] tags = new String[]{"TagD"};
        Message msg = new Message("OrderedProducer_topic", tags[0], "KEY" + 0,
                ("OrderedProducer_topic:testUnConsume-1 ").getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }
        }, 0);
        System.out.printf("%s%n", sendResult);
        FileUtils.put(JackJsonUtil.objToStr(sendResult));
        producer.shutdown();
    }
}
