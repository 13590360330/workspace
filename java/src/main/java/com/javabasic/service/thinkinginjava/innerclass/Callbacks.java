package com.javabasic.service.thinkinginjava.innerclass;

import com.javabasic.service.thinkinginjava.io.Logs;

/**
 * TODO [1.闭包与回调 P205]
 * TODO [2.钩子hook P207]
 * TODO [3.this与.new P193]
 * <p>
 * 需要生成对外部类对象的引用--外部类名.this
 * 创建内部类对象,需要有外部类(DotNew)对象引用,然后如下(内部类Inner)
 * DotNew dn=new DotNew();
 * DotNew.Inner dni=dn.new Inner()
 * <p>
 * 闭包的定义。
 * 1,是一个可调用的对象，它记录了一些信息，这些信息来自于创建它的作用域。-- <<Java编程思想>>
 */

interface Incrementable {
    void increment();
}

class Callee1 implements Incrementable {

    private int i = 0;

    @Override
    public void increment() {
        i++;
        System.out.println( i );
    }
}

class MyIncrement {
    /**
     * 由于这个方法的存在,使得Callee2要想实现Incrementable的increment()方法,必须借助内部类
     */
    public void increment() {
        System.out.println( "Other operation" );
    }

    static void f(MyIncrement mi) {
        mi.increment();
    }
}

class Callee2 extends MyIncrement {
    private int i = 0;

    public void increment() {
        super.increment();
        i++;
        System.out.println( i );
    }

    private class Closure implements Incrementable {

        @Override
        public void increment() {
            Callee2.this.increment();   //钩子hook ,通过这个钩子可以访问外部类对象的所有成员
        }
    }

    Incrementable getCallbackReference() {
        return new Closure();
    }
}

class Caller {
    private Incrementable callbackReference;

    Caller(Incrementable cbh) {
        callbackReference = cbh;
    }

    void go() {
        callbackReference.increment();
    }
}

public class Callbacks {
    public static void main(String[] args) {
        Logs.getLogs( "Callbacks" );
        Callee1 c1 = new Callee1();
        Callee2 c2 = new Callee2();
        MyIncrement.f( c2 );
        /**外部类实现的接口*/
        Caller caller1 = new Caller( c1 );
        /**内部类实现的接口*/
        Caller caller2 = new Caller( c2.getCallbackReference() );
        caller1.go();
        caller1.go();
        caller2.go();
        caller2.go();
    }
}
