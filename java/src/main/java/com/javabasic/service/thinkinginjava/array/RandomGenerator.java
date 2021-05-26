package com.javabasic.service.thinkinginjava.array;

import com.javabasic.dao.Generator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO 随机数据生成器
 */
public class RandomGenerator {
    private static Random r = new Random();

    public static class Boolean implements Generator<java.lang.Boolean> {

        @Override
        public java.lang.Boolean next() {
            return r.nextBoolean();
        }
    }

    public static class Byte implements Generator<java.lang.Byte> {

        @Override
        public java.lang.Byte next() {
            return (byte) r.nextInt();
        }
    }

    public static class Character implements Generator<java.lang.Character> {

        @Override
        public java.lang.Character next() {
            return CountingGenerator.chars[r.nextInt( CountingGenerator.chars.length )];
        }
    }

    /**
     * TODO 继承其他类的内部类
     */
    public static class String extends CountingGenerator.String {
        public String() {
        }
    }

    public static class
    Short implements Generator<java.lang.Short> {

        @Override
        public java.lang.Short next() {
            return (short) r.nextInt();
        }
    }

    public static class
    Integer implements Generator<java.lang.Integer> {
        private int mod = 200000;

        public Integer() {
        }

        public Integer(int modulo) {
            mod = modulo;
        }

        @Override
        public java.lang.Integer next() {
            return r.nextInt( mod );
        }
    }

    public static class
    Long implements Generator<java.lang.Long> {
        private int mod = 200000;

        public Long() {
        }

        public Long(int modulo) {
            mod = modulo;
        }

        @Override
        public java.lang.Long next() {
            return new java.lang.Long( r.nextInt( mod ) );
        }
    }

    public static class
    TimeStampe implements Generator<java.lang.String> {
        private int mod = 2000000000;
        Date date = new Date();     //获取当前时间
        long msec = date.getTime();     //获取date的毫秒数  eg.1614568474146

        @Override
        public java.lang.String next() {
            long ms=msec-(long) r.nextInt( mod )*500;
            date.setTime( new java.lang.Long( ms ) );
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat( "YYYY-MM-dd hh:mm:ss" );
            java.lang.String format = simpleDateFormat1.format( date );
            return format;
        }
    }

    public static class
    Float implements Generator<java.lang.Float> {

        @Override
        public java.lang.Float next() {
            int trimmed = Math.round( r.nextFloat() * 100 );
            return ((float) trimmed) / 100;
        }
    }

    public static class
    Double implements Generator<java.lang.Double> {

        @Override
        public java.lang.Double next() {
            long trimmed = Math.round( r.nextDouble() * 100000000 );
            return ((double) trimmed) / 100;
        }
    }

}

class CountingGenerator {

    static char[] chars = ("abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    public static class
    Character implements Generator<java.lang.Character> {
        int index = -1;

        @Override
        public java.lang.Character next() {
            index = (index + 1) % chars.length;
            return chars[index];
        }
    }

    public static class
    String implements Generator<java.lang.String> {
        private static Random r = new Random();

        File file1 = new File( "../bigdata/properties/createdata/chinese" );
//        File file1 = new File( "..\\BigData\\src\\resources\\properties\\createdata\\chinese" );

        private Set set = ReadFile.readFile( file1 );

        public static class ReadFile {
            /**
             * 把一个文件的内容读取到一个对应编码的Set中去
             */
            private static Set<java.lang.Character> readFile(File file) {
                try {
                    java.lang.String s = FileUtils.readFileToString( file, "utf-8" );
                    ArrayList<java.lang.Character> characters = new ArrayList<>();
                    for (int i = 0; i < s.length(); i++) {
                        characters.add( s.charAt( i ) );
                    }
                    Set<java.lang.Character> collect = characters.stream().collect( Collectors.toSet() );
                    return collect;
                } catch (IOException e) {
                    throw new RuntimeException( e );
                }
            }
        }

        public String() {
        }

        @Override
        public java.lang.String next() {
            char[] buf = new char[r.nextInt( 5 )];
            for (int i = 0; i < buf.length; i++)
                buf[i] = (char) set.toArray()[r.nextInt( set.size() )];
            return new java.lang.String( buf );   //new String[Char[]] 可以用Char[]初始化创建一个String对象
        }
    }
}

