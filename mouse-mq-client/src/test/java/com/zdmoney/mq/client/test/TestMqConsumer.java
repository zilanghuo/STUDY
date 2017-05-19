package com.zdmoney.mq.client.test;

import com.zdmoney.mq.client.MqMessage;
import com.zdmoney.mq.client.consumer.MqConsumer;
import com.zdmoney.mq.client.producer.MqProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config-test-consumer.xml"})
public class TestMqConsumer {


    @Resource
    private MqConsumer mqConsumer;

    @Test
    public void test() throws InterruptedException {
        Thread.sleep(10000000000L);
    }
}
