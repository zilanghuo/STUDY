package com.mouse.study.service.impl;

import com.mouse.study.dao.HealthDao;
import com.mouse.study.service.IHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lwf on 2017/5/23.
 */
@Service
public class HealthServiceImpl implements IHealthService {

    @Autowired
    private HealthDao healthDao;

    @Override
    public Integer checkHealth() {
        return healthDao.checkHealth();
    }
}