package com.source;

import au.com.bytecode.opencsv.CSVReader;
import com.javabasic.domain.Person;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.codehaus.jackson.map.ObjectMapper;
import scala.Tuple2;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SparkCoreFile {
    public static void main(String[] args) {
        SparkConf conf=new SparkConf().setMaster("local[*]").setAppName("SparkCoreFile");
        JavaSparkContext sc = new JavaSparkContext(conf);

        /**
         * spark支持的一些常见文件格式 (Spark的原生输入方式textFile和SequenceFile,  sc.textFile()和sc.sequenceFile())
         * 格式名                    结构化               备注
         * 文本文件                    否               文本文件,每一行一条记录
         * JSON                     半结构化            文本的格式,半结构化,大多数库都要求每一行一条记录
         * CSV                        是               文本的格式
         * SequenceFiles              是               键值对数据的常见Hadoop文件格式
         * Protocol buffers           是               一种快速,节约空间的跨语言格式
         * 对象文件                    是               用来将Spark作业中的数据存储下来以让共享的代码读取,改变类的时候会失效,因为依赖于java序列化
         */


        /**
         * 文本文件
         * sc.wholeTextFiles(args[0]) 这个方法会返回一个pairRDD,其中键是输入文件的文件名,这个方法在处理每个文件表示一个特定时间段内
         * 的数据时非常有用
         * Spark支持读取给定目录中的所有文件,以及在输入路径中使用通配字符(如part-*.txt),在处理批量文件是很有用
         *
         * saveAsTextFile(filePath)   filePath会被当做目录对待,会在这个目录下输出多个文件,这样spark就可以从多个节点上并行输出了,
         * 在这个方法中,我们不能控制数据的哪一部分输出到哪个文件中
         */

//        JavaRDD<String> input = sc.textFile(args[0]);

        /**
         * Json 读取json
         * 处理像Json这样的半结构化数据来说,格式错误是家常便饭,要跟踪错误对象和统计个数
         */
        class ParseJson implements FlatMapFunction<Iterator<String>, Person>{
            @Override
            public Iterable<Person> call(Iterator<String> lines) throws Exception {
                ArrayList<Person> people = new ArrayList<Person>();
                ObjectMapper mapper=new ObjectMapper();
                while (lines.hasNext()){
                    String line = lines.next();
                    try {
                        people.add(mapper.readValue(line,Person.class));   //**有问题
                    }catch (Exception e){
                        //跟踪错误对象和统计个数
                        e.printStackTrace();
                    }
                }
                return people;
            }
        }
        JavaPairRDD<String, String> JsonInput1=sc.wholeTextFiles("..\\BigData\\src\\resources\\Person.json");
        JavaRDD<String> JsonInput=JsonInput1.values();
        JavaRDD<Person> result=JsonInput.mapPartitions(new ParseJson());
//        System.out.println(result.collect());
        List<Person> person1=result.take( 1 );
        //数组流api
//        Arrays.stream( person1.get( 0 ).links ).forEach( x-> System.out.println(x.toString()) );

        /**
         * json 写出json
         */
        class WriteJson implements FlatMapFunction<Iterator<Person>,String>{

            @Override
            public Iterable<String> call(Iterator<Person> people) throws Exception {
                ArrayList<String> text=new ArrayList<String>();
                ObjectMapper mapper=new ObjectMapper();
                while (people.hasNext()){
                    Person person = people.next();
                    text.add( mapper.writeValueAsString( person ) );
                }
                return text;
            }
        }
        JavaRDD<Person> result1=result.filter(x->x.toString().contains( "BeJson" ));
        JavaRDD<String> formatted=result1.mapPartitions( new WriteJson() );
//        formatted.saveAsTextFile( "..\\BigData\\src\\resources\\Person1.json" );

        /**
         * csv文件
         * 读取不包含换行符的csv数据
         */
        class ParseLine implements Function<String,String[]>{

            @Override
            public String[] call(String line) throws Exception {
                CSVReader reader=new CSVReader( new StringReader( line ) );
                return reader.readNext();
            }
        }
        JavaRDD<String> csvFile1=sc.textFile( "..\\BigData\\src\\resources\\aa.csv" );
        JavaRDD<String[]> csvData=csvFile1.map( new ParseLine() );
//        csvData.takeSample( false,1 ).stream().forEach( x-> Arrays.stream( x ).forEach( y-> System.out.println(y) ) );

        /**
         * csv文件  ","作为分隔符, TSV文件是制表符作为分隔符
         * 读取包含换行符的完整csv数据
         */
        class ParseLine1 implements FlatMapFunction<Tuple2<String,String>,String[]>{

            @Override
            public Iterable<String[]> call(Tuple2<String, String> file) throws Exception {
                CSVReader reader=new CSVReader( new StringReader( file._2() ) );
                return reader.readAll();
            }
        }
        JavaPairRDD<String,String> csvData1=sc.wholeTextFiles( "..\\BigData\\src\\resources\\aa.csv" );
        JavaRDD<String[]> keyedRDD=csvData1.flatMap( new ParseLine1() );
        /**
         * 下面是打印出来的数组名（[Ljava.lang.String;@3e5084c9）的含义解释。
         * 【[】：表示一维数组 。
         * 【[[】：表示二维数组 。
         * 【L】：表示一个对象 。
         * 【java.lang.String】：表示对象的类型 。
         * 【@】：后面表示该对象的hashCode。
         */
//        keyedRDD.takeSample( false,1 ).stream().forEach( x-> Arrays.stream( x ).forEach( y-> System.out.println(y) ) );

        /**
         * SequenceFile文件是Hadoop用来存储二进制形式的key-value对而设计的一种平面文件(Flat File)。目前，也有不少人在该文件的基础之上提出了一些HDFS中小文件存储的
         * 解决方案，他们的基本思路就是将小文件进行合并成一个大文件，同时对这些小文件的位置信息构建索引。不过，这类解决方案还涉及到Hadoop的另一种文件格式——MapFile文件。
         * SequenceFile文件并不保证其存储的key-value数据是按照key的某个顺序存储的，同时不支持append操作。在SequenceFile文件中，每一个key-value被看做是一条记录(Record)，
         * 因此基于Record的压缩策略，SequenceFile文件可支持三种压缩类型(SequenceFile.CompressionType):
         * NONE: 对records不进行压缩;
         * RECORD: 仅压缩每一个record中的value值;
         * BLOCK: 将一个block中的所有records压缩在一起;
         *那么，基于这三种压缩类型，Hadoop提供了对应的三种类型的Writer:
         *
         * SequenceFile文件有同步标记,spark可以用它来定位到文件中的某个点,然后再与记录的边界对齐;
         * 在sparkcontext中,可以调用sequenceFile(path,keyClass,valueClass,minPartitions),SequenceFile使用Writable类,
         * 因此keyClass和valueClass参数都必须使用正确的Writable类
         * 读取SequenceFile
         */
        class ConvertToNativeTypes implements PairFunction<Tuple2<Text, IntWritable>,String,Integer> {

            @Override
            public Tuple2<String, Integer> call(Tuple2<Text, IntWritable> record) throws Exception {
                return new Tuple2(record._1.toString(),record._2.get());
            }
        }
        JavaPairRDD<Text,IntWritable> input
                =sc.sequenceFile("..\\BigData\\src\\resources\\SequenceFile\\part-00011",Text.class,IntWritable.class);
        JavaPairRDD<String,Integer> result2=input.mapToPair( new ConvertToNativeTypes() );
//        System.out.println(result2.collect());

        /**
         * 在java中保存SequenceFile 只有saveAsHadoopFile实现了保存为SequenceFile的功能
         * 调用scala保存SequenceFile  用saveAsSequenceFile方法 见SequenceFile.scala
         */
        class ConvertToWritableTypes implements PairFunction<Tuple2<String,Integer>,Text,IntWritable>{

            @Override
            public Tuple2<Text, IntWritable> call(Tuple2<String, Integer> record) throws Exception {
                return new Tuple2<>( new Text( record._1 ),new IntWritable( record._2 ) );
            }
        }
        List<Tuple2<String,Integer>> lt = new ArrayList<>();
        Tuple2<String,Integer> tp1 = new Tuple2<>("panda", 3);
        Tuple2<String,Integer> tp2 = new Tuple2<>("kay", 6);
        Tuple2<String,Integer> tp3 = new Tuple2<>("Snail", 2);
        lt.add(tp1);
        lt.add(tp2);
        lt.add(tp3);
        JavaPairRDD<String,Integer> rdd = sc.parallelizePairs( lt,2 );   //???切片
        JavaPairRDD<Text,IntWritable> result3 = rdd.mapToPair( new ConvertToWritableTypes() );
//        result3.saveAsHadoopFile( filename,Text.class,IntWritable.class, SequenceFileOutputFormat.class );

        /**
         * 对象文件
         * 对象文件看起来就像是对SequenceFile的简单封装,它允许存储只包含值的RDD.和SequenceFile不一样的是,对象文件是使用java序列化写出来的
         * 保存对象文件,只需要在RDD上调用saveAsObjectFile就行.读回对象文件也相当简单,用SparkContext中的objectFile()函数接收一个路径,返回对应的RDD
         * 使用对象文件的主要原因是它们可以用来保存几乎任意对象而不需要额外的工作
         */
        
        
    }
}
