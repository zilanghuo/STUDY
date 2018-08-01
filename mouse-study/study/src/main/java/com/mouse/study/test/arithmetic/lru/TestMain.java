package com.mouse.study.test.arithmetic.lru;

import com.mouse.study.test.cache.local.LruCache;
import com.mouse.study.utils.JackJsonUtil;

/**
 * @author lwf
 * @date 2018/7/30
 * use:
 */
public class TestMain {

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put("1","1");
        lruCache.put("2","2");
        //访问，变更位置
        lruCache.get("1");
        lruCache.put("3","3");
        System.out.println(lruCache.toString());
    }
}
