package com.mouse.study.facade;

import com.mouse.study.api.facade.ITestFacadeService;
import com.mouse.study.service.IHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lwf on 2017/6/5.
 */
@Component
public class TestFacadeService implements ITestFacadeService {

    @Autowired
    private IHealthService healthService;

    @Override
    public void testMotan() {
        healthService.testMotan();
    }
}
