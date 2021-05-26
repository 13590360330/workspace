package com.javabasic.service.thinkinginjava.collection;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.util.*;

/**
 * TODO Collection和Collections的常用方法
 *
 * Collection的功能放法:
 * add(T)
 * addAll(Collection<? extends T>)
 * clear()
 * contains(T)
 * containsAll(Collection<?>)        持有参数集合的所有元素则返回true  c2.retainAll(c3)
 * isEmpty()
 * iterator()
 * remove(Object)
 * removeAll(Collection<?>)
 * retainAll(Collection<?>)
 * size()
 * toArray()
 * toArray(T[] a)  ******转成T类型的数组
 */
public class ListFeatures {
    public static void main(String[] args) {
        Logs.getLogs( "ListFeatures" );
        Random rand = new Random( 47 );
        List<String> pets = new ArrayList<String>( Arrays.asList( "dddd", "aaa", "bbbb", "ccccc", "e", "ffff", "ggg", "hhhhh" ) );
        List<String> sub = pets.subList( 1, 4 );  //从index为1到index为4-1的元素
        System.out.println( "1:" + sub );
        Collections.sort( pets );
        System.out.println( "2:" + pets );
        Collections.shuffle( pets, rand );     //使用指定的随机源rand打乱原来的顺序,rand也可以没有
        System.out.println( "3:" + pets );
        ArrayList<String> pets2 = new ArrayList<>( pets );
        boolean contains = pets2.contains( pets );    //contains指代当前集合是否包含给定元素，containsAll指代当前集合是否包含给定集合所有元素。
        System.out.println( "4:" + contains );
        ArrayList<String> pets3 = new ArrayList<>( pets );
        boolean b = pets3.containsAll( pets );
        System.out.println( "5:" + b );
        ArrayList<String> pets4 = new ArrayList<>( pets );
        pets4.remove( sub );
        System.out.println( "6:" + pets4 );
        pets4.remove( "dddd" );            //remove和contains一样
        System.out.println( "6.1:" + pets4 );
        pets4.clear();
        System.out.println( "7:" + pets4 );
        ArrayList<String> pets5 = new ArrayList<>( pets );
        List<String> sub1 = pets.subList( 1, 5 );  //从index为1到index为4-1的元素
        pets5.removeAll( sub1 );       //同理 contains 和 containsAll
        System.out.println( "8:" + pets5 );
        LinkedList<String> cs = new LinkedList<>();
        Collections.addAll( cs, "Take the long way home".split( " " ) ); //用数组初始化集合
    }
}
