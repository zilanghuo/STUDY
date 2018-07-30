package com.mouse.study.test.arithmetic.lru;

import com.mouse.study.utils.JackJsonUtil;

import java.util.LinkedHashMap;

/**
 * @author lwf
 * @date 2018/7/30
 * use:least recently user(LinkedHashMap的实现方式)
 */
public class LRULinkedHashMapDemo {

    public static void main(String[] args) {
        LRULinkedHashMap hashMap = new LRULinkedHashMap(3);
        hashMap.put("1", "1");
        hashMap.put("2", "2");
        hashMap.put("3", "3");
        hashMap.put("4", "4");
        System.out.println(JackJsonUtil.objToStr(hashMap));
    }
}

/**
 * 实现LRU方法的功能,需要重写removeEldestEntry 方法
 */
class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 4504158311663914052L;

    private int maxCacheSize;

    public LRULinkedHashMap(int maxCacheSize) {
        super(maxCacheSize, 0.75f, true);
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        // 当达到预设缓存上限时删除最老元素
        return this.size() >= maxCacheSize + 1;
    }

}
