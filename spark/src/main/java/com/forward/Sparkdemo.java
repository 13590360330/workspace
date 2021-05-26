package com.forward;

import com.javabasic.service.thinkinginjava.io.Logs;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.DoubleFunction;
import org.apache.spark.util.StatCounter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO 移除异常值
 */
public class Sparkdemo {

    public static void main(String[] args) {

        Logs.getLogs( "Sparkdemo" );
        SparkConf conf = new SparkConf().setMaster( "local[*]" ).setAppName( "Sparkdemo" );
        JavaSparkContext sc = new JavaSparkContext( conf );
        JavaRDD<String> pl = sc.parallelize( new ArrayList<String>( Arrays.asList( "11111", "2222", "333", "44", "5" ) ) );
        JavaDoubleRDD disranceDoubles = pl.mapToDouble( new DoubleFunction<String>() {
            @Override
            public double call(String s) throws Exception {
                return Double.parseDouble( s );
            }
        } );
        /**spark的数值操作是通过流式算法实现的,允许以每次一个元素的方式构建出模型,这些统计数据都会在调用stats()时通过一次遍历数据计算出来,
         * 并以StatsCounter对象返回,使用StatsCounter对象可执行count(),mean(),sum(),max(),min()...等方法*/
        final StatCounter stats = disranceDoubles.stats();
        final double stdev = stats.stdev();   //标准差
        final double mean = stats.mean();     //元素的平均值
//        JavaDoubleRDD filter = disranceDoubles.filter( new Function<Double, Boolean>() {
//            @Override
//            public Boolean call(Double aDouble) throws Exception {
//                return (Math.abs( aDouble - mean ) < 3 * stdev);
//            }
//        } );
        /**Math.abs(n)：对int、long、float、double类型的数取绝对值,对于这个方法，如果是-2147483648不会进行处理，得到的还是负数*/
        JavaDoubleRDD filter = disranceDoubles.filter( x -> (Math.abs( x - mean ) < 3 * stdev) );
        System.out.println( Math.abs( -20 ) );
        System.out.println( StringUtils.join( filter.collect(), "," ) );
    }
}
