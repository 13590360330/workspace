package com.javabasic.service.thinkinginjava.nio;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * TODO [通道(IO设备)和缓冲器]
 * FileChannel 和 ByteBuffer 参考P552
 * 唯一与通道(IO设备)交互的缓冲器是ByteBuffer,是数据移进移出的唯一方式,特殊情况,视图(P560)
 */
public class GetChannel {

    private static final int BSIZE = 1024;


    public static void main(String[] args) {

        Logs getChannel = Logs.getLogs( "GetChannel" );

        try (//FileChannel是相当基础的东西,可以向它传送用于读写的ByteBuffer (IO设备和缓冲器(数据运输器)的关系)
             FileChannel out1 = new FileOutputStream( "../BigData/src/resources/tmp/test/1.txt" ).getChannel();
             FileChannel in = new FileInputStream( "../BigData/src/resources/tmp/test/1.txt" ).getChannel();
             FileChannel out2 = new FileOutputStream( "../BigData/src/resources/tmp/test/2.txt" ).getChannel();
             FileChannel out3 = new FileOutputStream( "../BigData/src/resources/tmp/test/3.txt" ).getChannel()
        ) {
            //"Some more"写入1.txt
            out1.write( ByteBuffer.wrap( "Some more".getBytes() ) );      //warp()方法将已存在的字节数组"包装"到ByteBuffer中,一旦如此,就不再复制底层数组

            //读取1.txt
            ByteBuffer allocate = ByteBuffer.allocate( BSIZE ); //allocate( BSIZE ) 参考P553
            in.read( allocate );  // channel.write() 和 channel.read() 分别向IO设备 写和读
            allocate.flip(); //当调用channel.read()和 write()时必须调用allocate.flip()让它做好让别人读取和写入的准备,包含allocate.rewind()的作用  P553
            //flip()将缓存字节数组的指针设置为数组的开始序列即数组下标0。这样就可以从buffer开头，对该buffer进行遍历（读取）了。

            System.out.println( "position()1:" + allocate.position() );
            while (allocate.hasRemaining()) {
                System.out.print( (char) allocate.get() + " " );
                System.out.println();
            }
            System.out.println( "position()2:" + allocate.position() );
//                allocate.rewind();            // allocate.rewind() 指针返回数据的开始部分

            //写入2.txt
            while (in.read( allocate ) != -1) {  //为什么是-1?    P553
                System.out.println( "1:" + in.read( allocate ) );
                allocate.flip();
                System.out.println( "position1():" + allocate.position() );
                out2.write( allocate );
                System.out.println( "2:" + in.read( allocate ) );
                System.out.println( "position2():" + allocate.position() );
                allocate.clear();  //allocate.clear()清空缓冲器,让缓冲器内部指针重新安排
            }

            //写入3.txt
            in.transferTo( 0, in.size(), out3 );  //作用同上
//            out1.transferFrom(in,0,in.size());

        } catch (Exception e) {
            e.printStackTrace( new PrintWriter( System.err, true ) );
        }

        getChannel.closePrintStream();
    }
}
