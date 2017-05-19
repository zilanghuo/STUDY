package com.zdmoney.mq.client.consumer.listener;

import com.zdmoney.mq.client.MqMessage;

import java.util.List;

/**
 * Created by rui on 16/8/25.
 */
public interface MqMessageListener {
    boolean execute(List<MqMessage> mqMessages);
}
