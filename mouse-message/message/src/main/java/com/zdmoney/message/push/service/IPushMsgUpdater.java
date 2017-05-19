package com.zdmoney.message.push.service;

import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.message.push.model.PushResult;

/**
 * Created by gaojc on 2016/8/4.
 */
public interface IPushMsgUpdater {

    void updateMsgPhones(String taskNo, PushResult result);

    void updateMsgPhone(MsgPhone phone, PushResult result);

    void updateMsgTask(MsgTask task, PushResult result);
}
