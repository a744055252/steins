package com.guanhuan.common.cache;

import java.util.*;

/**
 * 基于LFU(最近最频繁使用算法)的缓存
 *
 * @author liguanhuan_a@163.com
 **/
public class LFUCache<K, V> {

    private final HashMap<K, Value> cache;

    private final int SIZE;

    public LFUCache(int size){
        this.SIZE = size;
        cache = new HashMap<>(size);
    }

    public void put(K key, V value) {
        Value<K, V> value1 = cache.get(key);
        if (value1 == null) {
            if(cache.size() >= SIZE){
                remove();
            }
            cache.put(key,new Value(key, value));
        } else {
            value1.increment();
            value1.setV(value);
        }
    }

    public V get(K key) {
        Value<K, V> value = cache.get(key);
        if (value != null) {
            value.increment();
        }
        return value == null ? null : value.getV();
    }

    private void remove(){
        Value temp = Collections.min(cache.values());
        cache.remove(temp.getK());
    }

    class Value<K, V> implements Comparable<Value>{

        private K k;
        private V v;
        private int count;

        public Value(K k, V v){
            this.k = k;
            this.v = v;
            count = 1;
        }

        public K getK() {
            return k;
        }

        public void setK(K k) {
            this.k = k;
        }

        public V getV() {
            return v;
        }

        public void setV(V v) {
            this.v = v;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int increment(){
            return count++;
        }

        @Override
        public int compareTo(Value o) {
            if (this.getCount() > o.getCount()) {
                return 1;
            } else {
                return -1;
            }
        }

        @Override
        public String toString() {
            return k+"_"+v+"_"+count;
        }
    }

    @Override
    public String toString() {
        ArrayList<Value> list = new ArrayList<>(cache.values());
        Collections.sort(list);
        return list.toString();
    }

    public static void main(String[] args){

        LFUCache<Integer, Integer> map = new LFUCache<>(10);

        for (int i = 0; i++ < 10; ) {
            map.put(i, i);   //放入1-10总10个数据
        }
        System.out.println("1:"+map.toString());//打印起始存储情况

        map.get(7);
        System.out.println("2:"+map.toString());//打印命中之后的情况

        map.put(8, 8+1);  //存入一个已存在的数据，也就是命中一次缓存中的数据
        System.out.println("3:"+map.toString());//打印命中之后的情况

        map.put(11, 11); //又存入缓存之外的数据
        System.out.println("4:"+map.toString());//打印又存储一个数据之后的情况

        map.put(5, 5); //连续命中两次5
        map.put(5, 5);
        map.put(6, 6); //连续命中三次6
        map.put(6, 6);
        map.put(6, 6);
        System.out.println("5:"+map.toString());//打印又存储一个数据之后的情况

    }

}
