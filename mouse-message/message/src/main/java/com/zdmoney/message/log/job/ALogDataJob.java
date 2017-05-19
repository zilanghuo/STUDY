package com.zdmoney.message.log.job;

import com.zdmoney.job.DefaultJob;
import com.zdmoney.message.log.service.ILogDataService;
import com.zdmoney.message.log.service.ILogDataStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by user on 2017/2/22.
 */

public abstract class ALogDataJob extends DefaultJob{
    @Autowired
    protected ILogDataService logDataService;
    @Autowired
    protected ILogDataStatService logDataStatService;
    @Value("${log.access.remove.rate}")
    protected int rate;

}
