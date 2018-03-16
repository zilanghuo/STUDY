package com.mouse.study.spring.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author lwf
 * @date 2018/3/15
 * use:
 */
public class TestService implements BeanFactoryAware, ApplicationContextAware, BeanNameAware, InitializingBean, DisposableBean {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("-------------setBeanFactory");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("-------------setBeanName");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("---------------destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-------------------afterPropertiesSet");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("----------------------setApplicationContext");
    }


}
