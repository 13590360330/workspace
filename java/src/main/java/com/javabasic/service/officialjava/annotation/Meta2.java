package com.javabasic.service.officialjava.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

@What(description = "An annotation test method")
@MyAnno(str = "Meta2", val = 100)
public class Meta2 {
    /**
     * 获取注解数组
     */
    @What(description = "An annotation test method")
    @MyAnno(str = "Annotation Example", val = 100)
    public static void myMeth2() {
        try {
            //获取类的注解
            Meta2 ob = new Meta2();
            Annotation annos[] = ob.getClass().getAnnotations();
            System.out.println( "all annotations for Meta2" );
            for (Annotation a : annos)
                //Annotation是所有注解接口的超接口,并且它重写了Object类中的toString()方法
                System.out.println( a );
            //获取方法的注解
            Method m = ob.getClass().getMethod( "myMeth2" );
            annos = m.getAnnotations();
            System.out.println( "all annotations for myMeth2" );
            Arrays.stream( annos ).forEach( x -> System.out.println( x ) );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        myMeth2();
    }
}