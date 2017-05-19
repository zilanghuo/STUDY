package com.zdmoney.mq.client.producer;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.zdmoney.mq.client.config.MqConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rui on 16/9/21.
 */
@Slf4j
public class MqProducerContainer {

    private static ConcurrentHashMap<String, DefaultMQProducer> producerConcurrentHashMap = new ConcurrentHashMap<>();


    public static DefaultMQProducer build(MqConfig mqConfig, String group) {
        String key = buildKey(mqConfig, group);
        DefaultMQProducer producer = null;
        if (!producerConcurrentHashMap.containsKey(key)) {
            try {
                producer = new DefaultMQProducer(group);
                producerConcurrentHashMap.put(key, producer);
                producer.setNamesrvAddr(mqConfig.getAddress());
                producer.start();
            } catch (Exception e) {
                log.error("MqProducerContainer build error", e);
            }
        } else {
            producer = producerConcurrentHashMap.get(key);
        }
        return producer;

    }

    private static String buildKey(MqConfig mqConfig, String group) {
        return mqConfig.getAddress() + "-" + group;
    }


}
