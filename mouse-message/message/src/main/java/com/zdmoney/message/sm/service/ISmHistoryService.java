package com.zdmoney.message.sm.service;

import com.zdmoney.message.sm.model.SmMarketHistory;
import com.zdmoney.zdqd.service.EntityService;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface ISmHistoryService extends EntityService<SmMarketHistory> {
    void batchInsert(List<SmMarketHistory> smHistoryList);
}
