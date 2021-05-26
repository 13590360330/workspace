package com.javabasic.service.thinkinginjava.array;

import java.util.Arrays;
import java.util.Collections;

/**
 * TODO Arrays的实用功能  P451
 */
public class CopyingArrays {
    public static void main(String[] args) {
        int[] ints = new int[7];
        int[] ints1 = new int[10];
        /**fill*/
        Arrays.fill( ints, 47 );
        Arrays.fill( ints1, 99 );
        System.out.println( Arrays.toString( ints ) );
        System.out.println( Arrays.toString( ints1 ) );
        System.out.println( "---------------------------------" );
        /**arraycopy*/
        System.arraycopy( ints, 0, ints1, 0, ints.length );  //将ints中前ints.length个元素拷贝到ints1中
        System.out.println( Arrays.toString( ints1 ) );
        System.out.println( "---------------------------------" );
        int[] ints2 = new int[5];
        Arrays.fill( ints2, 103 );
        System.arraycopy( ints, 0, ints2, 0, ints2.length );
        System.out.println( Arrays.toString( ints2 ) );
        System.out.println( "---------------------------------" );
        Arrays.fill( ints2, 103 );
        System.arraycopy( ints2, 0, ints, 0, ints2.length );
        System.out.println( Arrays.toString( ints ) );
        System.out.println( "---------------------------------" );
        /**equals*/
        int[] ints3 = new int[5];
        int[] ints4 = new int[5];
        Arrays.fill( ints3, 5 );
        Arrays.fill( ints4, 5 );
        boolean equals = Arrays.equals( ints3, ints4 );
        System.out.println( equals );     //equals 比较数组长度和内容必须完全一致
        System.out.println( "---------------------------------" );
        /**sort  排序无需担心性能,对中文无效*/
        Character[] strings = new Character[10];
        RandomGenerator.Character chars = new RandomGenerator.Character();
        for (int i = 0; i < 10; i++) {
            strings[i] = chars.next();
        }
        System.out.println( Arrays.deepToString( strings ) );
        Arrays.sort( strings );                                    //sort默认排序依据字典A-Za-z排序
        System.out.println( Arrays.deepToString( strings ) );
        Arrays.sort( strings, Collections.reverseOrder() );        //倒序
        System.out.println( Arrays.deepToString( strings ) );
        String[] strings1 = new String[10];
//        System.arraycopy( strings,0,strings1,0,strings.length ); //使用arraycopy在不同数据类型的数组之间拷贝,会报异常  at java.lang.System.arraycopy(Native Method)
        String[] strings2 = {"36578","5hdfg","8fsdafa","2adeqew","1qerq","asdf","sdqwe","Cdaaaaaasg","Grehrt","ERFty","cfg","sdFr","Hjk","iuyt","QAZxsw","sdfr","bgfds"};
        System.out.println( Arrays.deepToString( strings2 ) );
        Arrays.sort( strings2, String.CASE_INSENSITIVE_ORDER );    //利用String,以首字母忽略字母大小写来排序
        System.out.println( Arrays.deepToString( strings2 ) );
        /**binarySearch 在已排序的数组中查找*/
        //binarySearch只能对sort排序后的元素进行查找,正值表示元素下标,负值表示未找到,对未排序的数组,结果往往不可预料,也不能用String.CASE_INSENSITIVE_ORDER排序,当是大写字母时,结果会返回负值,表示未找到
        int cdaaaaaasg = Arrays.binarySearch( strings2, "sdqwe" );
        System.out.println(cdaaaaaasg);


    }
}
