package com.zdmoney.mq.client.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.zdmoney.mq.client.config.MqConfig;
import com.zdmoney.mq.client.config.MqConfigException;
import com.zdmoney.mq.client.consumer.listener.MqByteMessageListener;
import com.zdmoney.mq.client.consumer.listener.MqByteMessageWrapper;
import com.zdmoney.mq.client.group.MqGroup;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by rui on 16/8/24.
 */

@Slf4j
@Data
public class MqByteConsumer implements InitializingBean, DisposableBean {

    private MqConfig mqConfig;

    private String topic;

    private String tag;

    private String group;

    private DefaultMQPushConsumer consumer;

    private MqByteMessageListener mqByteMessageListener;


    public void init() {
        log.info("init {} ...", this.getClass().getName());
        this.validate();
        try {

            consumer = new DefaultMQPushConsumer();
            consumer.setConsumerGroup(group);
            consumer.setNamesrvAddr(mqConfig.getAddress());
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe(topic, tag);
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MqByteMessageWrapper(this.mqByteMessageListener));
            consumer.start();
            log.info("init {} success", this.getClass().getName());
        } catch (Exception e) {
            log.error("init {} error", this.getClass().getName(), e);
        }
    }

    private void validate() {
        if (this.mqConfig == null) {
            throw new MqConfigException("not set mqConfig for consumer");
        }

        if (this.mqConfig.getAddress() == null) {
            throw new MqConfigException("not set address for consumer");
        }

        if (this.topic == null) {
            throw new MqConfigException("not set topic for consumer");
        }

        if (this.tag == null) {
            throw new MqConfigException("not set tag for consumer");
        }

        if (this.group == null) {
            throw new MqConfigException("not set group for consumer");
        }

        if (this.mqByteMessageListener == null) {
            throw new MqConfigException("not set mqByteMessageListener for consumer");
        }
        MqGroup.validate(group, topic, tag);

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    @Override
    public void destroy() throws Exception {
        consumer.shutdown();
    }
}
