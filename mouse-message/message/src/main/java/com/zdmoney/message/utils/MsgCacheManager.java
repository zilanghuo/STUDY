package com.zdmoney.message.utils;

/**
 * Created by user on 2017/2/21.
 */
public interface MsgCacheManager {
    String get(String key);

    void remove(String key);

    void put(String key, String value);

}
