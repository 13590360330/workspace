package com.sparkstreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SC {
    private SparkConf conf;
    private JavaStreamingContext jssc;
    private JavaSparkContext javaSparkContext;

    public SC(String master, String appname) {
        this.conf = rtConf( master, appname );
    }

    private SparkConf rtConf(String master, String appname) {
        SparkConf conf;
        if (master.contains( "local" ))
            conf = new SparkConf().setMaster( "local[*]" ).setAppName( appname );
        else
            conf = new SparkConf().setAppName( appname );
        return conf;
    }

    public SparkConf getConf() {
        return conf;
    }

    public JavaSparkContext getjavaSparkContext() {
        return new JavaSparkContext( conf );
    }

    public JavaStreamingContext getJavaStreamingContext(int num) {
        return new JavaStreamingContext( conf, Durations.seconds( num ) );
    }


}
