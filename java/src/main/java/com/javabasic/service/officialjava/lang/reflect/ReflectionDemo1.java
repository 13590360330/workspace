package com.javabasic.service.officialjava.lang.reflect;

import com.javabasic.service.officialjava.lang.LangUsage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * TODO 反射
 * 反射是软件分析自身的能力,这个功能是由java.lang.reflect包和Class中的元素提供的,反射是重要的功能,特别是调用了java bean时,
 * 通过反射可以在运行时动态的分析软件组件,并描述组件的功能,例如,类的方法,构造函数,域变量
 *
 */
public class ReflectionDemo1 {
    /**
     * 首先使用Class类的forName()方法来获取一个类对象
     * @param args
     */
    public static void main(String[] args) {
            LangUsage langUsage = new LangUsage();
            Class<? extends LangUsage> aClass = langUsage.getClass();
//            Class<?> aClass = Class.forName( "com.java.com.service.javacore.lang.LangUsage" );
            Constructor<?> constructors[] = aClass.getConstructors();
            Arrays.stream(constructors).forEach( System.out::println );
            Field[] fields = aClass.getFields();
            Arrays.stream( fields ).forEach( System.out::println );
            Method[] methods = aClass.getMethods();
            Arrays.stream( methods ).forEach( System.out::println );
    }
}
