package com.zdmoney.message.sm.service;

import com.zdmoney.message.sm.model.SmNotifyHistory;
import com.zdmoney.zdqd.service.EntityService;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface ISmNotifyHistoryService extends EntityService<SmNotifyHistory> {
    void batchInsert(List<SmNotifyHistory> smNotifyHistories);
}
