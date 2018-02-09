package com.guanhuan.test;

/**
 * √∂æŸ¿‡≤‚ ‘
 *
 * @author liguanhuan_a@163.com
 * @create 2018-02-08 9:32
 **/
public enum Print {

    PRINT_TEST_1 {
        public void print(String str) {
            System.out.println("TEST1:" + str);
        }
    },
    PRINT_TEST_2 {
        public void print(String str) {
            System.out.println("TEST2:" + str);
        }
    };

    public abstract void print(String str);
}
