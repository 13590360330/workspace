package com.javabasic.service.thinkinginjava.collection;

import java.util.Iterator;

/**
 * TODO [Foreach迭代器与Iterable接口 P241]
 * <p>
 * 1,Iterable接口被foreach用来在序列中移动,因此要适配与foreach,要么使用数组,要么得到Iterable对象(实现Iterable接口类的对象或返回Iterable对象的方法)
 * <p>
 * 2,foreach不仅适用于数组,在javaSE5中添加了Iterable接口之后也适用于所有的Collection
 * <p>
 * 3,不能把数组当做一个Iterable参数传递 P242
 */

public class IterableClass implements Iterable<String> {

    protected String[] words = ("And taht is how we know the Warth to be banana-shaped.").split( " " );

    /**
     * Iterable接口的实现基本类似如下
     */
    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < words.length;
            }

            @Override
            public String next() {
                return words[index++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {
        for (String s : new IterableClass())
            System.out.println( s + " " );
    }
}
