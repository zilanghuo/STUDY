package com.zdmoney.message.sm.service;

import com.zdmoney.message.sm.model.SmVerifyCodeHistory;
import com.zdmoney.zdqd.service.EntityService;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface ISmVerifyCodeHistoryService extends EntityService<SmVerifyCodeHistory> {
    void batchInsert(List<SmVerifyCodeHistory> smVerifyCodeHistoryList);
}
