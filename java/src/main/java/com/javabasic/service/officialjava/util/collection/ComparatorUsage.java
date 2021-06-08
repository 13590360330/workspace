package com.javabasic.service.officialjava.util.collection;

import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * "自然顺序"  abcdefg........,12345678......,等
 * 比较器精确的定义了"有序顺序"的含义
 *
 * 1,interface Comparator<T> 这个接口是一个函数是接口,在注解@FuctionalInterface的javadoc中如下说明：
 *     a.函数式接口,有且仅有一个抽象方法，但是可以有多个非抽象方法的接口
 *     b.default方法某默认实现，不属于抽象方法
 *     c.接口重写了Object的公共方法也不算入内（签名相同）
 *     所以，Comparator虽然有两个抽象方法：int compare(T o1, T o2);boolean equals(Object obj);其中 equals为Object的方法，不算入内,所以Comparator可以作为函数式接口。
 */
public class ComparatorUsage implements java.util.Comparator {

    /**
     * 2,自定义排序compare(Object o1, Object o2){return o1.toString().compareTo( o2.toString() )}
     * o1=o2  return 0
     * o1>o2  return 正数
     * o1<o2  return 负数
     * o1与o2类型不兼容 ClassCastException
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Object o1, Object o2) {
        //自然顺序的倒序,作用同reversed()
        return o2.toString().compareTo( o1.toString() );
    }

    /**
     * 3,倒序
     * @return
     */
    @Override
    public java.util.Comparator reversed() {
        return null;
    }

    /**
     * JDK8 新添加thenComparing,先比较....,再比较,   例子见P550和P551的实现  ******
     * @param other
     * @return
     */
    @Override
    public java.util.Comparator thenComparing(java.util.Comparator other) {
        return null;
    }

    @Override
    public java.util.Comparator thenComparingInt(ToIntFunction keyExtractor) {
        return null;
    }

    @Override
    public java.util.Comparator thenComparingLong(ToLongFunction keyExtractor) {
        return null;
    }

    @Override
    public java.util.Comparator thenComparingDouble(ToDoubleFunction keyExtractor) {
        return null;
    }

    @Override
    public java.util.Comparator thenComparing(Function keyExtractor) {
        return null;
    }

    @Override
    public java.util.Comparator thenComparing(Function keyExtractor, java.util.Comparator keyComparator) {
        return null;
    }
}

class CompDemo{
    public static void main(String[] args) {
//        TreeSet<String> strings = new TreeSet<String>( new Comparator() );
        TreeSet<String> strings = new TreeSet<String>( (a,b)->b.compareTo( a ));

        strings.add( "a" );
        strings.add( "c" );
        strings.add( "f" );
        strings.add( "z" );
        strings.add( "g" );
        strings.add( "b" );
        strings.add( "e" );
        strings.stream().forEach( x-> System.out.println(x) );



    }
}