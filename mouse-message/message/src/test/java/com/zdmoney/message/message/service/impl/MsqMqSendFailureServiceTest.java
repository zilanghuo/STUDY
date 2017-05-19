package com.zdmoney.message.message.service.impl;

import com.zdmoney.message.InitH2TestEnvironment;
import com.zdmoney.message.message.service.IMsqMqSendFailureService;
import com.zdmoney.mq.client.MqByteMessage;
import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.producer.MqProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MsqMqSendFailureServiceTest extends InitH2TestEnvironment {

    @Autowired
    private IMsqMqSendFailureService msqMqSendFailureService;

    @Autowired
    @Qualifier("msgMessageMqProducer")
    private MqProducer mqProducer;

    @Test
    public void testAdd() throws Exception {
        MqMessage message = new MqMessage();
        message.setKey("1234567890");
        message.setData("data");
        msqMqSendFailureService.add(mqProducer, message);
    }

    @Test
    public void testAdd1() throws Exception {
        MqByteMessage message = new MqByteMessage();
        message.setKey("1234567890");
        message.setData("data".getBytes());
        msqMqSendFailureService.add(mqProducer, message);
    }
}