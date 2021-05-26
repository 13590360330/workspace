package com.javabasic.service.thinkinginjava.intereface;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.util.Arrays;

/**
 * TODO [完全解耦及策略设计模式]
 * <p>
 * 策略设计模式(接口的常见用法(P182)):创建一个能够根据所传递的参数对象的不同而具有不同行为的方法
 */

interface Processor1 {
    String name();

    Object process(Object input);
}

class Processor implements Processor1 {
    public String name() {
        return getClass().getSimpleName();
    }

    public Object process(Object input) {
        return input;
    }
}

class Upcase extends Processor {
    public String process(Object input) {
        return ((String) input).toUpperCase();
    }
}

class Downcase extends Processor {
    public String process(Object input) {
        return ((String) input).toLowerCase();
    }
}

class Splitter extends Processor {
    public String process(Object input) {
        return Arrays.toString( ((String) input).split( " " ) );
    }
}

public class Apply {
    /**
     * process方法，就是策略,利用Processor对象的多态特性或Processor1接口,达到传递的参数对象的不同而具有不同行为的目的,使用接口,能使适配器模式更具可复用性
     */
    public static void process(Processor1 p, Object s) {
        System.out.println( "Using Processor " + p.name() );
        System.out.println( p.process( s ) );
    }

    public static String s = "hello world";

    public static void main(String[] args) {
        Logs.getLogs( "Apply" );
        process( new Upcase(), s );
        process( new Downcase(), s );
        process( new Splitter(), s );
    }
}
