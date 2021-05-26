package com.sparkstreaming;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextOutputFormat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class NewTextOutputFormat<K, V> extends TextOutputFormat<K, V> {

    public NewTextOutputFormat() {
    }

    public static void setOutputPath(JobConf conf, Path outputDir) {
        outputDir = new Path(conf.getWorkingDirectory(), "");
        System.out.println(outputDir);
        conf.set(org.apache.hadoop.mapreduce.lib.output.
                FileOutputFormat.OUTDIR, outputDir.toString());
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<NewTextOutputFormat> newTextOutputFormatClass = NewTextOutputFormat.class;
        NewTextOutputFormat newTextOutputFormat = newTextOutputFormatClass.newInstance();
        Method[] methods = newTextOutputFormatClass.getMethods();
//        Arrays.stream(methods).forEachOrdered( x-> System.out.println(x) );
        Field[] fields = newTextOutputFormatClass.getFields();
        Arrays.stream( fields ).forEachOrdered( x -> System.out.println( x ) );
    }
}
