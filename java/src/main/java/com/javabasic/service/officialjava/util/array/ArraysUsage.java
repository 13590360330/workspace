package com.javabasic.service.officialjava.util.array;

import java.util.Arrays;

/**
 * TODO 数组
 * Arrays类提供了对数组操作有用的方法,这些方法有助于连接集合和数组
 */
public class ArraysUsage {

    public static void main(String[] args) {
        /**
         * 1.Arrays类包含的方法
         * asList(T ....array)    List
         * binarySearch()         int           使用二分搜索法查找特定数值,只能为排过序的数组应用这个方法
         * copyOf()
         * equals()
         * deepEquals(Object[] a,Object[] b)
         *                        boolean       用于比较两个可能包含嵌套数组的数组是否相等
         * fill()                 void          使用指定的值填充数组,是填充,不修改已有的数据
         * sort()
         * parallelSort()
         * spliterator()
         * setAll                               为所有元素赋值
         * parallelSetAll                               为所有元素赋值
         *
         * 2.Arrays.toString()返回数组类容字符串,可方便打印
         */

        /**
         * 数组的创建是在运行时刻进行的；数字元素中的基本数据类型会自动初始化为空值(\u0000)。（对于数字和字符，就是0(\u0000)；对于布尔类型，是false）
         */
        int[] a1=new int[10];
        char[] a2=new char[10];
        boolean[] a3=new boolean[10];
        System.out.println( Arrays.toString(a1));
        System.out.println(Arrays.toString(a2));
        System.out.println(Arrays.toString(a3));
        System.out.println(new Boolean( "0") );

        Integer[][] a4 ={{1,2,3},{4,5,6}};
        System.out.println(Arrays.deepToString( a4 ));
    }
}
