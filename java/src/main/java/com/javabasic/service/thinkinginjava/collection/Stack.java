package com.javabasic.service.thinkinginjava.collection;

import java.util.LinkedList;

/**
 * TODO  [利用LinkedList实现stack栈的所有功能]
 *
 * @param <T>
 */
public class Stack<T> {
    private LinkedList<T> storage = new LinkedList<T>();

    public void push(T v) {
        storage.addFirst( v );
    }

    public T peek() {
        return storage.getFirst();
    }

    public T pop() {
        return storage.removeFirst();
    }

    public boolean empty() {
        return storage.isEmpty();
    }

    public String toString() {
        return storage.toString();
    }
}

class StackTest {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        for (String s : "My dog has fleas".split( " " ))
            stack.push( s );
        while (!stack.empty())
            System.out.println( stack.pop() + " " );
    }
}