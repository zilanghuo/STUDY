package com.mouse.study.test.arithmetic.lru;

import com.mouse.study.utils.JackJsonUtil;

import java.util.LinkedHashMap;

/**
 * @author lwf
 * @date 2018/8/1
 * use:先入先出
 * accessOrder:设置为false
 * removeEldestEntry：重写方法，超过大小不允许添加，默认为false
 */
public class FIFOLinkedHashMapDemo {

    public static void main(String[] args) {
        FIFOLinkedHashMap hashMap = new FIFOLinkedHashMap(3);
        hashMap.put("1", "1");
        hashMap.put("2", "2");
        hashMap.put("3", "3");
        hashMap.get("1");
        hashMap.put("4", "4");
        System.out.println(JackJsonUtil.objToStr(hashMap));
    }

    /**
     * 实现LRU方法的功能,需要重写removeEldestEntry 方法
     */
    static class FIFOLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

        private static final long serialVersionUID = 4504158311663914052L;

        private int maxCacheSize;

        public FIFOLinkedHashMap(int maxCacheSize) {
            super(maxCacheSize, 0.75f, false);
            this.maxCacheSize = maxCacheSize;
        }

        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
            // 当达到预设缓存上限时删除最老元素
            return this.size() >= maxCacheSize + 1;
        }

    }
}