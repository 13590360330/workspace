package com.javabasic.service.thinkinginjava.annotations.database;

public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = true);
}
