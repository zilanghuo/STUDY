package com.mouse.study.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by gaojc on 2016/7/27.
 */
public class SpringContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        // 在加载Spring时自动获得context
        SpringContextHelper.context = context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

}
