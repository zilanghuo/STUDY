package com.zdmoney.message.message.cache.impl;

import com.zdmoney.message.api.dto.message.MsgMessageStatus;
import com.zdmoney.message.message.cache.CacheBean;
import com.zdmoney.message.message.cache.IMsgCacheCountService;
import com.zdmoney.message.message.dao.MsgMessageDAO;
import com.zdmoney.message.message.model.MsgMessage;
import com.zdmoney.message.message.model.UnReadRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */
@Service
@Slf4j
public class MsgCacheCountService implements IMsgCacheCountService {

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, CacheBean> hashOperations;

    private ThreadLocal<HashOperations<String, String, CacheBean>> threadLocal = new ThreadLocal() {
        @Override
        protected HashOperations<String, String, CacheBean> initialValue() {
            return hashOperations;
        }
    };

    @Autowired
    private MsgMessageDAO msgMessageDAO;

    private static final String MESSAGE_CACHE_PREFIX = "MESSAGE_";

    /**
     * 批量+1
     *
     * @param ids
     */
    @Override
    public void batchAddCount(List<String> ids) {
        List<UnReadRecord> counts = msgMessageDAO.unReadCounts(ids);
        for (UnReadRecord record : counts) {
            CacheBean cacheBean = new CacheBean(record.getCKey(), record.getCValue());
            log.info("MsgCacheCountService batchAddCount key: {} before {}", cacheBean.getKey(), cacheBean.getValue());
            hashOperations.put(MESSAGE_CACHE_PREFIX, record.getCKey(), cacheBean);
            log.info("MsgCacheCountService batchAddCount key: {} after {}", cacheBean.getKey(), cacheBean.getValue());
        }
    }

    /**
     * 批量-size
     *
     * @param ids
     * @param size
     */
    @Override
    public void batchReduceCount(List<String> ids, int size) {
        List<CacheBean> cacheBeanList = hashOperations.multiGet(MESSAGE_CACHE_PREFIX, ids);
        if (CollectionUtils.isEmpty(cacheBeanList)) {
            return;
        }
        for (CacheBean cacheBean : cacheBeanList) {
            log.info("MsgCacheCountService batchReduceCount {} before {}", cacheBean.getKey(), cacheBean.getValue());
            CacheBean cacheBean1 = new CacheBean(cacheBean.getKey(), cacheBean.getValue() - size);
            hashOperations.put(MESSAGE_CACHE_PREFIX, cacheBean1.getKey(), cacheBean1);
            log.info("MsgCacheCountService batchReduceCount {} after {}", cacheBean1.getKey(), cacheBean1.getValue());
        }
    }

    @Override
    public Integer getOneCacheValue(String userId) {
        log.info("MsgCacheCountService getOneCacheValue before key: {}", userId);
        if (!hashOperations.hasKey(MESSAGE_CACHE_PREFIX, userId)) {
            MsgMessage message = new MsgMessage(userId, MsgMessageStatus.UN_READ);
            hashOperations.put(MESSAGE_CACHE_PREFIX, userId,
                    new CacheBean(userId, msgMessageDAO.countGet(message)));
        }
        Integer value = hashOperations.get(MESSAGE_CACHE_PREFIX, userId).getValue();
        log.info("MsgCacheCountService getOneCacheValue after key: {},value {}", userId, value);
        return value;
    }

    @Override
    public List<Integer> getBatchValue(List<String> ids) {
        List<CacheBean> cacheBeanList = hashOperations.multiGet(MESSAGE_CACHE_PREFIX, ids);
        List<Integer> integerList = new ArrayList<>(cacheBeanList.size());
        for (CacheBean cacheBean : cacheBeanList) {
            integerList.add(cacheBean.getValue());
        }
        return integerList;
    }

}
