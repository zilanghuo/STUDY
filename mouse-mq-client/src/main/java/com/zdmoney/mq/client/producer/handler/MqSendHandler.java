package com.zdmoney.mq.client.producer.handler;

/**
 * Created by rui on 16/9/22.
 */
public interface MqSendHandler {

    void before();

    void fail();

    void success();

    void complete();
}
