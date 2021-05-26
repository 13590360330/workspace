package com.javabasic.service.thinkinginjava.intereface;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Random;
import java.util.Scanner;

/**
 * TODO [适配接口,Readable接口]
 * <p>
 * 接口常见的用法就是策略设计模式,使得方法更灵活,通用,并更具有可复用性 P182
 */

class RandomDoubles {
    private static Random random = new Random();

    public double next() {
        return random.nextDouble();
    }

    public static void main(String[] args) {
        RandomDoubles rd = new RandomDoubles();
        for (int i = 0; i < 7; i++)
            System.out.println( rd.next() + " " );
    }
}

/**
 * Readable接口只要求实现read()方法,在read()内部,将输入内容添加到CharBuffer参数中,或者在没有任何输入时返回-1,返回-1时结果null占一个空位
 * 只要类实现Readable接口的read()方法,就表明该类对Readable的read()方法进行了适配
 */
public class AdaptedRandomDoubles extends RandomDoubles implements Readable {
    private int count;

    public AdaptedRandomDoubles(int count) {
        this.count = count;
    }

    @Override
    public int read(CharBuffer cb) throws IOException {
        if (count-- == 0)
            return -1;
        String result = Double.toString( next() ) + " ";
        cb.append( result );
        return result.length();
    }

    public static void main(String[] args) {
        Logs.getLogs( "AdaptedRandomDoubles" );
        Scanner s = new Scanner( new AdaptedRandomDoubles( 7 ) );
        while (s.hasNextDouble())
            System.out.println( s.nextDouble() + " " );
    }
}
