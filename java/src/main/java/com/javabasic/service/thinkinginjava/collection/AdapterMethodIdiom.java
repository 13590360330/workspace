package com.javabasic.service.thinkinginjava.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * TODO [适配器模式 P243,继承ArrayList]
 * <p>
 * 添加一种或多种在foreach语句中使用这个类的方法如下reversed()-反向遍历
 */

class ReversibleArrayList<T> extends ArrayList<T> {
    public ReversibleArrayList(Collection<T> c) {
        super( c );
    }

    public Iterable<T> reversed() {
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    int current = size() - 1;

                    @Override
                    public boolean hasNext() {
                        return current > -1;
                    }

                    @Override
                    public T next() {
                        return get( current-- );
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}

public class AdapterMethodIdiom {
    public static void main(String[] args) {
        ReversibleArrayList<String> ral =
                new ReversibleArrayList<>(
                        Arrays.asList( "To be or not to be".split( " " ) ) );
        for (String s : ral)
            System.out.println( s + " " );
        System.out.println( "------------------------" );
        for (String s : ral.reversed())
            System.out.println( s + " " );
    }
}
