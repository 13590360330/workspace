package com.sparksql;

import com.javabasic.service.thinkinginjava.io.Logs;
import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.sql.types.DataTypes;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SparkDemo1 {
    /**
     * TODO sparksql解析json文件,以及任何hive支持的存储格式(SerDe),包括文本(txt),Parquet,ORC,Avro,Protocal Buffer及RCFiles文件
     *
     * @param args
     */
    public static void main(String[] args) {
        Logs.getLogs( "SparkDemo1result", false );
//        SparkConf conf = new SparkConf().setAppName( "SparkDemo1" );
        SparkConf conf = new SparkConf().setMaster( "local[*]" ).setAppName( "SparkDemo1" );
        JavaSparkContext sc = new JavaSparkContext( conf );
        HiveContext hiveContext = new HiveContext( sc );

        /**1,解析json是逐行解析,也就是json文件中每一行是一条json(标准json),
         * 2,类似于"name": "BeJson",会统计所有行的这种格式,然后整,理字段总数,会保证将每个"name"位置的字符设置为字段,":"前的是字段名,例如 isNonProfit和isNonProfit1,
         * 3,读取parquet格式;类似于json,hiveContext.parquetFile( ... )*/
        DataFrame dataFrame = hiveContext.jsonFile( "..\\BigData\\src\\resources\\tmp\\Person.json" );
//        DataFrame dataFrame = hiveContext.jsonFile( "../bigdata/resources/Person.json" );

        /**TODO 使用dataFrame.printSchema()就能看到表结构了
         * 这个方法是System.out的输出流*/
        dataFrame.printSchema();
        dataFrame.registerTempTable( "Person" );

        /**缓存数据表:如果想多次使用这张表,就应当把这张表缓存起来,对应的删掉缓存hiveContext.uncacheTable( "Person" );
         * 在RDD上的cache()方法也会引发一次对cacheTable()方法的调用 P148*/
        hiveContext.cacheTable( "Person" );
        DataFrame sql = hiveContext.sql( "select links.name from Person" );

        /**DataFrame是一个Row对象的集合,Row对象本质就是一个定长的数组 P147*/
        sql.collectAsList().stream().forEachOrdered( x -> System.out.println( x ) );
        /**在spark中的算法,最好都转换成rdd再执行,spark的rdd算子中无法重定向到日志*/
//        sql.toJavaRDD().map( x -> x.getString( 0 ) + " " + x.getString( 1 ) ).foreach( x -> System.out.println( x ) );

//        DataFrame sql1 = hiveContext.sql( "insert into default.Person select * from Person" );
//        System.out.println(sql1.count());

        /**TODO 文件格式的转换,json,parquet(DataFrame-sparksql),txt(RDD-sparkcore)*/
        try {
            File file1 = new File( "..\\BigData\\src\\resources\\tmp\\sparksql" );
            FileUtils.deleteDirectory( file1 );
            FileUtils.forceMkdir( file1 );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        sql.saveAsParquetFile( "..\\BigData\\src\\resources\\tmp\\sparksql\\Person.parquet" );
        sql.toJavaRDD().saveAsTextFile( "..\\BigData\\src\\resources\\tmp\\sparksql\\Person.txt" );
        sql.toJavaRDD().saveAsObjectFile( "..\\BigData\\src\\resources\\tmp\\sparksql\\Person.dat" );

        /**TODO SparkSql的UDF函数 P157
         * 返回值类型Integer需要和IntegerType匹配*/
        hiveContext.udf().register( "stringLengthJava", new UDF1<String, Integer>() {
            @Override
            public Integer call(String s) throws Exception {
                return s.length();
            }
        }, DataTypes.IntegerType );
        DataFrame sql2 = hiveContext.sql( "select stringLengthJava('url') from Person limit 10" );
        Arrays.stream( sql2.collect() ).forEachOrdered( x -> System.out.println( x ) );

        /**
         * TODO 在java中无法将javaRDD转DataFrame,不过可以创建javaBean之后,通过javaBean中转
         *
         *  scala中导入隐式转换之后可以
         *  //导入隐式转换，如果不导入无法将RDD转换成DataFrame
         *     //将RDD转换成DataFrame
         *     import sqlContext.implicits._
         *     val personDF = personRDD.toDF
         */

        /**TODO Hive UDF P157*/


    }
}
