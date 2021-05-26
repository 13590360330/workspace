package com.javabasic.service.thinkinginjava.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO 正则表达式测试类 (Pattern和Matcher P300) ??????
 */
public class TestRegularExpression {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println( "Usage:\njava TestRegularExpression " +
                    "characterSequence regularExpression+" );
            System.exit( 0 );
        }
        System.out.println( "Input: \" " + args[0] + "\" " );
        for (String arg : args) {
            System.out.println( "Regular expression: \" " + arg + "\" " );
            //Pattern.compile( arg ),根据arg的正则表达式生成一个Pattern对象,接下来,把要检索的字符串传入matcher()方法
            Pattern p = Pattern.compile( arg );
            Matcher m = p.matcher( args[0] );
            System.out.println( "m:" + m );
            while (m.find()) {
                System.out.println( "Match \" " + m.group() + "\" at positions " +
                        m.start() + "-" + (m.end() - 1) );
            }
        }
    }
}
