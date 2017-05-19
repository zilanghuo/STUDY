package com.zdmoney.message.sm.job.impl;

import com.zdmoney.message.InitDBTestEnvironment;
import com.zdmoney.message.sm.job.ISendSmJob;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/30.
 */
public class SmJobTest extends InitDBTestEnvironment {
    @Autowired
    private ISendSmJob sendSmJob;

    @Test
    public void testSendJob() throws InterruptedException {
        sendSmJob.sendTimedShortMsg(new Date());
        Thread.sleep(1000000000L);
    }
}
