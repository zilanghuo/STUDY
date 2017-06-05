package com.mouse.study.service.impl;

import com.mouse.study.dao.HealthDao;
import com.mouse.study.model.MsgMessage;
import com.mouse.study.service.IHealthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lwf on 2017/5/23.
 */
@Service
@Slf4j
public class HealthServiceImpl implements IHealthService {

    @Autowired
    private HealthDao healthDao;

    @Override
    public Integer checkHealth(Map map) {
        return healthDao.checkHealth(map);
    }

    @Override
    public void insert(MsgMessage msgMessage) {
        healthDao.insert(msgMessage);
    }

    @Override
    public void testMotan() {
        log.info("testMotan start!");
    }
}
