package com.guanhuan.common.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于FIFO(先进先出算法)的缓存
 * 注意 继承工具类是一个错误的做法。
 *
 * @author liguanhuan_a@163.com
 * @since 2018-02-28 16:47
 **/
public class FIFOCache<K, V> extends LinkedHashMap<K, V> {

    private final int SIZE;

    public FIFOCache(int size){
        super();
        this.SIZE = size;
    }

    /**
     * 重写淘汰规则
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > SIZE;
    }

    public static void main(String[] args){
        FIFOCache<Integer, Integer> cache = new FIFOCache<Integer, Integer>(10);
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
