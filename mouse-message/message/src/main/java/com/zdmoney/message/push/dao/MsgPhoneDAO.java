package com.zdmoney.message.push.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.push.model.MsgPhone;

import java.util.List;

/**
 * Created by gaojc on 2016/7/15.
 */
public interface MsgPhoneDAO extends BaseDAO<MsgPhone> {
    List<MsgPhone> getNeedPushTasks();

    void batchInsert(List<MsgPhone> t);
}
