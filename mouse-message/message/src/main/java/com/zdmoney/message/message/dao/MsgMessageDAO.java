package com.zdmoney.message.message.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.message.model.MsgMessage;
import com.zdmoney.message.message.model.UnReadRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by lwf on 2016/8/15.
 */
public interface MsgMessageDAO extends BaseDAO<MsgMessage> {
    /**
     * 根据IDS  得到列表
     *
     * @param ids
     * @return
     */
    List<MsgMessage> getByIds(List<Integer> ids);

    /**
     * 批量插入
     *
     * @param messages
     */
    void inserts(List<MsgMessage> messages);

    /**
     * 批量更新
     *
     * @param messages
     */
    void batchUpdate(Map<String, Object> messages);

    /**
     * 根据userId获取未读数量
     * @param uerIds
     * @return
     */
    List<UnReadRecord> unReadCounts(List<String> uerIds);

    /**
     * 健康检查
     */
    Integer checkHealth();
}
