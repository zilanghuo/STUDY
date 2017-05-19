package com.zdmoney.message.log.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.log.model.LogData;

import java.util.Map;

/**
 * Created by user on 2017/2/4.
 */
public interface LogDataDAO extends BaseDAO<LogData> {
    Integer removeLogData(Map<String, Object> map);
}
