package com.javabasic.service.thinkinginjava.Enum;

import com.javabasic.service.thinkinginjava.io.Logs;
import com.javabasic.service.thinkinginjava.io.OSExecute;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

/**
 * TODO [values()的神秘之处 P595]
 */
public class Reflection {
    public static Set<String> analyze(Class<?> enumClass) {
        System.out.println( "------ Analyzing " + enumClass + " ------" );
        System.out.println( "Interfaces:" );
        for (Type t : enumClass.getGenericInterfaces()) {
            System.out.println( t );
        }
        System.out.println( "Base: " + enumClass.getSuperclass() );
        System.out.println( "Methods: " );
        Set<String> methods = new TreeSet<String>();
        for (Method m : enumClass.getMethods()) {
            methods.add( m.getName() );  //m.getName() 只存方法的string名
        }
        System.out.println( methods );
        return methods;
    }

    public static void main(String[] args) {
        Logs.getLogs("Reflection");
        Set<String> exploreMethods = analyze( Explore.class );  //对于具体的枚举类,会多了一个values()方法,是由编译器添加的static方法,编译器还会添加一个valueOf()方法,和下面的valueOf()不同
        Set<String> enumMethods = analyze( Enum.class );
        System.out.println("Explore.containsAll(Enum)? " +
                exploreMethods.containsAll( enumMethods ));
        System.out.println("Explore.removeAll(Enum):");
        exploreMethods.removeAll( enumMethods );
        System.out.println(exploreMethods);
        String commandstring = "javac -encoding UTF-8 " +
                "E:\\日常文件\\知识点整理\\CDH\\DW\\project\\中信银行\\BigData\\src\\main\\com\\java\\com.service\\Thinking_In_Java\\Enum\\Explore.java";
        OSExecute.command( commandstring );
        String commandstring2 = "javap -encoding UTF-8 " +
                "E:\\日常文件\\知识点整理\\CDH\\DW\\project\\中信银行\\BigData\\src\\main\\com\\java\\com.service\\Thinking_In_Java\\Enum\\Explore.class";
        OSExecute.command( commandstring2 );


    }

}
