package com.javabasic.service.officialjava.util.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * RecursiveAction
 * 不返回任务结果
 * 公共池是JDK8对Fork/Join框架所做的增强之一,使其更易于使用,在不考虑JDK7的情况下,公共池是首选
 * invokeAll()方法隐式的递归启动子任务,invokeAll()方法会进行等待,直到这两个任务返回为止
 */
public class FJExperiment {
    public static void main(String[] args) {
        int pLevel;
        int threshold;

        if (args.length != 2) {
            System.out.println( "Usage 参数输入有误" );
            return;
        }
        pLevel = Integer.parseInt( args[0] );
        threshold = Integer.parseInt( args[1] );
        long beginT, endT;
        ForkJoinPool forkJoinPool = new ForkJoinPool( pLevel );
        double[] doubles = new double[1000000];
        for (int i = 0; i < doubles.length; i++) doubles[i] = (double) i;
        Transform transform = new Transform( doubles, 0, doubles.length, threshold );
        beginT = System.nanoTime();
        //开始任务
        forkJoinPool.invoke( transform );
//        forkJoinPool.execute( transform );
//        while (!transform.isDone()){
//            System.out.println("forkJoinPool:"+forkJoinPool); //ForkJoinPool重写了toString方法
//        }
        endT = System.nanoTime();
        System.out.println( "设置并行等级: " + pLevel );
        System.out.println( "临界值:" + threshold );
        System.out.println( "Elapsed time:" + (endT - beginT) + " ns" );
        System.out.println( "查看并行级别" + forkJoinPool.getParallelism() );
        System.out.println( "查看公共池并行级别" + ForkJoinPool.commonPool().getParallelism() );
        System.out.println( "查看的处理器数量" + Runtime.getRuntime().availableProcessors() );
    }
}

class Transform extends RecursiveAction {

    int seqThreshlod;

    double[] data;

    int start, end;

    Transform(double[] vals, int s, int e, int t) {
        data = vals;
        start = s;
        end = e;
        seqThreshlod = t;
    }


    @Override
    protected void compute() {
        if ((end - start) < seqThreshlod) {
            for (int i = start; i < end; i++) {
                //程序逻辑块,分而治之,指每个线程单独执行切分的最小块start-end这部分数据
                System.out.println("no---"+start+";"+end+";"+Thread.currentThread().getId());
                if ((data[i] % 2 == 0)) data[i] = Math.sqrt( data[i] );
                else data[i] = Math.cbrt( data[i] );
            }
        } else {
            int middle = (start + end) / 2;
            invokeAll( new Transform( data, start, middle, seqThreshlod ), new Transform( data, middle, end, seqThreshlod ) );
            //静态方法的调用等同形式如下
//            ForkJoinTask.invokeAll( new Transform( data, start, middle, seqThreshlod ), new Transform( data, start, middle, seqThreshlod ) );
        }
    }
}