package com.zdmoney.message.sm.job;

import java.util.Date;

/**
 * Created by gaojc on 2016/11/22.
 */
public interface ISendSmJob {
    void sendTimedShortMsg(Date date);
}
