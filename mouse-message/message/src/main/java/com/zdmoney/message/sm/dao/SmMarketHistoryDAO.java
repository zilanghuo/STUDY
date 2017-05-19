package com.zdmoney.message.sm.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.sm.model.SmMarket;
import com.zdmoney.message.sm.model.SmMarketHistory;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public interface SmMarketHistoryDAO extends BaseDAO<SmMarketHistory> {

    void batchInsert(List<SmMarketHistory> smMarketHistories);
}
