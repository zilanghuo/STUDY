package com.zdmoney.message.sm.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.sm.model.SmMarket;
import com.zdmoney.message.sm.model.SmNotifyHistory;

import java.util.List;

/**
 * Created by gaojc on 2016/11/12.
 */
public interface SmNotifyHistoryDAO extends BaseDAO<SmNotifyHistory> {
    void batchInsert(List<SmNotifyHistory> smNotifyHistories);
}
