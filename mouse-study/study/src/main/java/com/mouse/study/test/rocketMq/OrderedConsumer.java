package com.mouse.study.test.rocketMq;

import com.mouse.study.utils.FileUtils;
import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by lwf on 2017/7/27.
 * use to do:订阅消息示例代码
 */
@Data
public class OrderedConsumer {


    public static void main(String[] args) throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("OrderedProducer_group");
        consumer.setNamesrvAddr("172.17.34.136:9876");
        consumer.setInstanceName("OrderedProducer-test");

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("OrderedProducer_topic", "TagD");

        consumer.registerMessageListener(new MessageListenerOrderly() {

            AtomicLong consumeTimes = new AtomicLong(0);

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
                return getConsumeOrderlyStatus(msgs, context);

            }

            private ConsumeOrderlyStatus getConsumeOrderlyStatus(List<MessageExt> msgs, ConsumeOrderlyContext context) {

                context.setAutoCommit(false);
                StringBuilder msg = new StringBuilder();
                String body = "";
                for (int i = 0; i < msgs.size(); i++) {
                    body = "--" + new String(msgs.get(i).getBody());
                    msg = msg.append(msgs.get(i) + body);
                }
                String str = Thread.currentThread().getName() + " Receive New Messages: " + msg + "%n";
                System.out.println(this.consumeTimes + body);
                FileUtils.put(this.consumeTimes + str);

                this.consumeTimes.incrementAndGet();
                /*if ((this.consumeTimes.get() % 2) == 0) {
                    return ConsumeOrderlyStatus.SUCCESS;
                } else if ((this.consumeTimes.get() % 3) == 0) {
                    return ConsumeOrderlyStatus.ROLLBACK;
                } else if ((this.consumeTimes.get() % 4) == 0) {
                    return ConsumeOrderlyStatus.COMMIT;
                } else if ((this.consumeTimes.get() % 5) == 0) {
                    context.setSuspendCurrentQueueTimeMillis(3000);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }*/
                if (this.consumeTimes.get() < 10) {
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                } else {
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            }
        });

        consumer.start();
        System.out.printf("Consumer Started.%n");
        Thread.sleep(5000);
    }
}
