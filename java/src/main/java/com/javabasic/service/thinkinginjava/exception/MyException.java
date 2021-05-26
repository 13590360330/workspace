package com.javabasic.service.thinkinginjava.exception;

/**
 * TODO 自定义RuntimeException()   并打印自定义信息
 */
public class MyException {
    public static void getException(Exception e, String s) {
        if (e instanceof Exception) {
            throw new RuntimeException( s ) {
                public String getMessage() {
                    return e + "：{" + super.getMessage() + "}";
                }
            };
        }
    }
}

