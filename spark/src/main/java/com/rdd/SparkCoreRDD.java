package com.rdd;

import com.javabasic.dao.IntegerComparator;
import com.javabasic.domain.AvgCount;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.util.*;

public class SparkCoreRDD {

    public static void main(String[] args) {
        SparkConf conf=new SparkConf().setMaster("local[*]").setAppName("SparkCoreRDD");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //读取输入数据
        System.out.println(args[0]);
        JavaRDD<String> input = sc.textFile("..\\BigData\\src\\resources\\aa.csv");
        /**
         * java函数式接口:Function<T,R>        {R call(T)},
         *               Function2<T,T,R>     {R call(T1,T2)},
         *               FlatMapFunction<T,R> {Iterable<R> call(T)}
         * 一个RDD转换操作：map(),flatMap(),filter(),distinct(),rdd.sample(false,0,5)对RDD采样,false不替换
         * 二个RDD转换操作：union() 合并，intersection() 交集，subtract() 差集，rdd1.cartesian(rdd2) 与另一个RDD的笛卡尔积
         */

        //切分单词
        JavaRDD<String> words = input.flatMap((x) -> Arrays.asList(x.split(",")));
        /**
         * 持久化 persist()，unpersist()把持久化的RDD从缓存中移除,默认情况下persist()会把数据以序列化的形式缓存在JVM的堆空间中
         * 级别                   使用空间   CPU时间  是否在内存中  是否在磁盘上   注
         * MEMORY_ONLY()           高         低         是           否
         * MEMORY_ONLY_SER()       低         高         是           否
         * MEMORY_AND_DISK()       高         中等       部分         部分      如果数据在内存中放不下，则溢写到磁盘上
         * MEMORY_AND_DISK_SER()   低         高         部分         部分      如果数据在内存中放不下，则溢写到磁盘上，在内存中存放序列化后的数据
         * DISK_ONLY()             低         高         否           是
         */
        words.persist(StorageLevel.DISK_ONLY()); //words在第一次调用行动操作前就调用了persist()方法，persist()调用本身不会触发强制求值

        /**
         * rdd={1,2,3,3}
         * 行动操作：collect(),count(),take(),top(),aggregate()还需学习
         * countByValue()                    各元素在RDD中出现的次数 rdd.countByValue()=={(1,1),(2,1),(3,2)}
         * rdd.takeOrdered(2)(myOrdering)    按照提供的顺序返回最前面的2个元素
         * rdd..takeSample(false,1)          返回任意一个数据
         * rdd.reduce(x->(x+1))              并行整合RDD中所有数据
         * rdd.fold(0)(x->(x+1))             和reduce()一样，但是需要提供初始值
         * rdd.foreach(func)                 对rdd中的每个元素使用给定的函数
         */
        //第一列求和
        JavaRDD<Integer> num = input.map(x-> new Integer(x.split(",")[0]));
        num.persist(StorageLevel.DISK_ONLY());
        Integer sum =input.map(x-> new Integer(x.split(",")[0])).reduce((x, y)->(x+y));

        //统计个数*****
        //Spark的java API让用户使用scala.Tuple2类来创建二元组，可以通过._1()和._2()方法访问其中的元素
        JavaPairRDD<String,Integer> count = words.mapToPair(x->new Tuple2(x,1));
        JavaPairRDD<String, Integer> count1=count.reduceByKey((x, y)->(x+y));

        /**
         * Java中正对专门类型的函数接口
         * DoubleFlatMapFunction<T>   等价函数 Function<T,Iterable<Double>>      用于flatMapToDouble,以生成DoubleRDD
         * DoubleFunction<T>          等价函数 Function<T,Double>                用于mapToDouble,以生成DoubleRDD
         * PairFlatMapFunction<T,K,V> 等价函数 Function<T,Iterable<Tuple2<K,V>>> 用于flatMapToPair,以生成PairRDD<K,V>
         * PairFunction<T,K,V>        等价函数 Function<T,Tuple2<K,V>>           用于mapToPair,以生成PairRDD<K,V>
         */
        JavaDoubleRDD result=num.mapToDouble(x->x);
        //查询这两个方法的作用
//        System.out.println(result.mean());
//        System.out.println(result.variance());

        /**
         * 键值对 PairRDD的转化操作 rdd={(1,2),(3,4),(3,6)}
         * 使用java从内存数据集创建pairRDD,需要使用SparkContext.parallelizePairs()
         * rdd.reduceByKey((x,y)->(x+y))    {(1,2),(3,10)}                         合并具有相同键的值
         * rdd.groupByKey()                 {(1,[2]),(3,[4,6])}                    对具有相同键的值进行分组
         * combineByKey                                                            使用不同的返回类型合并具有相同键的值
         * rdd.mapValues(x->(x+1))          {(1,3),(3,5),(3,6)}                    对pairRDD中的每个值应用一个函数而不改变键
         * rdd.flatMapValues(x->(x to 5))   {(1,2),(1,3),(1,4),(1,5),(3,4),(3,5)}  对pairRDD中的每个值应用一个返回迭代器的函数，然后对返回的每个元素都生成一个对应原键的键值对记录，通常用于符号化
         * rdd.keys()                       {1,3,3}                                返回一个仅包含键的RDD
         * rdd.values()                     {2,4,6}                                返回一个仅包含值的RDD
         * rdd.sortByKey()                  {(1,2),(3,4),(3,6)}                    返回一个根据键排序的RDD,sortByKey()函数接收一个叫作ascending的参数,默认为true升序
         */
        IntegerComparator comp=new IntegerComparator();
//        JavaPairRDD<String, Integer> soutcount=count.sortByKey( new Comparator<String>() {
//                             @Override
//                             public int compare(String o1, String o2) {
//                                 return String.valueOf(o1).compareTo(String.valueOf(o2));
//                             }
//                         });
//        System.out.println("soutcount:"+soutcount.collect());

        /**
         * 针对两个pairRdd的转化操作(rdd={(1,2),(3,4),(3,6)}) other={(3,9)})
         * rdd.subtractByKey(other)        {(1,2)}                                 删掉RDD中键与otherRdd中的键相同的元素
         * rdd.join(other)                 {(3,(4,9)),(3,(6,9))}                   对两个RDD进行内连接
         * rdd.rightOuterJoin(other)       {(3,(Some(4),9)),(3,(Some(6),9)}        对两个RDD进行连接操作，确保第一个RDD的键必须存在（右外连接）
         * rdd.leftOuterJoin(other)        {(1,(2,None)),(3,(4,Some(9))),(3,(6,Some(9)))}  对两个RDD进行连接操作，确保第二个RDD的键必须存在（左外连接）
         * rdd.cogroup(other)              {(1,([2],[])),(3,([4,6],[9]))}          将两个RDD中拥有相同键的数据分组到一起
         */

        JavaPairRDD<String, Integer> count2=count1.filter(x->x._1().equals("a"));

        /**
         * java中使用combineByKey()求每个键对应的平均值
         */
        List<Tuple2<String,Integer>> lt = new ArrayList<>();
        Tuple2<String,Integer> tp1 = new Tuple2<>("coffee", 1);
        Tuple2<String,Integer> tp2 = new Tuple2<>("coffee", 2);
        Tuple2<String,Integer> tp3 = new Tuple2<>("panda", 3);
        Tuple2<String,Integer> tp4 = new Tuple2<>("coffee", 9);
        lt.add(tp1);
        lt.add(tp2);
        lt.add(tp3);
        lt.add(tp4);
        JavaPairRDD<String,Integer> nums = sc.parallelizePairs(lt,2);
        Function<Integer, AvgCount> createAcc=new Function<Integer, AvgCount>() {
            @Override
            public AvgCount call(Integer x) {
                return new AvgCount(x,1);
            }
        };
        //以下是同一个分区里的统计，total_是value值的和，num_是计数和
        Function2<AvgCount,Integer,AvgCount> addAndCount =(AvgCount a,Integer x)->{
            a.total_+=x;
            a.num_+=1;
            return a;
        };
        //以下是不同分区里的统计，total_是value值的和，num_是计数和
        Function2<AvgCount,AvgCount,AvgCount> combine=(AvgCount a,AvgCount b)->{
            a.total_+=b.total_;
            a.num_+=b.num_;
            return a;
        };
        AvgCount initial = new AvgCount(0,0);
        JavaPairRDD<String,AvgCount> avgCounts=
                nums.combineByKey(createAcc,addAndCount,combine);
        Map<String,AvgCount> countMap=avgCounts.collectAsMap();
//        countMap.entrySet().stream().forEach(x-> System.out.println(x.getKey()+":"+x.getValue().avg()));

        /**
         * 分区repartition()和coalesce()
         * Spark提供了repartition()函数,它会把数据通过网络进行混洗,并创建出新的分区集合,切记对数据进行重新分区是代价相对比较大的操作
         * Spark中优化版的repartition(),--coalesce()  在java或scala中rdd.partitions.size,python中rdd.getNumPartitions查看分区数
         *
         *使用情景
         * 假设RDD有N个分区，需要重新划分成M个分区：
         * 1,N < M: 一般情况下N个分区有数据分布不均匀的状况，利用HashPartitioner函数将数据重新分区为M个，这时需要将shuffle设置为true。
         * 因为重分区前后相当于宽依赖，会发生shuffle过程，此时可以使用coalesce(shuffle=true)，或者直接使用repartition()。
         * 2,如果N > M并且N和M相差不多(假如N是1000，M是100): 那么就可以将N个分区中的若干个分区合并成一个新的分区，最终合并为M个分区，
         * 这是前后是窄依赖关系，可以使用coalesce(shuffle=false)。
         * 3,如果 N> M并且两者相差悬殊: 这时如果将shuffle设置为false，父子ＲＤＤ是窄依赖关系，他们同处在一个Ｓｔａｇｅ中，
         * 就可能造成spark程序的并行度不够，从而影响性能，如果在M为1的时候，为了使coalesce之前的操作有更好的并行度，可以将shuffle设置为true。
         *
         * 总结
         * 如果传入的参数大于现有的分区数目，而shuffle为false，RDD的分区数不变，也就是说不经过shuffle，是无法将RDDde分区数变多的。
         *
         * 分区操作
         * mapPartitions和map
         * map是对rdd中的每一个元素进行操作，而mapPartitions(foreachPartition)则是对rdd中的每个分区的迭代器进行操作。
         * 如果在map过程中需要频繁创建额外的对象(例如将rdd中的数据通过jdbc写入数据库,
         * map需要为每个元素创建一个链接而mapPartition为每个partition创建一个链接),则mapPartitions效率比map高的多。
         */

        /**
         * 数据分组
         * rdd.groupByKey()             [K,Iterable[V]]                         使用键对数据进行分组
         * rdd.cogroup(other)           {(1,([2],[])),(3,([4,6],[9]))}          将两个RDD中拥有相同键的数据分组到一起
         */

        /**
         * Pair RDD的行动操作(以键值对集合{(1,2),(3,4),(3,6)}为例)
         * rdd.countByKey()             {(1,1),(3,2)}                           对每个键对应的元素分别计数
         * rdd.collectAsMap()           Map{(1,2),(3,6)}                        将结果以映射表的形式返回,以便查询,因为是生成map,相同键后者会覆盖前者(3,4)会被(3,6)覆盖
         * rdd.lookup(3)                [4,6]                                   返回给定键对应的所有值
         */
        Map<String,Integer> numsToMap=nums.collectAsMap();
        System.out.println(nums.collect());
        //集合流api
        numsToMap.entrySet().stream().forEach(x-> System.out.println(x.getKey()+":"+x.getValue()));

        /**
         * 键值对rdd分区  根据键值进行的分区,键相同的元素,hash值也相同,会被哈希到同一台机器上
         * 分区方式:HashPartitioner(100),RangePartitioner(100)
         * val userData=sc.sequenceFile[UserID,UserInfo]("hdfs://...").partitionBy(new HashPartitioner(100)).persist()
         * 100表示分区数目,它会控制之后对这个RDD进行进一步操作时有多少任务会并行执行,总的来说,这个值至少应该和集群中的总核心数一样,persist()持久化,避免每次使用userData要将数据hash到相应的机器,混洗
         * userData.partitioner    Option[spark.Partitioner]=Some(......)   获取RDD的分区方式
         *
         * Spark的许多操作都引入了将数据根据键跨节点进行混洗的过程,就Spark1.0而言,能够从数据分区中获益的操作有
         * cogroup(),groupWith(),join(),leftOuterJoin(),rightOuterJoin(),groupByKey(),reduceByKey(),combineByKey()以及lookup()
         * 除了lookup()以上的操作都会为生成的结果RDD设置好分区方式,还有partitionBy(),sort(),mapValues(),flatMapValues(),spark1.x版本的只有这些
         * 对于二元操作,输出数据的分区方式取决于父RDD的分区方式,如果其中一个父RDD已经设置过分区方式,那么结果就会采用那种分区方式;如果两个父RDD都设置过分区方式,
         * 结果RDD会采用第一个父RDD的分区方式
         */



    }
}
