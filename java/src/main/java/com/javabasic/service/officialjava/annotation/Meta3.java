package com.javabasic.service.officialjava.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/**
 * 默认注解
 */
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno2 {
    String str() default "Testing";

    int val() default 9000;
}

public class Meta3 {
    /**
     * 使用默认注解时,下面4种使用方式都是可以的
     *
     * @MyAnno2()
     * @MyAnno2(str = "Annotation Example")
     * @MyAnno2(val = 100)
     * @MyAnno2(str = "Annotation Example", val = 100)
     */
    @MyAnno2()
    public static void myMeth() {
        try {
            Meta3 ob = new Meta3();
            Class<?> c = ob.getClass();
            Method m = c.getMethod( "myMeth" );
            MyAnno2 anno = m.getAnnotation( MyAnno2.class ); //anno是一个MyAnno类型的Class对象,即注解,这种结构被称为"类字面值"
            System.out.println( anno.str() + "," + anno.val() );
        } catch (NoSuchMethodException e) {
            System.out.println( "Method Not Found" );
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        myMeth();
    }
}
