package com.javabasic.service.thinkinginjava.nio;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * TODO [文件加锁,对映射文件的部分加锁] P566
 *
 * 一旦文件被上锁直到被release,文件将无法被其他方式使用,包括人为打开
 */
public class FileLocking {
    public static void main(String[] args) throws Exception {
        Logs.getLogs("FileLocking");
        FileOutputStream fileOutputStream = new FileOutputStream( "../BigData/src/resources/tmp/test/2.txt" );
        FileChannel channel = fileOutputStream.getChannel();
        FileLock fileLock =channel.lock();     //阻塞锁
//        FileLock fileLock =channel.lock(0,1000,false);  //部分加锁
        System.out.println(fileLock.isValid());
        if(fileLock != null){
            System.out.println("Locked File");
            TimeUnit.SECONDS.sleep( 10 );
            fileLock.release();
            System.out.println("release Lock");
        }
        fileOutputStream.close();
        System.out.println(fileLock.isValid());
    }
}
