package com.forward;

import org.apache.commons.io.FileUtils;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SparkCount {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster( "local[*]" ).setAppName( "SparkCount" );
        JavaSparkContext sc = new JavaSparkContext( conf );

        /**
         * TODO [spark中的空行累加器 P89]
         */
        JavaRDD<String> rdd = sc.textFile( "..\\BigData\\src\\resources\\tmp\\Person.json" );
        final Accumulator<Integer> blankLines = sc.accumulator( 0 );  //创建初始值为0 的累加器Accumulator
        /**
         * TODO 扁平化flatMap的常用方法,和FlatMapFunction,Iterable结合使用,
         *
         * 返回的对象需要是Iterable的实现类
         */
        JavaRDD<String> callSigns = rdd.flatMap(
                new FlatMapFunction<String, String>() {
                    public Iterable<String> call(String line) {
                        if (line.equals( "" )) {
                            blankLines.add( 1 );
                        }
                        return Arrays.asList( line.split( " " ) );
                    }
                } );
        try {
            FileUtils.deleteDirectory( new File( "..\\BigData\\src\\resources\\tmp\\output" ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        callSigns.saveAsTextFile( "..\\BigData\\src\\resources\\tmp\\output" );
        System.out.println( "Blank lines: " + blankLines.value() );

        /**
         * TODO [广播变量  P93]
         */



    }
}
