package com.zdmoney.mq.client.producer;

import com.zdmoney.mq.client.producer.handler.MqSendFailHandler;

/**
 * Created by rui on 16/9/21.
 */
public class MqSendFailHandlerTask implements Runnable {


    private MqSendFailHandler failHandler;

    public MqSendFailHandlerTask(MqSendFailHandler failHandler) {
        this.failHandler = failHandler;
    }

    @Override
    public void run() {
        failHandler.execute();
    }


    public MqSendFailHandler getFailHandler() {
        return failHandler;
    }

}
