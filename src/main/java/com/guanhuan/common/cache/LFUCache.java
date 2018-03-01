package com.guanhuan.common.cache;

import java.util.*;

/**
 * ����LFU(�����Ƶ��ʹ���㷨)�Ļ���
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
            map.put(i, i);   //����1-10��10������
        }
        System.out.println("1:"+map.toString());//��ӡ��ʼ�洢���

        map.get(7);
        System.out.println("2:"+map.toString());//��ӡ����֮������

        map.put(8, 8+1);  //����һ���Ѵ��ڵ����ݣ�Ҳ��������һ�λ����е�����
        System.out.println("3:"+map.toString());//��ӡ����֮������

        map.put(11, 11); //�ִ��뻺��֮�������
        System.out.println("4:"+map.toString());//��ӡ�ִ洢һ������֮������

        map.put(5, 5); //������������5
        map.put(5, 5);
        map.put(6, 6); //������������6
        map.put(6, 6);
        map.put(6, 6);
        System.out.println("5:"+map.toString());//��ӡ�ִ洢һ������֮������

    }

}
