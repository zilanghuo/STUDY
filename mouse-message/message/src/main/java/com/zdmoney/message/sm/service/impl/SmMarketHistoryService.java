package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.sm.dao.SmMarketHistoryDAO;
import com.zdmoney.message.sm.dao.SmNotifyHistoryDAO;
import com.zdmoney.message.sm.model.SmMarketHistory;
import com.zdmoney.message.sm.service.ISmHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
@Service
public class SmMarketHistoryService extends BaseServiceImpl<SmMarketHistory> implements ISmHistoryService {

    @Autowired
    private SmMarketHistoryDAO smMarketHistoryDAO;

    @Override
    public void batchInsert(List<SmMarketHistory> smHistoryList) {
        smMarketHistoryDAO.batchInsert(smHistoryList);
    }
}
