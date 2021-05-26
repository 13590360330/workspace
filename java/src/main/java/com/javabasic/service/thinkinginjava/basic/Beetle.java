package com.javabasic.service.thinkinginjava.basic;

/**
 * TODO [继承与初始化,static字段和构造器的初始化顺序]
 * <p>
 * 对于非静态对象,调用构造函数初始化对象,然后实例化  (java编程思想P77,P94),初始化顺序(P94),任何字段的初始化在所有方法调用之前发生,
 * 静态数据的初始化(P95),非静态数据的初始化(P97)
 */

class Insect {
    private int i = 9;
    protected int j;

    /**
     * 构造器也是static方法,尽管static关键字并没有显式的写出来,因此更准确地讲,类是在其任何static成员被访问时加载的
     */
    Insect() {          //继承结构,会先初始化所有的字段,然后调用构造函数
        System.out.println( "i = " + i + ", j = " + j );
        j = 39;
    }

    private static int x1 = printInit( "Insect" );

    static int printInit(String s) {
        System.out.println( s );
        return 47;
    }
}

public class Beetle extends Insect {
    private int k = printInit( "Beetle.k initialized" );

    public Beetle() {
        System.out.println( " k =" + k );
        System.out.println( " j =" + j );
    }

    private static int x2 = printInit( "Beetle" );

    public static void main(String[] args) {
        System.out.println( "Beetle constructor" );
        Beetle b = new Beetle();
    }
}
