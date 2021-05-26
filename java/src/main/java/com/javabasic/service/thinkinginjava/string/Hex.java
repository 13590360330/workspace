package com.javabasic.service.thinkinginjava.string;

import com.javabasic.service.thinkinginjava.io.BinaryFile;

import java.io.File;

/**
 * TODO 一个十六进制转储工具 P294
 *
 * 以可读的十六进制格式将字节数组打印出来
 */
public class Hex {
    public static String format(byte[] data) {
        StringBuilder result = new StringBuilder();
        int n = 0;
        for (byte b : data) {
            if (n % 16 == 0)
                result.append( String.format( "%05X:", n ) );  //X-整数(十六进制) Formatter转换
            result.append( String.format( "%02X ", b ) ); //这里%02X中的0是占位符（不写时会以空格补足），2是位数，x代表数字类型；
            n++;
            if (n % 16 == 0) result.append( "\n" );
        }
        result.append( "\n" );
        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            System.out.println( format( BinaryFile.read(
                    "..\\BigData\\src\\main\\com\\java\\com.service\\Thinking_In_Java\\io\\OSExecuteException.class" ) ) );
        else
            System.out.println( format( BinaryFile.read( new File( args[0] ) ) ) );
    }
}
