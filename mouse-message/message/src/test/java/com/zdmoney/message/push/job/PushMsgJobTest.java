package com.zdmoney.message.push.job;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by gaojc on 2016/8/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext.xml")
@Slf4j
public class PushMsgJobTest {
    @Test
    public void testRun() throws InterruptedException {
        log.info("Message project Jobs start execute timerTask...");
        Thread.sleep(10000000L);
    }
}