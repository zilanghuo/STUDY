package com.zdmoney.message.message.cache;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */
public interface IMsgCacheCountService {

    //批量+1
    void batchAddCount(List<String> ids);

    //批量-1
    void batchReduceCount(List<String> ids, int size);

    Integer getOneCacheValue(String userId);

    List<Integer> getBatchValue(List<String> ids);

}
