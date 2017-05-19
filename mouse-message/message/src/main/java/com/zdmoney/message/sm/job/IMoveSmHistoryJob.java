package com.zdmoney.message.sm.job;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/4.
 */
public interface IMoveSmHistoryJob {

    void move(Date startTime, Date endTime);
}
