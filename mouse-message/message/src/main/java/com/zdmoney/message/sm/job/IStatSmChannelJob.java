package com.zdmoney.message.sm.job;

/**
 * Created by Administrator on 2016/11/3.
 */
public interface IStatSmChannelJob {

    /**
     * 每天晚上统计当月各个通道的发送数量，
     * 253短信数量发送大于55万（每月最大可发送数量）的时候，
     * 停用232并且启用博士通（定时任务）
     */
    void statSmChannelDay();

}
