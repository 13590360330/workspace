package com.javabasic.service.thinkinginjava.Enum;

import java.util.Random;

/**
 * TODO [随机选取1  P596]
 */
public class Enums {
    private static Random rand = new Random( 47 );

    public static <T extends Enum<T>> T random(Class<T> ec) {
        return random( ec.getEnumConstants() );    //获取ec枚举中的所有实例
    }

    public static <T> T random(T[] values) {
        return values[rand.nextInt( values.length )];
    }
}
