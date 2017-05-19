package com.zdmoney.mq.client.test;

import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.producer.MqProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config-test.xml"})
public class TestMqSender {


    @Resource
    private MqProducer mqProducer;

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            MqMessage message = new MqMessage("test" + i, "test" + i + "===============================");
            mqProducer.send(message);
        }
       Thread.sleep(30000);

        for (int i = 10; i < 11; i++) {
            MqMessage message = new MqMessage("test" + i, "test" + i + "===============================");
            mqProducer.send(message);
        }
    }
}
