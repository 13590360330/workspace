package com.sparkstreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SparkDemo4 {

    SparkConf conf = new SparkConf().setAppName( "SparkDemo4" );
    JavaStreamingContext jssc = new JavaStreamingContext( conf, Durations.seconds( 10 ) );
    JavaDStream<String> ds = jssc.textFileStream( "/home/bigdata/bigdata/filepath" );

}
