package com.zdmoney.message.log.job;

import com.zdmoney.message.InitDBTestEnvironment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by user on 2017/2/6.
 */
public class LogDataJobTest extends InitDBTestEnvironment {
    @Autowired
    private LogDataHourJob logDataHourJob;

    @Autowired
    private LogDataDayJob logDataDayJob;


    @Test
    public void testProcess() throws Exception {
        logDataHourJob.logStat();
        logDataDayJob.logStat();
        logDataDayJob.logRemove();
    }
}