package com.mouse.study.service;

import com.mouse.study.model.MsgMessage;

import java.util.Map;

/**
 * Created by lwf on 2017/5/23.
 */
public interface IHealthService {

    /**
     * 健康检查
     * @return
     */
    Integer checkHealth(Map map);

    /**
     * 插入
     */
    void insert(MsgMessage msgMessage);

    /**
     * 测试motan方法
     */
    void testMotan();

    MsgMessage get();
}
