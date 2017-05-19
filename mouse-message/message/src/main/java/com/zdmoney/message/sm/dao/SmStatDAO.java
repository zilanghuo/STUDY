package com.zdmoney.message.sm.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.sm.model.SmStat;

import java.util.Map;

/**
 * 短信统计 数据访问对象
 * Created by gaojc on 2016/7/15.
 */
public interface SmStatDAO extends BaseDAO<SmStat> {

    Map<String, Integer> statMonthNumber();
}
