package com.javabasic.service.thinkinginjava.nio;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

/**
 * TODO [(1)获取基本类型,(2)视图缓冲器,(3)字节存放顺序(P559低位优先和高位优先)]
 */
public class GetData {


    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        Logs getData = Logs.getLogs( "GetData" );

        ByteBuffer allocate = ByteBuffer.allocate( BSIZE );
        System.out.println( "BSIZE " + allocate.limit() );
        int i = 0;
        while (i++ < allocate.limit())     //检测BSIZE
            if (allocate.get() != 0)       //缓存器里没有东西所以allocate.get() == 0
                System.out.println( "nonzero" );
        System.out.println( " i = " + i );
        allocate.rewind();
        allocate.asCharBuffer().put( "Howdy!" );    //获取缓冲器上的视图
//        System.out.println( "allocate.asCharBuffer().get():" + allocate.get() );   //见P559
        char c;
        while ((c = allocate.getChar()) != 0) {
            System.out.println( c + " " );
        }
        System.out.println( " " );
        allocate.rewind();
        allocate.asShortBuffer().put( (short) 471142 );
        System.out.println( "allocate.asCharBuffer().get():" + allocate.get() );
        System.out.println( allocate.getShort() );
        allocate.rewind();
        allocate.asIntBuffer().put( 99471142 );
        System.out.println( "allocate.asCharBuffer().get():" + allocate.get() );
        System.out.println( allocate.getInt() );
        allocate.rewind();
        allocate.asLongBuffer().put( 99471142 );
        System.out.println( "allocate.asCharBuffer().get():" + allocate.get() );
        System.out.println( allocate.getLong() );
        allocate.rewind();
        allocate.asFloatBuffer().put( 99471142 );
        System.out.println( "allocate.asCharBuffer().get():" + allocate.get() );
        System.out.println( allocate.getFloat() );
        allocate.rewind();
        allocate.asDoubleBuffer().put( 99471142 );
        System.out.println( "allocate.asCharBuffer().get():" + allocate.get() );
        System.out.println( allocate.getDouble() );
        allocate.rewind();

        //视图缓冲器 P557    视图缓冲器是修改缓冲器的可行方式
        allocate.clear();
        IntBuffer put = allocate.asIntBuffer().put( new int[]{11, 42, 47, 99, 143, 811, 1016} );
        System.out.println( "allocate.getInt():" + allocate.getInt() );  //获取ByteBuffer中指针位置为0的元素
        System.out.println( "put.get():" + put.get( 0 ) );     //读其中的一个元素
        put.put( 0, 13 );                                //修改
        System.out.println( "put.get():" + put.get( 0 ) );

        //遍历缓冲器
        put.flip();
        while (put.hasRemaining()) {
            int i1 = put.get();
            System.out.println( i1 );
        }

        //ByteBuffer,CharBuffer,.......的区别      P559  低位优先和高位优先
        ByteBuffer wrap = ByteBuffer.wrap( new byte[]{0, 0, 0, 0, 0, 0, 'a'} );
        wrap.rewind();
        while (wrap.hasRemaining()) {
            System.out.println( wrap.position() + "->" + wrap.get() + "," );
        }

        CharBuffer charBuffer = ((ByteBuffer) wrap.rewind()).asCharBuffer();
        while (charBuffer.hasRemaining()) {
            System.out.println( charBuffer.position() + "->" + charBuffer.get() + "," );
        }

        getData.closePrintStream();
    }
}
