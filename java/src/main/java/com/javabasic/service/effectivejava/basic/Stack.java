package com.javabasic.service.effectivejava.basic;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * TODO [内存泄露 P23]
 */
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        /**内存泄露的第2种情况,因为elements[size]这一步如果不置为null,引用将一直存在,那么内存就得不到释放*/
        elements[size] = null;
        return result;
    }

    /**
     * 当elements的长度为0时,会调用这个方法,将elements指向的长度为1的新数组,copyof()是浅拷贝
     */
    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf( elements, 2 * size + 1 );
    }
}
