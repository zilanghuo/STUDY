package com.zdmoney.message.push.service;

import com.zdmoney.message.push.model.MsgPhone;
import com.zdmoney.message.push.model.MsgTask;
import com.zdmoney.message.push.model.PushResult;

import java.util.List;

/**
 * Created by gaojc on 2016/8/4.
 */
public interface IPushMsgProvider {

    PushResult pushMessageToSingle(MsgTask msgTask, MsgPhone phone);

    PushResult pushMessageToList(MsgTask msgTask, List<MsgPhone> phones);

    PushResult pushMessageToApp(MsgTask msgTask);

    PushResult statPushResult(String thirdTaskNo);

}