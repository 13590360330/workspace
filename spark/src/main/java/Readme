1,scala类和方法有时候会有[],这里的中括号就是类型参数化,这是泛型，Java 中是尖括号

2,在package org.apache.spark.api.java中例如下
def map[R](f : org.apache.spark.api.java.function.Function[T, R]) : org.apache.spark.api.java.JavaRDD[R] = { /* compiled code */ }
R是泛型方法map参数化类型，T是Function的形参的参数化类型，R是结果的参数化类型
   scala常用语法
   1,匿名函数的写法
   (参数:类型) => 函数体    (x:Int,y:Int) => x+y或(f:T=>U)
   2,类
   Scala中的类不声明为public，一个Scala源文件中可以有多个类。
   Scala 的类定义可以有参数，称为类参数，如上面的 xc, yc，类参数在整个类中都可以访问(功能类似java构造函数)。
   例:
   class Point(xc: Int, yc: Int) {
      var x: Int = xc
      var y: Int = yc
      }
   创建实例:val pt = new Point(10, 20);
   例如 MapPartitionsRDD算子U和T是泛型声明,()内的是实例化的传入参数
   private[spark] class MapPartitionsRDD[U:ClassTag,T:ClassTag](
        prev:RDD[T],f:(TaskContext,Int,Iterator[T])=>Iterator[U],preservesPartitioning:Boolean = false) extends RDD[U](prev){}
   3,方法 : def functionName (参数列表) : [return type] = {.........}
   Scala具备给参数提供默认值的能力，这样调用者就可以忽略这些具有默认值的参数.
   def log(message: String, level: String = "INFO") = println(s"$level: $message")
   4,柯里化函数(Spark最佳实践 P206)
   柯里化是把接受多个参数的函数变换成接受单个参数(最初函数的第一个参数)的函数,并且返回接受余下的参数而且返回结果的新函数的技术
   例如 def plainOldSum(x:Int,y:Int) = x + y
        可以转换成 def plainOldSum(x:Int)(y:Int) = x + y
                  调用时可以val a = plainOldSum(1)(2),
                  或者val b = plainOldSum(1)_
                  val c = b(2)

3,SparkContext.addFile(path):构建一个文件列表,让每一个工作节点在spark作业中下载列表中的文件(P98),这个方法在执行外部程序时很好用(P98)
,pipe()执行外部程序
String path = "/home/bigdata/bigdata/properties/createdata/tablecolumn";
JavaSparkContext jsc = jssc.sparkContext();
jsc.addFile( path );
SparkFiles.getRootDirectory();
/**
 * 不知道是不是伪分布式的原因,自己测试一直失败
 * addFile方法可以接收本地文件（或者HDFS上的文件），甚至是文件夹
 * （如果是文件夹，必须是HDFS路径,如果path是一个目录，那么我们可以设置recursive为true）
 * ，然后Spark的Driver和Exector可以通过SparkFiles.get()方法来获取文件的绝对路径
 * （Get the absolute path of a file added through SparkContext.addFile()）
 *
 * addFile把添加的本地文件传送给所有的Worker，这样能够保证在每个Worker上正确访问到文件。另外，Worker会把文件放在临时目录下。
 * 因此，比较适合用于文件比较小，计算比较复杂的场景。如果文件比较大，网络传送的消耗时间也会增长。
 */


4,spark内核讲解(Spark最佳实践)
  一,RDD(P77)
  (1),RDD 弹性分布式数据集,从操作上看RDD最像Array和List,里面的数据都是平铺的,可以顺序遍历
  (2),RDD 是只读的,一旦生成,内容就不能修改了
  (3),RDD 可指定缓存在内存中,一般计算都是流水式生成,使用RDD,新的RDD生成之后,旧的不再使用,并被java虚拟机回收掉,但是后续有多个计算依赖某个RDD,我们可以
  让这个RDD缓存在内存中,避免重复计算
  (4),如果未做缓存,RDD有其自身的高可靠性机制,通过记录足够的计算过程,在需要时(比如因为节点故障导致内容失效)重新从头或从某个镜像重新计算来恢复的

 二,RDD的定义(P78)
 一个RDD对象,包含如下5个核心属性,spark调度和计算都基于这5个属性,各种RDD都有自己实现的计算,用户也可以方便地实现自己的RDD
 (1),一个分区列表,每个分区里是RDD的部分数据(或称数据块)
 (2),一个依赖列表,存储依赖的其他RDD
 (3),一个名为compute的计算函数,用于计算RDD各分区的值
 (4),分区器(可选),用于键/值类型的RDD,比如某个RDD是按散列来分区的
 (5),计算各分区时优先的位置列表(可选),比如从HDFS上的文件生成RDD时,RDD分区的位置优先选择数据所在的节点,这样可以避免数据移动带来的开销

