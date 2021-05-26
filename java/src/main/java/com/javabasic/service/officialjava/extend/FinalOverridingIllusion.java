package com.javabasic.service.officialjava.extend;

/**
 *  TODO 继承 extends
 *  extends 关键字会使子类获取得到父类的所有的字段和方法,相当于子类包含了他初始化的父类对象,
 *      除了向上转型可以访问这个父类对象,其他的方式无法访问到,包括反射
 *
 *  向上转型
 *
 */
public class FinalOverridingIllusion {
    public static void main(String[] args) {
        OverridingPrivate2 op2 = new OverridingPrivate2();
        op2.f();
        op2.g();
        op2.h();
//        op2.k(); //在这里 op2引用对象的k(),是因为private
        System.out.println("----------------------------");

        /*
          向上转型
         */
        OverridingPrivate op = op2;
        /**
         * 1, TODO 如果某个方法是private,它就不是基类的接口的一部分,他仅是一些隐藏于类中的程序代码,导出类中有同名方法视为新的方法,父子类中的同名方法没有影响
         */

         /*
           在这里 op引用无法访问对象的f()方法和g()方法,原因就是private
         */
        op.g();
        ((OverridingPrivate2) op).f();  //强转回OverridingPrivate2类型
         op.h();  //调用WithFinals的方法,自身的h()方法,原因不允许overwrite报错
        System.out.println("----------------------------");

        /*
          同上
         */
        WithFinals wf = op2;
        ((OverridingPrivate2) wf).f();
        ((OverridingPrivate2) wf).g();
        wf.h();
//        wf.k(); //父类无法访问子类特有的方法 除非强转

    }
}

class WithFinals {
    private final void f() {
        System.out.println( "WithFinals.f()" );
    }

    public void g() {
        System.out.println( "WithFinals.g()" );
    }

    protected final void h() {
        System.out.println( "WithFinals.h()" );
    }
}

class OverridingPrivate extends WithFinals {
    private final void f() {
        System.out.println( "OverridingPrivate.f()" );
    }

    public void g() {
        System.out.println( "OverridingPrivate.g()" );
    }

//    protected final void h(){
//        System.out.println("OverridingPrivate.h()");
//    }
}

class OverridingPrivate2 extends OverridingPrivate {
    public final void f() {
        System.out.println( "OverridingPrivate2.f()" );
    }

    public final void g() {
        System.out.println( "OverridingPrivate2.g()" );
    }

    private final void k() {
        System.out.println( "OverridingPrivate2.h()" );
    }
}