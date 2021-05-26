package com.javabasic.service.thinkinginjava.nio;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * TODO 内存映射文件 P563
 *
 * 尽管I/O在用nio实现后性能有所提高,但是"映射文件访问"往往可以更加显著地加快速度
 */
public class LargeMappedFiles {

    static int length = 0x8FFFFFF; //128M

    public static void main(String[] args) throws Exception {

        Logs.getLogs( "LargeMappedFiles" );

        //映射1.txt的第0-128M的部分内容,映射文件中的所有输出必须使用RandomAccessFile
        MappedByteBuffer out = new RandomAccessFile( "../BigData/src/resources/tmp/test/1.txt", "rw" )
                .getChannel()
                .map( FileChannel.MapMode.READ_WRITE, 0, length );

        for (int i = 0; i < length; i++) {
            out.put( (byte) 'x' );
        }

        System.out.println( "Finished writing" );

        for (int i = length / 2; i < length / 2 + 6; i++) {
            System.out.println( (char) out.get( i ) );
        }
    }
}

/**
 * TODO [性能比较,模板方法,继承自抽象类的匿名内部内] P565
 */
class MappedIO{

}
