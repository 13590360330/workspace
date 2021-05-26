package com.javabasic.service.thinkinginjava.nio;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;


/**
 * TODO 用缓冲器操纵数据
 */
public class UsingBuffers {

    public static void main(String[] args) {
        Logs usingBuffers = Logs.getLogs( "UsingBuffers" );
        char[] chars = "UsingBuffers".toCharArray();
        ByteBuffer allocate = ByteBuffer.allocate( chars.length * 2 );
        CharBuffer charBuffer = allocate.asCharBuffer();
        charBuffer.put( chars );
        System.out.println( charBuffer.rewind() );
        sym( charBuffer );
        System.out.println( charBuffer.rewind() );
        sym( charBuffer );
        System.out.println( charBuffer.rewind() );
        usingBuffers.closePrintStream();
    }

    private static void sym(CharBuffer charBuffer) {
        while (charBuffer.hasRemaining()) {
            charBuffer.mark();   // mark() 在这个位置上设置标记 只会有一个mark标签
            char c1 = charBuffer.get();
            char c2 = charBuffer.get();
            charBuffer.reset();
            charBuffer.put( c2 ).put( c1 ); //交换位置  get()和put()是不同的指针
        }
    }
}
