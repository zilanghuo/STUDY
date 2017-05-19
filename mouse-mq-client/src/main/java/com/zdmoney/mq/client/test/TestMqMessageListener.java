package com.zdmoney.mq.client.test;

import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.listener.MqMessageListener;

import java.util.List;

/**
 * Created by rui on 16/8/25.
 */
public class TestMqMessageListener implements MqMessageListener {
    @Override
    public boolean execute(List<MqMessage> mqMessages) {
        for (MqMessage message : mqMessages) {
            System.out.println("==================================message:" + message);
        }
        return true;
    }
}