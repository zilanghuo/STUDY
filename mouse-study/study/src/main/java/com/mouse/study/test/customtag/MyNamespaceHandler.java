package com.mouse.study.test.customtag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by lwf on 2017/7/21.
 * use to do:
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
    }
}
