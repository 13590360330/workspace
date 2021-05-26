package com.javabasic.dao;

public interface Competitor<T extends Competitor<T,V>,V> {
    V compete(T competitor);
}
