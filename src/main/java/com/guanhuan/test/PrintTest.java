package com.guanhuan.test;

import java.lang.reflect.Constructor;

/**
 * @author liguanhuan_a@163.com
 * @create 2018-02-08 9:35
 **/
public class PrintTest {
    public static void main(String[] args) throws Exception {
//        Print.PRINT_TEST_1.print("Œ“");
//        Print.PRINT_TEST_2.print("Œ“");
        Constructor<?> constructor = Class.forName("Test")
                .getConstructor(new Class[]{Integer.class});
        Test test = (Test) constructor.newInstance(1);
        test.print();
    }

    class Test{
        int i, j;
        Object o;
        public Test(int i){
            this.i = i;
            System.out.println(i);
        }

        public Test(int i, int j){
            this.i = i;
            this.j = j;
            System.out.println(""+i+"_"+j);
        }

        public Test(int i, Object o){
            this.i = i;
            this.o = o;
            System.out.println(""+i+"_"+o);
        }

        public void print(){
            System.out.println("ok!_"+i+"_"+j+"_"+o);
        }

    }

}
