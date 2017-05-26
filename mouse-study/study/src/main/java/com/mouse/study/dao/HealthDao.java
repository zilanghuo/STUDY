package com.mouse.study.dao;

import com.mouse.study.model.MsgMessage;

import java.util.Map;

/**
 * Created by lwf on 2017/5/23.
 */
public interface HealthDao {

    Integer checkHealth(Map map);

    void insert(MsgMessage msgMessage);
}
