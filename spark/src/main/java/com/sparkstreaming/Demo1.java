package com.sparkstreaming;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Date;
import java.util.List;

/**
 * TODO 离线数仓数据抽取部分
 * <p>
 * 谨记:
 * 1,日志:JavaDStream里的system.out 在client和cluster模式下不会打印,所以应当用local[*]配合yarn-client模式下先调试好
 * 2,a,spark集群模式下的数据源都应当是hdfs或拉式Flume,
 *     一个服务从kafka的端口读到一条数据就不会再读到了,导致不可靠,
 *     读本地文件时要确保每个服务器该目录下都有相同的文件,且难以确保源数据不会丢失,如服务器挂了(P183)
 *   b,生成的文件也应当是hdfs或S3上的,存在其他的文件系统,当出错时excutor重新写入hdfs时可能会导致多份重复数据
 *   c,checkpoint应当选hdfs,S3或其他自带备份的网络文件系统(P181)
 * 3,从读取到数据的数据源开始,每个Dstream会分批次一次次执行,其他不在Dstream代码中执行的逻辑在整个Driver程序中只会执行一次
 */
public class Demo1 {

    public static void main(String[] args) throws ClassNotFoundException {
        SparkConf conf = new SparkConf().setAppName( "Demo1" );
        /**
         * 我们可以手动设置如何序列化NullWritable类，实现如下：
         * 这样就会使用 org.apache.spark.serializer.KryoSerializer 来序列化 NullWritable， 这样NullWritable 类可以在网络上传送。
         * 且Kryo库效率比java默认的序列化库要高
         */
        conf.set( "spark.serializer", "org.apache.spark.serializer.KryoSerializer" );
        conf.registerKryoClasses( new Class[]{
                Class.forName( "org.apache.hadoop.io.NullWritable" ),
                Class.forName( "org.apache.hadoop.io.LongWritable" )
        } );
        JavaStreamingContext jssc = new JavaStreamingContext( conf, Durations.seconds( 10 ) );
        /**
         * 通过spark上下文获取到hadoop的配置
         */
        Configuration entries = jssc.sparkContext().hadoopConfiguration();

        //打印当前Hadoop配置
//        for (Map.Entry<String, String> str : entries) {
//            System.out.println( str );
//        }

        /**
         * JavaDStream<String>-> JavaPairInputDStream<LongWritable, Text>  的过程是将RDD的每一行由string转变为new Tuple<LongWritable, Text>
         *
         * textFileStream 无法避免hdfs上_COPYING_的问题,所以改用fileStream会好一些
         *
         * 对旧文件的监听只能用PairDStream
         * 如果是路径是,file://{/bigdata/} 则需要每个节点都有同名文件在这个目录下,否则会报找不到文件
         */

        //textFile可以支持模糊匹配,sc.textFile(“hdfs://n1:8020/user/hdfs/input/dt=20130728/hr=*/”)
        jssc.checkpoint( "hdfs://quickstart.cloudera:8020/tmp/checkpoint" );

        JavaPairInputDStream<LongWritable, Text> ds1 = jssc.fileStream( "../bigdata/demo1"
                , LongWritable.class
                , Text.class
                , TextInputFormat.class
                , path -> !path.getName().contains( "_COPYING_" )
                , false );

//        JavaPairInputDStream<LongWritable, Text> ds1 = jssc.fileStream( "..\\BigData\\src\\resources\\tmp\\test\\1"
//                , LongWritable.class
//                , Text.class
//                , TextInputFormat.class
//                , path -> !path.getName().contains( "_COPYING_" )
//                , false );

        JavaPairDStream<LongWritable, Text> lwtjpd = ds1.transformToPair( new Function<JavaPairRDD<LongWritable, Text>, JavaPairRDD<LongWritable, Text>>() {
            @Override
            public JavaPairRDD<LongWritable, Text> call(JavaPairRDD<LongWritable, Text> longWritableTextJavaPairRDD) throws Exception {
                JavaPairRDD<LongWritable, Text> maprdd
                        = longWritableTextJavaPairRDD.mapToPair( new PairFunction<Tuple2<LongWritable, Text>, LongWritable, Text>() {
                    @Override
                    public Tuple2<LongWritable, Text> call(Tuple2<LongWritable, Text> line) throws Exception {
                        String s = line._2().toString().replaceFirst( "\\[", " " );
                        String str = s.substring( 0, s.length() - 1 );
                        return new Tuple2<>( line._1(), new Text( str ) );
                    }
                } );
                return maprdd;
            }
        } );

        /**
         * TODO 外部辅助文件的处理方式
         */
        String path = "/user/bigdata/resources/properties/createdata/tablecolumn";
        JavaSparkContext jsc = jssc.sparkContext();
        List<String> collect = jsc.textFile( path ).collect();
        jsc.broadcast( collect );

        //持久化到内存 只能持久化RDD,DataFrame,Dstream
        lwtjpd.persist( StorageLevel.MEMORY_ONLY() );

        loadDate( lwtjpd, collect, "itl_demo1", "itl_demo1_afc_d", "itl_demo1_afc_d" );
        loadDate( lwtjpd, collect, "itl_demo1", "itl_demo1_quota_management_i", "itl_demo1_quota_management_i" );
        loadDate( lwtjpd, collect, "itl_demo1", "itl_demo1_users_d", "itl_demo1_users_d" );
        loadDate( lwtjpd, collect, "itl_demo1", "itl_demo1_user_info_item_d", "itl_demo1_user_info_item_d" );
        loadDate( lwtjpd, collect, "itl_demo1", "itl_demo1_borrow_money_d", "itl_demo1_borrow_money_d" );
        loadDate( lwtjpd, collect, "itl_demo1", "itl_demo1_repayment_i", "itl_demo1_repayment_i" );

        jssc.start();
        jssc.awaitTermination();
    }

