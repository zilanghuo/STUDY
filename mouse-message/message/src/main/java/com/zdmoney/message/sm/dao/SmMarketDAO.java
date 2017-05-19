package com.zdmoney.message.sm.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.sm.model.SmMarket;
import com.zdmoney.message.sm.model.StatRecord;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信 数据访问对象
 * Created by gaojc on 2016/7/15.
 */
public interface SmMarketDAO extends BaseDAO<SmMarket> {

    List<SmMarket> getListBySmReqDto(Map<String, Object> map);

    void batchInsert(List<SmMarket> smMarkets);

    Integer batchUpdate(Date date);

    List<SmMarket> getWaitSendSms(Date date);

    List<StatRecord> statSmMarket(Map<String, Object> map);

    Integer batchUpdateRetMsg(HashMap<String, Object> map);

    Integer updateSingleRetMsg(HashMap<String, Object> map);

    Integer batchDelete(List<SmMarket> smMarkets);
}
