package com.javabasic.service.officialjava.util.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 使用RecursiveAction
 */
class SqrtTransform extends RecursiveAction {

    final int seqThreshold = 1000;

    double[] data;

    int start, end;

    SqrtTransform(double[] vals, int s, int e) {
        data = vals;
        start = s;
        end = e;
    }

    //计算逻辑代码
    @Override
    protected void compute() {
        //选择临界点
        if ((end - start) < seqThreshold) {
            for (int i = start; i < end; i++) {
                data[i] = Math.sqrt( data[i] );
            }
        } else {
            int middle = (start + end) / 2;
            //开始多个任务,递归
            invokeAll( new SqrtTransform( data, start, middle ), new SqrtTransform( data, middle, end ) );
        }
    }
}

class ForkJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool fjp = new ForkJoinPool();
//        ForkJoinPool fjp = ForkJoinPool.commonPool(); 使用公共池,可以获取引用,也可以不获取
        double[] doubles = new double[100000];
        for (int i = 0; i < doubles.length; i++)
            doubles[i] = (double) i;

        for (int i = 0; i < 10; i++)
            System.out.println( doubles[i] + " " );

        SqrtTransform sqrtTransform = new SqrtTransform( doubles, 0, doubles.length );
        //开始任务,并等待该任务结束,返回或不返回调用任务的结果
        fjp.invoke( sqrtTransform );
        for (int i = 0; i < 10; i++)
            System.out.format( "%.4f", doubles[i] );
        System.out.println();
    }
}
