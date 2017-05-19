package com.zdmoney.message.push.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.push.model.MsgTask;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2016/7/15.
 */
public interface MsgTaskDAO extends BaseDAO<MsgTask> {

    List<MsgTask> getNeedPushTasks(Date pushTime);

    List<String> getPushedBatchNos(Date pushTime);
}
