package com.zdmoney.message.sm.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.sm.model.SmChannel;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/7.
 */
public interface SmChannelDAO extends BaseDAO<SmChannel> {

    int updateStatus(Map map);
}
