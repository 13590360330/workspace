package com.source;

import com.javabasic.service.thinkinginjava.io.Logs;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.hive.HiveContext;

public class SparkCoreSource {
    public static void main(String[] args) {
        Logs.getLogs("SparkCoreSource");
        SparkConf conf=new SparkConf().setMaster("local[*]").setAppName("SparkCoreSource");
        JavaSparkContext sc = new JavaSparkContext( conf );

        /**
         * 非文件系统数据源
         * 除了hadoopFile()和saveAsHadoopFile()这一大类函数,还可以使用hadoopDataset/saveAsHadoopDataSet
         * 和newAPIHadoopDataset/saveAsNewAPIHadoopDataset来访问Hadoop所支持的非文件系统的存储格式,例如,许多想HBase和MongoDB
         * 这样的键值对存储都提供了用来直接读取Hadoop输入格式的接口,Spark可以很方便地使用这些格式
         */

        /**
         * 对于像spark这样的分布式系统,从多个不同机器上一起读入数据,要实现这种情况,每个工作节点都必须能够找到一条新记录的开端,
         * 有些压缩格式会使这变得不可能,而必须要单个节点来读入所有数据,这就很容易产生性能瓶颈,可以很容易地从多个节点上并行读取
         * 的格式被称为"可分割"的格式,下表列出了可用的压缩选项
         * 格式     可分割     平均压缩速度   文本文件压缩效率  hadoop压缩编解码器   纯java实现   原生    备注
         * gzip      否           快              高        org.apache.hadoop       是         是
         *                                                .io.compress.GzipCodec
         *
         * lzo       是         非常快           中等      com.hadoop.compression   是          是    需要在每个节点上安装
         *                                                .lzo.LzoCodec                              LZO
         *
         * bzip2     是           慢            非常高     org.apache.hadoop.io     是          是    为可分割版本
         *                                                .compress.Bzip2Codec                       使用纯java
         *
         * zlib      否           慢             中等      org.apache.hadoop.io     是          是    Hadoop的默认
         *                                                .compress.DefaultCodec                     压缩编解码器
         *
         * Snappy    否         非常快            低       org.apache.hadoop.io     否          是    Snappy有纯java的移植版,
         *                                                .compress.SnappyCodec                      但是在Spark/Hadoop中不能用
         *
         * 重点:如果要读取单个压缩过的输入,最好不要考虑用spark的封装,而是使用newAPIHadoopFile或者hadoopFile,并指定正确的压缩编解码器.
         */


        /**
         * TODO SparkSql中的结构化数据 - hive
         */

        HiveContext hiveCtx = new HiveContext( sc );
//        Dataset<Row> sql = hiveCtx.sql( "select * from source.test" );
//        Row first = sql.first();
//        System.out.println(first);

        /**
         * TODO SparkSql中的结构化数据 - Json
         */
        DataFrame tweets = hiveCtx.jsonFile( "..\\BigData\\src\\resources\\Person.json" );
        tweets.registerTempTable( "tweets" );
        DataFrame sql1 = hiveCtx.sql( "select user.name,text from tweet" );


    }
}
