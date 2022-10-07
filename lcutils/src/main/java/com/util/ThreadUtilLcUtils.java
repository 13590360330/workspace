package com.util;

import cn.hutool.core.thread.ExecutorBuilder;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * author:liuch
 * date：2022-10-02
 * desc: 线程池
 */
public class ThreadUtilLcUtils {

    public static void main(String[] args) {

        /**
         *  1.默认线程池
         *  初始线程数为corePoolSize指定的大小
         *  没有最大线程数限制
         *  默认使用LinkedBlockingQueue，默认队列大小为1024（最大等待数1024）
         *  当运行线程大于corePoolSize放入队列，队列满后抛出异常
         */

        ExecutorService executor1 = ExecutorBuilder.create().build();


        /**
         * 2.单线程线程池
         * 初始线程数为 1
         * 最大线程数为 1
         * 默认使用LinkedBlockingQueue，默认队列大小为1024
         * 同时只允许一个线程工作，剩余放入队列等待，等待数超过1024报错
         */
        ExecutorService executor2 = ExecutorBuilder.create()//
                .setCorePoolSize( 1 )//
                .setMaxPoolSize( 1 )//
                .setKeepAliveTime( 0 )//
                .build();

        /**
         * 3.更多选项的线程池
         *
         * 初始5个线程
         * 最大10个线程
         * 有界等待队列，最大等待数是100
         */
        ExecutorService executor3 = ExecutorBuilder.create()
                .setCorePoolSize( 5 )
                .setMaxPoolSize( 10 )
                .setWorkQueue( new LinkedBlockingQueue<>( 100 ) )
                .build();

        /**
         * 4.特殊策略的线程池
         *
         * 初始5个线程
         * 最大10个线程
         * 它将任务直接提交给线程而不保持它们。当运行线程小于maxPoolSize时会创建新线程，否则触发异常策略
         */
        ExecutorService executor4 = ExecutorBuilder.create()
                .setCorePoolSize( 5 )
                .setMaxPoolSize( 10 )
                .useSynchronousQueue()
                .build();

        executor3.execute( new test() );
        executor3.execute( new test() );
        executor3.execute( new test() );


    }

    static class test implements Runnable {
        int i = 0;

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                System.out.println( String.format( "%s,%s", Thread.currentThread().getName(), i ) );
                i++;
                Thread.sleep( 1000 );
            }
        }
    }
}
