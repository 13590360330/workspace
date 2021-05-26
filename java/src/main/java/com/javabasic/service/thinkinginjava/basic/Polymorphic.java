package com.javabasic.service.thinkinginjava.basic;

/**
 * TODO 多态
 * 父类方法被子类覆盖
 */
public class Polymorphic {

   static class demo1{

       demo1(){func();}

        public void func(){
            System.out.println("11111");
        }
    }

    static class demo2 extends demo1{

        public void func(){
            System.out.println("2222222");
        }
    }

    public static void main(String[] args) {
        demo2 demo2 = new demo2();
        demo1 demo1 = new demo1();
    }
}
