package com.javabasic.service.thinkinginjava.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * TODO 读取二进制文件 P548   available()
 */
public class BinaryFile {
    public static byte[] read(File bFile) throws Exception {
        BufferedInputStream bf = new BufferedInputStream( new FileInputStream( bFile ) );
        try {
            byte[] data = new byte[bf.available()];   //available()依据流,产生恰当的数组尺寸
            bf.read( data );
            return data;
        } finally {
            bf.close();
        }
    }

    /**
     * getAbsoluteFile()返回的是一个File类对象，这个File对象表示是当前File对象的绝对路径名形式
     * getAbsolutePath()返回的是一个字符串，这个字符串就是当前File对象的绝对路径名的字符串形式
     *
     * @param bFile
     * @return  byte[]数组
     * @throws Exception
     */
    public static byte[] read(String bFile) throws Exception {
        return read( new File( bFile ).getAbsoluteFile() );
    }
}