    public static JavaPairDStream<LongWritable, Text> tf(JavaPairDStream<LongWritable, Text> ds, List<String> collect, String dateFile, String tablename) {
        JavaPairDStream<LongWritable, Text> tfdstream = ds.transformToPair( new Function<JavaPairRDD<LongWritable, Text>, JavaPairRDD<LongWritable, Text>>() {
            @Override
            public JavaPairRDD<LongWritable, Text> call(JavaPairRDD<LongWritable, Text> longWritableTextJavaPairRDD) throws Exception {
                JavaPairRDD<LongWritable, Text> tfd
                        = longWritableTextJavaPairRDD.mapToPair( new PairFunction<Tuple2<LongWritable, Text>, LongWritable, Text>() {
                    @Override
                    public Tuple2<LongWritable, Text> call(Tuple2<LongWritable, Text> line) throws Exception {
                        String[] sp = line._2().toString().split( "\\001" );
                        String[] str = new DemoSplit( sp, dateFile, tablename, collect ).getStr();
                        String joinstr = StringUtils.join( str, "\001" );
                        return new Tuple2<LongWritable, Text>( line._1(), new Text( joinstr ) );
                    }
                } );
                return tfd;
            }
        } );
        return tfdstream;
    }

    /**
     * TODO JavaDStream<String>使用saveAsHadoopFiles(),卸载数据时可以使用
     */
    public static void loadDate(JavaPairDStream<LongWritable, Text> transformdstream, List<String> collect, String datafile, String tablename, String hadoopfilepath) {
        JavaPairDStream<LongWritable, Text> afc = tf( transformdstream, collect, datafile, tablename );

        /**
         * TODO saveAsHadoopFiles和saveAsTextFile等都应该结合分区表来使用,因为会自动创建文件夹,最好是外部表
         * TODO 当文件落到分区字段end_dt之后,"end_dt=xxx"等号前后不要有空格,之后执行msck repair table itl.itl_demo1_quota_management_i的命令刷新元数据(shell和sparksql都可以)
         */
        String hadooppath = "/user/hive/warehouse/itl.db/" + hadoopfilepath + "/sparkdirctory=" + hadoopfilepath;
//        String hadooppath = "E:\\日常文件\\临时目录\\新建文件夹\\" + hadoopfilepath;

        /**保存为其他格式,如Avro,SequenceFile,Parquet.....,需要继承OutputFormat类,详情见P174*/

        /**参数,1路径,2后缀,3keyclass,4value.class,5输出格式-必须继承自OutputFormat*/
        //ParquetOutputFormat<T> 继承自 FileOutputFormat<Void, T> 的文件保存不能转 JavaPairInputDStream<LongWritable, Text>;  而SequenceFileOutputFormat却可以

        //去掉key值,key-value换位
        JavaPairDStream<Text, NullWritable> nwtjpd = afc.transformToPair( new Function<JavaPairRDD<LongWritable, Text>, JavaPairRDD<Text, NullWritable>>() {
            @Override
            public JavaPairRDD<Text, NullWritable> call(JavaPairRDD<LongWritable, Text> longWritableTextJavaPairRDD) throws Exception {
                JavaPairRDD<Text, NullWritable> maprdd
                        = longWritableTextJavaPairRDD.mapToPair( new PairFunction<Tuple2<LongWritable, Text>, Text, NullWritable>() {
                    @Override
                    public Tuple2<Text, NullWritable> call(Tuple2<LongWritable, Text> line) throws Exception {
                        return new Tuple2<>( new Text( line._2 ), NullWritable.get() );
                    }
                } );
                return maprdd;
            }
        } );

        nwtjpd.saveAsHadoopFiles( hadooppath
                , "txt"
                , Text.class
                , NullWritable.class
                , TextOutputFormat.class );
    }

    /**
     * TODO rdd.saveAsTextFile( hadooppath )
     */
    public static void inputDate(JavaPairDStream<LongWritable, Text> transformdstream, List<String> collect, String datafile, String tablename, String hadoopfilename) {
        JavaPairDStream<LongWritable, Text> afc = tf( transformdstream, collect, datafile, tablename );
        Date date = new Date();
        long msec = date.getTime();
        String hadooppath = "/user/hive/warehouse/itl.db/" + hadoopfilename + msec;
//        String hadooppath = "E:\\日常文件\\临时目录\\新建文件夹\\" + hadoopfilename;
        afc.foreachRDD( rdd -> {
            rdd.saveAsTextFile( hadooppath );
        } );
    }
}
