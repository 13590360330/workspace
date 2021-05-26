package com.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.hive.HiveContext;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SparkSqlDemo3 {

    /**
     * TODO SparkContext并发
     * SparkContext虽然是单例,但是可以在多线程下,实现多任务并发
     *
     * @param args
     */
    public static void main(String[] args) {
        SparkConf sparkSqlDemo1 = new SparkConf().setAppName( "SparkSqlDemo1" );
        JavaSparkContext javaSparkContext = new JavaSparkContext( sparkSqlDemo1 );
        HiveContext hiveCt = new HiveContext( javaSparkContext );

        String[] sql = {"ods_demo1_afc_d", "ods_demo1_borrow_money_d", "ods_demo1_quota_management_i"
                , "ods_demo1_repayment_i", "ods_demo1_user_info_item_d", "ods_demo1_users_d"};
        for (; ; ) {
            Arrays.stream( sql ).parallel().forEach( x -> {
                String sqlstr = "SELECT count(*) FROM ods." + x + " LIMIT 100";
                DataFrame sql1 = hiveCt.sql( sqlstr );
                Date date = new Date();     //获取当前时间
                long msec = date.getTime();
                sql1.toJavaRDD().saveAsTextFile( "/tmp/logs/bigdata/logs/sparksql/" + x + msec );
            } );
            try {
                TimeUnit.MINUTES.sleep( 10 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
