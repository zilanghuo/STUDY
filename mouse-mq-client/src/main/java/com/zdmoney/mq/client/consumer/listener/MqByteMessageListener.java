package com.zdmoney.mq.client.consumer.listener;

import com.zdmoney.mq.client.MqByteMessage;

import java.util.List;

/**
 * Created by rui on 16/8/25.
 */
public interface MqByteMessageListener {
    boolean onMessage(List<MqByteMessage> byteMessages);
}
