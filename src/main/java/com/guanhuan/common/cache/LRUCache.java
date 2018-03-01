package com.guanhuan.common.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于LRU(最近使用算法)的缓存
 *
 * @author liguanhuan_a@163.com
 * @since 2018-02-28 16:59
 **/
public class LRUCache<K, V> extends LinkedHashMap<K, V>{

    private final int SIZE;

    public LRUCache(int size){
        super(size, 0.75f, true);
        this.SIZE = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > this.SIZE;
    }

    public static void main(String[] args){
        LRUCache<Integer, Integer> cache = new LRUCache<Integer, Integer>(10);
        for (int i = 0; i < 10; i++) {
            cache.put(i, i);
        }

        System.out.println("before:" + cache.toString());

        cache.put(8, 8);
        System.out.println("after 1:" + cache.toString());

        cache.put(11, 11);
        System.out.println("after 2:" + cache.toString());
    }
}
