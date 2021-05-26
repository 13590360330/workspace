package com.javabasic.service.thinkinginjava.RTTI;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO 动态代理2  P339
 */
public class DynamicProxyHandler implements InvocationHandler {
    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println( "**** proxy: " + proxy.getClass() + ", method: " + method + ", args: " + args );
        if (args != null)
            for (Object arg : args)
                System.out.println( "args[]:" + arg );
        return method.invoke( proxied, args );   //返回调用被代理对象相应方法的结果
    }
}

class SimpleDynamicProxy {
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse( "bonobo" );
    }

    public static void main(String[] args) {
        Logs.getLogs( "SimpleDynamicProxy", true );
        System.out.println( "-----------RealObject------------" );
        RealObject real = new RealObject();
        consumer( real );
        System.out.println( "-----------Proxy------------" );
        /**创建动态代理*/
        Interface proxy = (Interface) Proxy.newProxyInstance(
                Interface.class.getClassLoader(),            //类加载器
                new Class[]{Interface.class},                //相应Class对象的数组
                new DynamicProxyHandler( real ) );           //InvocationHandler接口的实现类
        consumer( proxy );   //这边已经走的是动态代理了,当method.invoke( proxied, args )中args和consumer中的参数匹配时,会返回method.invoke( proxied, args )中的结果
    }
}
