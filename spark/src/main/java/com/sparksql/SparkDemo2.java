package com.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.hive.HiveContext;

import java.util.ArrayList;

public class SparkDemo2 {
    /**
     * TODO 在java中基于JavaBean创建DataFrame或Rdd
     * 通过这种方式,可以实现collection对象和DataFrame或Rdd的自由转换
     */
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster( "local[*]" ).setAppName( "SparkDemo1" );
        JavaSparkContext sc = new JavaSparkContext( conf );
        HiveContext hiveContext = new HiveContext( sc );
        ArrayList<HappyPerson> peopleList = new ArrayList<>();
        peopleList.add( new HappyPerson( "holden", "coffee" ) );
        /**TODO parallelize的作用是对集合或数组进行并行化产生rdd,作用类同makeRDD*/
        /**将javabean转成DataFrame或Rdd*/
        JavaRDD<HappyPerson> happyPersonJavaRDD = sc.parallelize( peopleList );
        /**TODO java中將rdd转换为DataFrame的方式*/
        DataFrame dataFrame = hiveContext.applySchema( happyPersonJavaRDD, HappyPerson.class );
        JavaRDD<Row> rowJavaRDD = dataFrame.toJavaRDD();
    }
}
