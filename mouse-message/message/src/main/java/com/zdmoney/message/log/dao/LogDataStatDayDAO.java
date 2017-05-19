package com.zdmoney.message.log.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.log.model.LogDataStatDay;

import java.util.List;

/**
 * Created by user on 2017/2/23.
 */
public interface LogDataStatDayDAO extends BaseDAO<LogDataStatDay> {
    Integer insertLogStatDay(List<LogDataStatDay> statDayList);
}
