package com.mouse.study.service.impl;

import com.mouse.study.model.MsgMessage;
import com.mouse.study.service.IHealthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lwf on 2017/5/23.
 */
@Service
@Slf4j
public class HealthServiceImpl implements IHealthService {


    @Override
    public Integer checkHealth(Map map) {
        log.info("initialSize:{}","23432");
        return 1;
    }

    @Override
    public void insert(MsgMessage msgMessage) {
        return;
    }

    @Override
    public void testMotan() {
        log.info("testMotan start!");
    }

    @Override
    public MsgMessage get() {
        return null;
    }
}
