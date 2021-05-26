package com.javabasic.service.thinkinginjava.io;

import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * TODO 文件锁  禁止非本线程使用这个文件   P566
 */
public class FileLocking {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream( "../BigData/testfile.txt" );
        FileLock fl = fos.getChannel().tryLock();
        if (fl != null) {
            System.out.println( "Locked File" );
            /**
             * TODO 休眠
             */
            TimeUnit.SECONDS.sleep( 5 );
            fl.release();
            System.out.println( "Released Lock" );
        }
        fos.close();
    }
}
