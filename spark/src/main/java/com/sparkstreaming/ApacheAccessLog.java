package com.sparkstreaming;

import org.apache.spark.streaming.api.java.JavaDStream;

public class ApacheAccessLog {

    private String filepath;
    private String splitstr;

    public ApacheAccessLog(String filepath, String splitstr) {
        this.filepath = filepath;
        this.splitstr = splitstr;
    }

    public JavaDStream<Integer> getStrCount(String str,JavaDStream<String> ds) {
        JavaDStream<Integer> map = ds.filter( rdd -> rdd.contains( str ) ).map( rdd -> rdd.length() );
        return map;
    }

}