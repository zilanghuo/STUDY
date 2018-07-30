package com.mouse.study.test.arithmetic.lru;

import lombok.Data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lwf
 * @date 2018/7/30
 * use: 先不考虑并发问题
 */
@Data
public class LRUCache<K, V> extends HashMap<K, V> {

    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = cacheLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = cacheLock.writeLock();

    private Map<K, CacheObject<K, V>> cacheMap;

    private Integer size;

    public Integer capacity;

    public LRUCache(Integer size) {
        this.capacity = size;
        this.size = 0;
        cacheMap = new LinkedHashMap<K, CacheObject<K, V>>(size, 1.0f, true) {
            private static final long serialVersionUID = -1806954614512571136L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheObject<K, V>> eldest) {
                //当链表元素大于容量时，移除最老（最久未被使用）的元素
                System.out.println(size() + ":" + capacity);
                return size() >= capacity;
            }
        };
    }


    @Override
    public V put(K key, V value) {
        writeLock.lock();
        if (isFull()) {
            remove();
        }
        cacheMap.put(key, new CacheObject(key, value));
        this.size++;
        V put = super.put(key, value);
        writeLock.unlock();
        return put;
    }

    @Override
    public V get(Object key) {
        readLock.lock();
        V v = super.get(key);
        readLock.unlock();
        return v;
    }

    /**
     * 判断是否包含
     *
     * @return
     */
    public Boolean isFull() {
        if (size >= capacity) {
            return true;
        }
        return false;
    }

    /**
     * 删除最少使用的数据
     */
    public void remove() {
        CacheObject<K, V> object = null;
        //获取最小的访问数
        Iterator<CacheObject<K, V>> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()) {
            CacheObject<K, V> next = iterator.next();
            if (object == null) {
                object = next;
            }
            if (object.getAccessSize() > next.getAccessSize()) {
                object = next;
            }
        }
        //删除最小的访问数
        Iterator<CacheObject<K, V>> cacheObjectIterator = cacheMap.values().iterator();
        while (cacheObjectIterator.hasNext()) {
            CacheObject<K, V> next = cacheObjectIterator.next();
            if (next.getAccessSize() <= object.getAccessSize()) {
                cacheObjectIterator.remove();
                this.size--;
            }
        }
    }

}
