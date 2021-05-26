package com.sparkstreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * TODO SparkStreaming
 */
public class SparkDemo3 {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName( "SparkDemo4" ).setMaster( "local[*]" );

        /**
         * 批次间隔:
         * 指定1秒的批处理大小
         * 每个批次都形成一个RDD
         * Streaming Context只能启动一次
         */
        JavaStreamingContext jssc = new JavaStreamingContext( conf, Durations.seconds( 10 ) );
        /**
         * 监听端口
         * 监听数据源的是一个线程,每设置一个数据源就需要一个线程,而处理计算过程的则是其他线程
         */
//        JavaReceiverInputDStream<String> lines = jssc.socketTextStream( "127.0.0.1", 9999 );
//        JavaDStream<String> abc = lines.filter( x -> x.contains( "abc" ) );

        /**
         * 监听文件夹
         * 读取的文件必须是在启动任务后才在该目录下产生的文件,即这个文件的产生时间要在sparkstreaming任务开始至后,
         * 且不能和任务开始之前的文件同名,否则不能读取
         */
        JavaDStream<String> ds = jssc.textFileStream( "E:\\日常文件\\临时目录\\新建文件夹" );
        /**
         * 每个DStream包含多个批次(RDD)
         */
//        ds.foreachRDD( (VoidFunction<JavaRDD<String>>) rdd->rdd.foreach( x-> System.out.println(x) ) );
        ds.print();

        /**
         * 要开始接收数据,必须显示的调用StreamingContext的start()方法
         *
         */
        jssc.start();
        /**
         * awaitTermination()等待流计算完成,防止应用退出
         */
        jssc.awaitTermination();
    }

}
