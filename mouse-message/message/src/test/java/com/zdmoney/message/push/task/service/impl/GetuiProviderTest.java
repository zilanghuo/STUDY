package com.zdmoney.message.push.task.service.impl;

import com.gexin.rp.sdk.http.IGtPush;
import com.zdmoney.message.InitDBTestEnvironment;
import com.zdmoney.message.push.config.GetuiConfig;
import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.message.push.service.IMsgPhoneService;
import com.zdmoney.message.push.service.IMsgTaskService;
import com.zdmoney.message.push.service.IPushMsgProvider;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gaojc on 2016/8/10.
 */
public class GetuiProviderTest extends InitDBTestEnvironment implements InitializingBean {
    @Autowired
    private IPushMsgProvider provider;

    @Autowired
    private IMsgTaskService msgTaskService;

    @Autowired
    private IMsgPhoneService msgPhoneService;

    @Autowired
    private GetuiConfig getuiConfig;

    //离线有效时间，单位为毫秒
    private static final long TIME_MILL = 24 * 3600 * 1000L;

    private IGtPush push = null;
    private String appId = null;
    private String appKey = null;

    @Test
    public void testPushMessageToSingle() throws Exception {
        List<MsgTask> msgTasks = msgTaskService.getByBatchNo("2046641312");
        MsgTask task = msgTasks.get(0);

        List<MsgPhone> phones = msgPhoneService.getByTaskNo(msgTasks.get(0).getTaskNo());
        provider.pushMessageToSingle(task,null);


    }

    @Test
    public void testPushMessageToList() throws Exception {

    }

    @Test
    public void testPushMessageToApp() throws Exception {

    }

    @Test
    public void testStatPushResult() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        appId = getuiConfig.getAppId();
        appKey = getuiConfig.getAppKey();

        String masterSecret = getuiConfig.getMasterSecret();
        String host = getuiConfig.getHost();

        push = new IGtPush(host, appKey, masterSecret);
    }
}