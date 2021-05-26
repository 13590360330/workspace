package com.rdd;

import com.javabasic.service.thinkinginjava.io.Logs;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.Arrays;

public class SparkTest {
    public static void main(String[] args) {
        Logs.getLogs( "SparkTest", true );
        SparkConf conf = new SparkConf();
        JavaSparkContext sc = new JavaSparkContext( conf );
        JavaRDD<String> pl = sc.parallelize( new ArrayList<String>( Arrays.asList( "aaaa", "bbbb" ) ) );
        System.out.println( StringUtils.join( pl.collect(), "," ) );
    }
}
