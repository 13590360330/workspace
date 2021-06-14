package com.javabasic.service.officialjava.util.collection;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class Old {
    /**
     * 1,Vector类,动态数组
     * 2,Stack类,Vector子类,后进先出堆栈
     * 3,Dictionary类, 类似map的类,键值对
     * 4,HashTable类,Dictionary的具体实现
     * 5,Properties类,属性列表,用于保存值的列表,Hashtable的子类,强大之处在与任何时候都可以将Properties对象写入到流中或者从流中读取,从而对接IO设备Properties非常简便
     */

    /**
     * System.getProperties()方法返回的对象类型就是Properties,尽管Properties类本身不是泛型类,但其中的一些方法是泛型方法
     * Properties定义了以下实例变量:
     * Properties defaults;
     * 该变量包含与Properties对象关联的默认属性列表,Properties定义了以下构造函数:
     * Properties()
     * Properties(Properties propDefault)    使用propDefault作为默认值的Properties对象
     * 对于以上两个构造函数产生的对象,属性列表都是空的
     * 方法定义:参考书 P575
     */

    public static void main(String[] args) {
        Properties properties = new Properties();
        //指定默认属性
        properties.put( "florida", "Tallahassee" );
        properties.put( "Wisconsin", "Madison" );

        Properties properties1 = new Properties( properties );
        properties1.put( "123", "aaaaa" );
        properties1.put( "234", "aaaba" );
        properties1.put( "567", "aaaca" );
        properties1.put( "890", "aaada" );
        properties1.put( "567", "aaaea" );
        //移除
        properties1.remove( "567" );

        Set<Object> objects = properties1.keySet();
        objects.stream().forEach( x -> System.out.println( x + ":" + properties1.getProperty( (String) x ) ) );
        System.out.println();
        System.out.println( properties1.getProperty( "Wisconsin" ) );

        //TODO 获取系统属性
        //System Properties是Property的一个特殊实例
        System.out.println("System:"+System.getProperties());
        //(重点)启动项目时,可以通过-D参数设置System properties: java -D key1=value -cp .com.jenkov.MyApp

        //获取环境变量    环境属性见参考书P474
        System.out.println(System.getProperty( "sun.desktop" ));
        System.out.println("user.name"+System.getProperty( "user.name" ));
        System.out.println("user.home"+System.getProperty( "user.home" ));

        //在linux环境下，在java程序中如何获取环境变量？
        //System.getenv('变量名')
        //注意：getenv全部是小写的


    }
}

/**
 * Properties类最有用的方面之一是:在Properties对象中保存的信息,可以使用store()和load()方法很容易的存储到磁盘中以及从磁盘中加载
 * 任何时候都可以将Properties对象写入到流中或者从流中读取,这使得对于实现简单的数据库来说,属性列表特别方便
 */
class Phonebook {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        /*System.in读取标准输入设备数据（从标准输入获取数据，一般是键盘），其数据类型为InputStream。方法：
　　       int    read()   // 返回输入数值的ASCII码，，该值为0到 255范围内的int字节值。若返回值为-1，说明没有读取到任何字节读取工作结束。
　　       int    read(byte[] b)  // 读入多个字节到缓冲区b中，返回值是读入的字节数
           String readLine()每次读一行。换句话说，用户输入一行内容，然后回车，这些内容一次性读取进来。这种情况下，不论用户输入的是什么东西，通通按照字符串来读入，读入后根据业务进行拆分和处理。*/
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( System.in ) );
        String name, number;
        FileInputStream fin = null;
        boolean changed = false;
        try {
            fin = new FileInputStream( "velocity\\Phonebook.dat" );
            //读取Phonebook.dat
            if (fin != null) {
//                properties.load( fin );
                properties.loadFromXML( fin );
                System.out.println( properties );
                fin.close();
                do {
                    System.out.println( "Enter new name" + "(stop reading): " );
                    name = bufferedReader.readLine();
                    if (name.equals( "quit" )) continue;
                    number = (String) properties.get( name );
                    System.out.println( number );
                } while (!name.equals( "quit" ));
            }
        } catch (FileNotFoundException e) {
            System.out.println( "File Not Found file" );
            //写入Phonebook.dat
            do {
                System.out.println( "Enter new name" + "(stop write): " );
                name = bufferedReader.readLine();
                if (name.equals( "quit" )) continue;
                System.out.println( "enter number: " );
                number = bufferedReader.readLine();
                properties.put( name, number );
                changed = true;
            } while (!name.equals( "quit" ));
            if (changed) {
                FileOutputStream fileOutputStream = new FileOutputStream( "velocity\\Phonebook.dat" );
//                properties.store( fileOutputStream, "text context" );
                //可以不指定utf8
                properties.storeToXML( fileOutputStream,"xml context", "UTF-8" );
                fileOutputStream.close();
            }
        }
    }
}

