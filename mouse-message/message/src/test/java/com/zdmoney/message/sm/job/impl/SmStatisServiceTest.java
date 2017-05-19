package com.zdmoney.message.sm.job.impl;

import com.zdmoney.message.InitDBTestEnvironment;
import com.zdmoney.message.api.utils.DateUtils;
import com.zdmoney.message.sm.job.IMoveSmHistoryJob;
import com.zdmoney.message.sm.job.IStart253StopBstJob;
import com.zdmoney.message.sm.job.IStatSmChannelJob;
import com.zdmoney.message.sm.service.ISmStatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by gaojc on 2016/11/16.
 */
@Slf4j
public class SmStatisServiceTest extends InitDBTestEnvironment {

    @Autowired
    private IStatSmChannelJob statSmChannelJob;

    @Autowired
    private IStart253StopBstJob start253StopBstJob;

    @Autowired
    private IMoveSmHistoryJob moveSmHistoryJob;

    @Test
    public void testStatSmChannelDay() throws Exception {
        Date day = DateUtils.plusDays(new Date(), 0);
        String startTime = DateUtils.getDateBegin(day);
        String endTime = DateUtils.getDateEnd(day);
        statSmChannelJob.statSmChannelDay();
    }

    @Test
    public void start253StopBstJob() throws Exception {
        start253StopBstJob.start253Job();
    }

    @Test
    public void moveSmHistoryJob() throws Exception {
        Date day = DateUtils.plusDays(new Date(), 0);
        String startTime = DateUtils.getDateBegin(day);
        String endTime = DateUtils.getDateEnd(day);
        moveSmHistoryJob.move(DateUtils.parse(startTime), DateUtils.parse(endTime));
    }
}