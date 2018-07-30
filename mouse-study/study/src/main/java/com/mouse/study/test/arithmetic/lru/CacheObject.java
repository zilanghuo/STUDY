package com.mouse.study.test.arithmetic.lru;

import lombok.Data;

/**
 * @author lwf
 * @date 2018/7/30
 * use:
 */
@Data
public class CacheObject<K, V> {

    private K key;

    private V value;

    private Integer accessSize;

    private Long lastAccessTime;

    public CacheObject(K key, V value) {
        this.key = key;
        this.value = value;
        this.accessSize = 1;
        this.lastAccessTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "key:" + key + ",value:" + value + ",accessSize:" + accessSize + ",lastAccessTime:" + lastAccessTime;
    }
}
