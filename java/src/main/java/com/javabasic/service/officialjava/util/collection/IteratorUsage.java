package com.javabasic.service.officialjava.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * TODO 迭代器
 * 迭代器是实现了Iterator或者ListIterator接口的对象
 * Iterator接口允许遍历集合,获取或移除元素
 */
public class IteratorUsage implements java.util.Iterator {

    public static void main(String[] args) {
        /**
         * 1,ListIterator接口扩展了Iterator接口,允许双向遍历列表,并且允许修改元素
         */
        ArrayList<String> strings = new ArrayList<String>(Arrays.asList( "a", "b", "c", "d" ));
        ListIterator<String> stringListIterator = strings.listIterator();
        while (stringListIterator.hasNext()){
            String next = stringListIterator.next();
            stringListIterator.set( next +"+" );
        };
        while (stringListIterator.hasPrevious()){
            String previous = stringListIterator.previous();
            System.out.println(previous);
        }

        /**
         * 2,for-each循环替代迭代器
         * 3,Spliterator 并行迭代 P530
         */


    }


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public void forEachRemaining(Consumer action) {

    }
}
