package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.sm.dao.SmNotifyHistoryDAO;
import com.zdmoney.message.sm.model.SmNotifyHistory;
import com.zdmoney.message.sm.service.ISmNotifyHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gaojc on 2016/11/12.
 */
@Service
public class SmNotifyHistoryService extends BaseServiceImpl<SmNotifyHistory> implements ISmNotifyHistoryService {
    @Autowired
    private SmNotifyHistoryDAO smNotifyHistoryDAO;

    @Override
    public void batchInsert(List<SmNotifyHistory> smNotifyHistories) {
        smNotifyHistoryDAO.batchInsert(smNotifyHistories);
    }
}