三,RDD及其常见子类的继承关系
   RDD <- [MapPartitionsRDD,CoalescedRDD,HashPartitioner]

四,RDD算子(Spark最佳实践 P54)
   Transformation操作,生成新的RDD,不会进行真正的计算
   (1),map
   (2),flatmap
   (3),filter
   (4),mapPartitions
   (5),mapPartitionsWithIndex
   (6),sample
   (7),union  合并2个RDD
   (8),distinct
   (9),groupByKey
   (11),reduceByKey
   (12),sortByKey
   (13),join
   Action操作,Transformation代表计算的中间过程,一个RDD生成新的RDD;而Action代表计算的结束,一次Action调用之后,不再生成新的RDD,结果返回到Driver程序(解释来自 P82)
   (14),reduce
   (15),collect
   (16),count
   (17),first
   (18),take
   (19),saveAsTextFile   可以保存到hdfs或本地
   (20),saveAsSequenceFile
   (21),countByKey
   (22),foreach

以下的内容,详细内容参见<<Spark最佳实践>>
五,DAG(多个Stage(按shuffle切分)对应一个DAG(job,按action切分),一个Stage对应多个RDD,一个分区对应一个task任务(每个task需要一个线程处理))
  1,定义:
  通过Transformation操作RDD能产生新的RDD,他们之间存在依赖关系,这种依赖关系分为:
  窄依赖:依赖上级RDD的部分分区,
  Shuffle依赖:依赖上级RDD的所有分区
  2,原理
  窄依赖：父RDD的分区和子RDD的分区关系是：一对一,窄依赖不会发生Shuffle，执行效率高
         ，spark框架底层会针对多个连续的窄依赖执行流水线优化，把多个Task合并成一个Task来执行。从而提高性能。例如 map  flatMap等方法都是窄依赖方法
  Shuffle依赖：父RDD的分区和子RDD的分区关系是：一对多,一个子RDD分区的数据来源于父RDD的多个分区,宽依赖会产生shuffle，会产生磁盘读写，无法优化。
         例如:几乎所有<key,value>类型的RDD操作,都涉及按key对RDD成员进行重组,将具有相同key但分布在不同节点上的成员聚合到一个节点上,以便对它们的value进行操作,这个重组过程就是Shuffle操作
         Haddop的MapReduce过程中的Shuffle和这个原理一样,(shuffle目录设置P83)
  3.DAG。有向无环图，当一整条RDD的依赖关系形成之后，就形成了一个DAG。一般来说，一个DAG，最后都至少会触发一个Action操作，触发执行。可以这样理解：一个DAG对应一个Spark的Job。
  4.Stage。一个DAG会根据RDD之间的依赖关系进行Stage划分，流程是：以Action为基准，向前回溯，遇到宽依赖，就形成一个Stage。遇到窄依赖，则执行流水线优化（将多个连续的窄依赖放到一起执行）
  5.task。任务。一个分区对应一个task。每个task会在spark服务器的一个进程里来运行。可以这样理解：一个Stage是一组Task的集合 

