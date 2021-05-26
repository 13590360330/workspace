package com.javabasic.service.thinkinginjava.basic;

/**
 * TODO [break,return,continue及标签 P73]
 */

import com.javabasic.service.thinkinginjava.io.Logs;

/**
 * 标签的模板
 * label1:
 * outer-iteration{
 * inner-iteration{
 * break;
 * continue;
 * continue label1;
 * break label1;
 * }
 * }
 */

public class LabeledFor {
    public static void main(String[] args) {
        Logs.getLogs( "LabeledFor" );
        int i = 0;
        outer:
        for (; true; ) {
            inner:
            for (; i < 10; i++) {
                System.out.println( "i= " + i );
                if (i == 2) {
                    System.out.println( "continue" );
                    continue;        //跳出到 i = 3
                }
                if (i == 3) {
                    System.out.println( "break" );
                    i++;
                    break;           //终止 for (; i < 10; i++)
                }
                if (i == 7) {
                    System.out.println( "continue outer" );
                    i++;
                    continue outer;  //跳到标签 outer 这个for循环终止
                }
                if (i == 8) {
                    System.out.println( "break outer" );
                    break outer;     //中断并跳出标签所指的循环
                }
                for (int k = 0; k < 5; k++) {
                    System.out.println( "continue inner:" + k );
                    continue inner;  //跳到标签 inner处 这个for循环终止
                }
                System.out.println( "i:" + i );
            }
        }
    }

}
