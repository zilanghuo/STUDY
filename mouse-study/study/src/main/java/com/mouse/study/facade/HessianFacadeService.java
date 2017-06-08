package com.mouse.study.facade;

import com.mouse.study.api.facade.IHessianFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by lwf on 2017/6/5.
 */
@Slf4j
@Component
public class HessianFacadeService implements IHessianFacadeService {


    @Override
    public void test() {
        log.info("enter HessianFacadeService test!");
    }
}
