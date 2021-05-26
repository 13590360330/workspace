package com.javabasic.dao;


import java.util.Comparator;

public class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        return String.valueOf(o1).compareTo(String.valueOf(o2));
    }
}
