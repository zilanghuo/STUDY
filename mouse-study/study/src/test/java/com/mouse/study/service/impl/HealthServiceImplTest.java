package com.mouse.study.service.impl;

import com.mouse.study.JUnit4ClassRunner;
import com.mouse.study.service.IHealthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lwf on 2017/8/24.
 * use to do:
 */
@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testAppApplication-h2.xml")
@Transactional
public class HealthServiceImplTest {

    @Autowired
    private IHealthService healthService;

    @Test
    public void checkHealth() throws Exception {
        Map map = new HashMap();
        map.put("tableName","t_msg_message");
        healthService.checkHealth(map);

    }

}