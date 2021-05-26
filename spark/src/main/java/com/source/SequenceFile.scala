package com.source

import org.apache.spark.{SparkConf, SparkContext}

object SequenceFile {
  def main(args: Array[String]): Unit =
  {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("test")
    val sc=new SparkContext(conf)
    val data=sc.parallelize(List(("panda",3),("kay",6),("Snail",2)))
    data.saveAsSequenceFile("..\\BigData\\src\\resources\\SequenceFile")
  }

}
