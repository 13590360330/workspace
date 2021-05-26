package com.javabasic.service.officialjava.extend;

import java.util.Arrays;

/**
 * 多态与静态方法
 * 4.静态方法不存在于对象之中,无法通过对象访问,也不具备多态性,父子类的同名静态方法,没有关系
 */
public class StaticPolymorphism {
    public static void main(String[] args) {
        StaticSuper staticSub = new StaticSub();
        System.out.println( staticSub.dynamicGet() );
        //静态方法与多态
        System.out.println( StaticSuper.staticGet() );
        System.out.println( StaticSub.staticGet() );

        StaticSub staticSub1 = new StaticSub();
        System.out.println( System.identityHashCode( staticSub1 ) ); //地址
        System.out.println( staticSub1.getClass().getSimpleName() ); //类型
        Arrays.stream( staticSub1.getClass().getMethods() ).forEach(System.out::println);
        System.out.println("----------------------------------------------------------");

        StaticSuper a = staticSub1;
        System.out.println( System.identityHashCode( a ) );
        System.out.println( a.getClass().getSimpleName() );
        Arrays.stream( a.getClass().getMethods() ).forEach(System.out::println);
    }
}


class StaticSuper {
    public static String staticGet() {
        return "Base staticGet()";
    }

    public String dynamicGet() {
        return "Base dynamicGet()";
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    protected void dispose() {
    }
}

class StaticSub extends StaticSuper {
    public static String staticGet() {
        return "Derived staticGet()";
    }

    public String dynamicGet() {
        return "Derived dynamicGet()";
    }
}