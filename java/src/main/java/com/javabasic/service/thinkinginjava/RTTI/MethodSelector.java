package com.javabasic.service.thinkinginjava.RTTI;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO 动态代理3
 */
public class MethodSelector implements InvocationHandler {

    private Object proxied;

    public MethodSelector(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals( "interesting" )) {
            System.out.println( "Proxy detected the interesting method" );
            return method.invoke( proxied, args );
        }
        return null;
    }
}

interface SomeMethods {
    void boring1();

    void boring2();

    void interesting(String arg);

    void boring3();
}

class Implementation implements SomeMethods {

    @Override
    public void boring1() {
        System.out.println( "boring1" );
    }

    @Override
    public void boring2() {
        System.out.println( "boring2" );
    }

    @Override
    public void interesting(String arg) {
        System.out.println( "interesting " + arg );
    }

    @Override
    public void boring3() {
        System.out.println( "boring3" );
    }
}

class SelectingMethods {
    public static void main(String[] args) {
        Logs.getLogs( "SelectingMethods", true );
        /**
         * 动态代理,可以做到通过传入的参数,来控制方法的调用
         */
        SomeMethods proxy = (SomeMethods) Proxy.newProxyInstance(
                SomeMethods.class.getClassLoader(),
                new Class[]{SomeMethods.class},
                new MethodSelector( new Implementation() ) );
        proxy.boring1();
        proxy.boring2();
        proxy.interesting( "bonobo" );
        proxy.boring3();
    }
}