package com.zdmoney.message.message.service.impl;

import com.zdmoney.message.message.dao.MsgMessageDAO;
import com.zdmoney.message.message.service.IHealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lwf on 2017/5/17.
 */
@Service
public class HealthCheckServiceImpl implements IHealthCheckService {

    @Autowired
    private MsgMessageDAO msgMessageDAO;

    @Override
    public Integer checkHealth() {
        return msgMessageDAO.checkHealth();
    }
}
