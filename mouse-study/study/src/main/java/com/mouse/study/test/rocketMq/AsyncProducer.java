package com.mouse.study.test.rocketMq;

import com.mouse.study.utils.FileUtils;
import com.mouse.study.utils.JackJsonUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by lwf on 2017/7/26.
 * use to do:
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("test_group");
        producer.setNamesrvAddr("172.17.34.136:9876");
        producer.setInstanceName("AsyncProducer-test");

        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        for (int i = 0; i < 2; i++) {
            try {
                final int index = i;
                Message msg = new Message("AsyncProducer_topic",
                        "AsyncProducer_tag",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult)  {
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                        try {
                            FileUtils.put(JackJsonUtil.objToStr(sendResult));
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(10000);
        producer.shutdown();
    }
}