六,Spark Streaming
  1,DStream:
  (a),SparkStreaming 使用离散化流作为抽象,叫做DStream,每个DStream包含了许多个批次,也就包含了多个RDD
  (b),DStream之间可以join()和union()整合不同输入源的数据
  (c),DStream支持2种操作
      转化操作:会生成新的DStream
      输出操作:可以把数据写入外部系统(目录,hdfs,数据库.....P174,由于saveAs...File方法会在一个文件写完时,自动的将其原子化地移动到最终位置上,以此确保每个输出文件只存在一份,因此存为hdfs文件(自动备份)的
      可靠性最高,数据库的可能会重复(foreachRDD方法P174),原因P181节点容错,执行器进程自动重启,从检查点读数据后,重新计算那部分数据)

  3,转化操作(需要注意,每个DStream都包含许多RDD,例如每个数据源对应一个DStream,所以对应多个RDD)
  无状态转化操作:每个批次计算,不依赖前面的批次数据
  有状态转化操作:需要使用之前批次的数据或者是中间结果来计算当前批次的数据,有状态转化操作包括基于滑动窗口的转化操作和追踪状态变化的转化操作

  无状态转化操作,(每个批次RDD自己的转化)(P167)
  针对键值对的DStream转化操作(比如reduceByKey()),在java中需要通过mapToPair()创建出一个JavaPairDStream才能使用
  (1).map
  (2).flatMap()
  (3).filter()
  (4).repartition()
  (5).reduceByKey()
  (6),groupByKey()
  transform():高级操作符,将DStream中的RDD按transform()中的规则转化成另一个RDD(P168)

  有状态的转化操作(有状态的Transformation操作)
  (a),基于窗口的转化操作(P170)
      通过整合多个批次的结果,计算出整个窗口的的结果
      JavaDStream dst=accessLogsDStream.window(Durations.seconds(30),Durations.seconds(10))
      int result=dst.count()
      Durations.seconds(30)--窗口步长,Durations.seconds(10)--滑动步长,accessLogsDStream中的RDD会按照windowDuration(窗口步长)/batchInterval(滑动步长)
  数目个数的RDD,来计算生成新的RDD,即原来有6个10秒步长的RDD,现在会新的DStream里会只有2个DStream,然后再这么重复一次获得result,也就是这是一个归约的过程,
  比window效率更高的窗口操作是reduceByWindow()和reduceByKeyAndWindow()(P171)
  (b),有状态的Transformation操作必须开启检查点

七,检查点(P181)(缺点增加了批次计算时间,并且降低了吞吐量)
  (1),检查点(checkpointing,不是默认的容错机制,但是是spark streaming中用来保障容错性的主要机制,在有状态的Transformation操作和防止计算失败重新计算(比如节点失效)必须开启)
  检查点,也就是把数据存储到可靠文件系统(如HDFS,S3)上的机制,这也是Spark Streaming用来实现不间断工作的主要方式
  ,一般设置每5-10个批次的数据就保存一次,在恢复数据时,Spark Streaming 只需要回溯到上一个检查点即可
  (2),猜测检查点会根据windowDuration(窗口步长)/batchInterval(滑动步长),即每个窗口步长内的计算数据进行保存

八,驱动程序容错(P183)
  --supervise 自动重启失败的驱动器程序(P182)

九,提升性能
  (1),批次和窗口大小,可以从10s开始测试,下限一般为0.5秒,streaming界面处理的事件变长了,证明性能达到极限
  (2),并行度(P185)
      增加接收器数据(数据源)
      接收器的数目无法增加,将接收到的数据显示重分区(并发处理)
      提高聚合计算的并行度
  (3),垃圾回收和内存使用

十,运行模式(Spark最佳实践 P61)
   1,Standalone  最简单的一种集群模式,调度任务只支持先进先出FIFO
   2,Mesos
   3,yarn   将任务使用yarn调度管理
     (a),client    交互模式,会在任务提交的服务器窗口输出日志,多用这个
     (b),cluster
      Resource Manager在集群中的某个NodeManager上运行ApplicationMaster，该AM同时会执行driver程序。紧接着，会在各NodeManager上运行CoarseGrainedExecutorBackend来并发执行应用程序。
      应用程序的结果，会在执行driver程序的节点的stdout中输出，而不是打印在屏幕上,需要在yarn或hdfs上查看日志

十一,spark 7*24小时的理解:
   7*24是说每周24小时都为你服务，没有周末，也没有节假日,稳定运行

十二,spark的常见配置及常识(E:\日常文件\知识点整理\CDH\hadoop\CDH-hadoop配置.txt)
jar目录:LD_LIBRARY_PATH=CDH-5.13.0-1.cdh5.13.0.p0.29/lib/hadoop/lib


十三,补充
addJar添加在这个SparkContext实例运行的作业所依赖的jar。，其函数原型如下：

def addJar(path: String)
　　path：可以是本地文件（local file）、HDFS文件（其他所有的Hadoop支持的文件系统也可以）、HTTP、 HTTPS 或者是FTP URI文件等等。

　　其实Spark内部通过spark.jars参数以及spark.yarn.dist.jars函数传进去的Jar都是通过这个函数分发到Task的。

十四,改造demo1,优化Table类,表之间的关系写入文件传到hdfs,然后broadcast出去,要能保证稳定运行1个小时
    ,将sparkconf和sparkcontext写成独立的类,demo1以后就作为学习,升级案例