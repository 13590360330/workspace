package com.javabasic.service.officialjava.util.other;

import java.util.StringTokenizer;

/**
 * TODO StringTokenizer字符串分割
 * 将字符串分隔成一系列独立的部分,又称为标记
 */
public class StringTokenizerUsage {
    public static void main(String[] args) {
        /**
         * 默认的定界符组由空白字符构成:空格,制表符,换页符,换行符以及回车符
         *     StringTokenizer(String str)                                           //使用默认定界符" "
         *     StringTokenizer(String str,String delimiters)                         //指定定界符 delimiters中的每个字符都被认为是有效的定界符,如下" :=;\t"
         *     StringTokenizer(String str,String delimiters,boolean delimAsToken)    //delimAsToken=true 将定界符作为标记返回
         */
        String in = "title\n=Java: the " +
                "Complete\tReference; " +
                " author=Schildt;" +
                "publisher =McGraw-Hill;" +
                "copyright= 2014";
//        StringTokenizer stringTokenizer = new StringTokenizer( in );
//        while (stringTokenizer.hasMoreTokens()) {
//            String val = stringTokenizer.nextToken();
//            System.out.println( val );
//        }
        StringTokenizer stringTokenizer2 = new StringTokenizer( in," :=;\t" );
        while (stringTokenizer2.hasMoreTokens()) {
            String val = stringTokenizer2.nextToken();
            System.out.println( val );
        }
    }
}
