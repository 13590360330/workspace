package com.javabasic.service.thinkinginjava.basic;

/**
 * TODO [单例模式]
 */

/**一般类*/
class Soup1 {
    private static int count = 1;

    private final  int no = count++;

    private Soup1() {
    }

    public int getNo() {
        return no;
    }

    /**
     * 外界不能new Soup1(),如果不设成static,则外界无法访问makeSoup(),达不到创建Soup1()的效果
     */
    public static Soup1 makeSoup() {
        return new Soup1();
    }
}

/**
 * 单例类
 */
class Soup2 {
    private Soup2() {
    }

    private static Soup2 ps1 = new Soup2();

    public static Soup2 access() {
        return ps1;
    }

    public void f() {
    }
}

public class Lunch {
    void testPrivate() {
    }

    void testStatic() {
        Soup1 soup1 = Soup1.makeSoup();
    }

    void testSingleton() {
        Soup2.access().f();
    }
}
