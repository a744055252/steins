package com.guanhuan.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于转换中文与数字
 *
 * @author liguanhuan_a@163.com
 * @create 2017-11-23 11:02
 **/
public class NumberUtil {

    private static final Map<Character, Long> unitNumber = new HashMap<Character, Long>();

    static {
       unitNumber.put('个', 1L);
       unitNumber.put('十', 10L);
       unitNumber.put('百', 100L);
       unitNumber.put('千', 1000L);
       unitNumber.put('万', 10000L);
       unitNumber.put('亿', 100000000L);
    }

    /**
     * 根据单位获取倍数
     * @Date: 11:09 2017/11/23
     * @param unitStr
     */
    public static long getNumber(String unitStr){
        char[] units = unitStr.toCharArray();
        long multiple = 1;
        for(char unit : units){
            if(!unitNumber.containsKey(unit)){
                throw new RuntimeException("NumberUtil不支持这样的单位："+unit);
            }
            multiple *= unitNumber.get(unit);
        }
        return multiple;
    }

    public static double toNumber(String strNumber){
        int min = strNumber.length();
        String num = null;
        String unit = null;
        for(Map.Entry<Character, Long> entry : unitNumber.entrySet()){
            int temp = strNumber.indexOf(entry.getKey());
            if(temp == -1)
                continue;
            min = min > temp ? temp : min;
        }
        num = strNumber.substring(0, min);
        unit = strNumber.substring(min, strNumber.length());
        double number = Double.parseDouble(num);
        long unitNumber = getNumber(unit);
        return number * unitNumber;
    }

    public static void main(String[] args){
        System.out.println((long)toNumber("1.2千万"));
    }
}
