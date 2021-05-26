package com.javabasic.service.thinkinginjava.reflect;

import java.lang.reflect.Method;

/**
 * TODO 反射获取private和包访问权限
 */
interface A {
    void f();
}

class B implements A {
    public void f() {
    }

    public void g() {
    }
}

class C implements A {
    @Override
    public void f() {
        System.out.println( "f" );
    }

    public void g(String x) {
        System.out.println( "g(" + x + ")" );
    }

    void u() {
        System.out.println( "C.u()" );
    }

    protected void v() {
        System.out.println( "v()" );
    }

    private void w() {
        System.out.println( "C.w()" );
    }
}

public class HiddenC {
    public static A makeA() {
        return new C();
    }
}

class HiddenImplementation {
    public static void main(String[] args) throws Exception {
        A a = HiddenC.makeA();
        a.f();
        System.out.println( a.getClass().getName() );
//        callHiddenMethod( a, "f" );
        callHiddenMethod( a, "g" );
    }

    static void callHiddenMethod(Object a, String methodName) throws Exception {
        //不含参数的 f()
//        Method g = a.getClass().getDeclaredMethod( methodName );
//        g.setAccessible( true );
//        g.invoke( a );
        //含参数的 g(string b)
        Method g = a.getClass().getDeclaredMethod( methodName, String.class );
        g.setAccessible( true );
        Object[] paras = {"b"};
        g.invoke( a, paras );
    }
}