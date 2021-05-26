package com.javabasic.service.thinkinginjava.reflect;


/**
 * TODO newInstance() 使用newInstance()来创建的类,不能有含参构造器,无参和默认都可以
 */
public class Concatenation {

    public Concatenation() {
        System.out.println( "aaaaa" );
    }

    public Concatenation(boolean col) {
        System.out.println( col );
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<Concatenation> concatenationClass = Concatenation.class;
        concatenationClass.newInstance();
    }
}
