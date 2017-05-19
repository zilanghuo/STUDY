package com.zdmoney.message.sm.service.impl;

import com.zdmoney.message.common.base.BaseServiceImpl;
import com.zdmoney.message.sm.dao.SmVerifyCodeHistoryDAO;
import com.zdmoney.message.sm.model.SmVerifyCodeHistory;
import com.zdmoney.message.sm.service.ISmVerifyCodeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
@Service
public class SmVerifyCodeHistoryService extends BaseServiceImpl<SmVerifyCodeHistory> implements ISmVerifyCodeHistoryService {

    @Autowired
    private SmVerifyCodeHistoryDAO smVerifyCodeHistoryDAO;

    @Override
    public void batchInsert(List<SmVerifyCodeHistory> smVerifyCodeHistoryList) {
        smVerifyCodeHistoryDAO.batchInsert(smVerifyCodeHistoryList);
    }
}
