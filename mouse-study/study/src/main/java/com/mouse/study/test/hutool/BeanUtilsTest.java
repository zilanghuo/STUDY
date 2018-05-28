package com.mouse.study.test.hutool;

import cn.hutool.core.bean.BeanUtil;
import com.mouse.study.test.java8.reflect.CollUtils;

/**
 * @author lwf
 * @date 2018/5/25
 * use:
 */
public class BeanUtilsTest {

    @org.junit.Test
    public void isBean(){
        System.out.println(BeanUtil.isBean(CollUtils.class));

    }
}
