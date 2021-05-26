package com.javabasic.service.thinkinginjava.reflect;


import com.javabasic.dao.Generator;

public class GeneratorsTest {
    public static int size = 10;

    public static void test(Class<?> surroundingClass) {
        //Class对象的getClasses()方法可以获取内部类的Class对象
        for (Class<?> type : surroundingClass.getClasses()) {
//            System.out.println("1:" + type );
            try {
                Generator<?> g = (Generator<?>) type.newInstance();
                for (int i = 0; i < size; i++)
                    System.out.print( g.next() + "," );
                System.out.println();
            } catch (Exception e) {
                throw new RuntimeException( e );
            }
        }
    }

    public static void main(String[] args) {
        test( CountingGenerator.class );
    }
}
