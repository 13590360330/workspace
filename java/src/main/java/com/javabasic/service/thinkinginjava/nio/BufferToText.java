package com.javabasic.service.thinkinginjava.nio;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * TODO 数据转换
 * <p>
 * 缓冲器容纳的是普通的字节,为了把他们转换成字符,在输入(输出)的时候进行编码(解码),不能用系统默认的UTF-8
 */
public class BufferToText {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws Exception {

        Logs.getLogs( "BufferToText" );

        FileChannel channel = new FileOutputStream( "../BigData/src/resources/tmp/test/2.txt" ).getChannel();

        channel.write( ByteBuffer.wrap( "some text".getBytes() ) );
        channel.close();
        channel = new FileInputStream( "../BigData/src/resources/tmp/test/2.txt" ).getChannel();
        ByteBuffer allocate = ByteBuffer.allocate( BSIZE );
        channel.read( allocate );
        allocate.flip();
        System.out.println( allocate.asCharBuffer() );
        allocate.rewind();
        String encoding = System.getProperty( "file.encoding" );
        System.out.println( "encoding : " + encoding );
        channel = new FileOutputStream( "../BigData/src/resources/tmp/test/2.txt" ).getChannel();
        channel.write( ByteBuffer.wrap( "Some text".getBytes( "UTF-16BE" ) ) );
//        channel.write( ByteBuffer.wrap( "Some text".getBytes( Charset.availableCharsets().get("UTF-16BE" ) ) ) );
//        System.out.println("Charset.availableCharsets().get(\"UTF-16BE\" ) ) ):"+Charset.availableCharsets().get("UTF-16BE" ));
        channel.close();
        channel = new FileInputStream( "../BigData/src/resources/tmp/test/2.txt" ).getChannel();
        allocate.clear();
        channel.read( allocate );
        allocate.flip();
        System.out.println( allocate.asCharBuffer() );
        channel = new FileOutputStream( "../BigData/src/resources/tmp/test/2.txt" ).getChannel();
        allocate = ByteBuffer.allocate( 24 );
        allocate.asCharBuffer().put( "Some text" );
        channel.write( allocate );
        channel.close();
        channel = new FileInputStream( "../BigData/src/resources/tmp/test/2.txt" ).getChannel();
        allocate.clear();
        channel.read( allocate );
        allocate.flip();
        System.out.println( allocate.asCharBuffer() );

    }
}


/**
 * TODO 字符集类
 */
class AvailableCharSets {

    public static void main(String[] args) {
        Logs.getLogs( "AvailableCharSets" );
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        Iterator<String> iterator = charsets.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.print( next );
            Iterator<String> iterator1 = charsets.get( next ).aliases().iterator();
            if (iterator1.hasNext()) {
                System.out.print( ":[ " );
            }
            while (iterator1.hasNext()) {
                System.out.print( iterator1.next() );
                if (iterator1.hasNext()) {
                    System.out.print( ", " );
                }
            }
            System.out.println( " ]" );
        }
    }

}