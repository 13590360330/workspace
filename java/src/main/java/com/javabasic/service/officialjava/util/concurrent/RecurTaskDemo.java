package com.javabasic.service.officialjava.util.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用RecursiveTask<V>
 * 返回任务结果
 * 与RecursiveAction区别,1是返回结果,2是通常显式地调用fork()和join()方法来开始子任务
 * 在这个例子中,之所以使用fork()方法,是因为该方法启动任务却不等待任务结束(因此能够异步地运行任务),通过调用join()方法可以获得每个任务的结果(join()方法返回的是任务的结果)
 */
public class RecurTaskDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        double[] doubles = new double[5000];
        for (int i = 0; i < doubles.length; i++)
            doubles[i] = (double) (((i % 2) == 0) ? i : -i);
        Sum sum = new Sum( doubles, 0, doubles.length );
        //开始任务
        Double invoke = forkJoinPool.invoke( sum );
        System.out.println( invoke );
    }
}


 class Sum extends RecursiveTask<Double> {

    final int seqThresHold = 500;

    double[] data;

    int start, end;

    Sum(double[] vals, int s, int e) {
        data = vals;
        start = s;
        end = e;
    }

    @Override
    protected Double compute() {
        double sum = 0;
        if ((end - start) < seqThresHold) {
            for (int i = start; i < end; i++) sum += data[i];
        } else {
            int middle = (start + end) / 2;
            Sum sum1 = new Sum( data, start, middle );
            Sum sum2 = new Sum( data, middle, end );
            sum1.fork();
            sum2.fork();
            //下面的这条语句会进行等待,直到每个任务完成为止,然后将每个任务的结果返回值相加
            sum = sum1.join() + sum2.join();
//            不需要调用sum1.fork(),还可以写成:
//            sum2.fork();
//            sum = sum1.invoke() + sum2.join(); 或者  sum = sum1.compute() + sum2.join();
        }
        return sum;
    }
}