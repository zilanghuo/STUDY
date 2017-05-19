package com.zdmoney.message.sm.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.sm.model.SmMarketHistory;
import com.zdmoney.message.sm.model.SmVerifyCodeHistory;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public interface SmVerifyCodeHistoryDAO extends BaseDAO<SmVerifyCodeHistory> {

    void batchInsert(List<SmVerifyCodeHistory> smVerifyCodeHistories);
}
