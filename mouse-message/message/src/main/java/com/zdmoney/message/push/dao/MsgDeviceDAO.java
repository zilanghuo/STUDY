package com.zdmoney.message.push.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.push.model.MsgDevice;

/**
 * Created by admin on 2016/7/15.
 */
public interface MsgDeviceDAO extends BaseDAO<MsgDevice> {
    Integer getCount(String deviceType);
}
