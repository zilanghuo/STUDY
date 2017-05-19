package com.zdmoney.message.log.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.log.model.LogDataStat;
import com.zdmoney.message.log.model.LogStatWrapRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2017/2/4.
 */
public interface LogDataStatDAO extends BaseDAO<LogDataStat> {
    Integer insertLogStat(List<LogDataStat> statLogDatas);

    List<LogStatWrapRecord> getStatLogData(Map<String, Object> map);
}
