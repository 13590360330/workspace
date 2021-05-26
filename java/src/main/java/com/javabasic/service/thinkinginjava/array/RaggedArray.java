package com.javabasic.service.thinkinginjava.array;

import java.util.Arrays;
import java.util.Random;

/**
 * TODO 多维数组
 */
public class RaggedArray {
    public static void main(String[] args) {
        Random random = new Random( 47 );
        int[][][] ints = new int[random.nextInt( 7 )][][];
        /**定义第2个层级*/
        for (int i = 0; i < ints.length; i++) {
            ints[i] = new int[random.nextInt( 5 )][];
            /**定义第3个层级*/
            for (int j = 0; j < ints[i].length; j++) {
                ints[i][j] = new int[random.nextInt( 5 )];
            }
        }
        System.out.println( Arrays.deepToString( ints ) );
    }
}
