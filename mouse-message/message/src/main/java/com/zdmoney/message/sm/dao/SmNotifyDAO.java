package com.zdmoney.message.sm.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.sm.model.SmNotify;
import com.zdmoney.message.sm.model.StatRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by gaojc on 2016/11/12.
 */
public interface SmNotifyDAO extends BaseDAO<SmNotify> {

    List<StatRecord> statSmNotify(Map<String, Object> map);

    Integer batchDelete(List<SmNotify> smNotifies);

    Integer updateSmNotify(SmNotify smNotify);
}
