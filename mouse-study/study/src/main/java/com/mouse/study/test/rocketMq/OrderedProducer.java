package com.mouse.study.test.rocketMq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by lwf on 2017/7/26.
 * use to do:
 */
public class OrderedProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("OrderedProducer_group");
        //producer.setNamesrvAddr("172.17.34.136:9876");
        producer.setNamesrvAddr("172.17.35.1:9876;172.17.35.2:9876;172.17.35.7:9876;172.17.35.8:9876");
        producer.setInstanceName("OrderedProducer-test");

        producer.start();
        String[] tags = new String[]{"TagD"};
        Message msg = new Message("OrderedProducer_topic", tags[0], "KEY" + 0,
                ("OrderedProducer_topic:helloword-09 ").getBytes(RemotingHelper.DEFAULT_CHARSET));
        producer.sendOneway(msg);
       /* SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer id = (Integer) arg;
                int index = id % mqs.size();
                return mqs.get(index);
            }
        }, 0);*/
     //   System.out.printf("%s%n", sendResult);
       // FileUtils.put(JackJsonUtil.objToStr(sendResult));
        producer.shutdown();
    }
}
