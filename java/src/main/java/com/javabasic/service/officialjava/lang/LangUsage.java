package com.javabasic.service.officialjava.lang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO java.lang定义的类和接口
 * 1.抽象类Number是一个超类,用于封装byte,short,int,long,float以及double的类派生自这个超类,
 * Number定义了一些抽象方法,以及各种不同的数字格式返回对象的值,例如doubleValue()返回double类型等...
 * Number包含用于保存各种基本数值类型显示值的具体子类:Double,float,Byte,Short,Integer以及long(分别是对应类型的封装器)
 * <p>
 * 2.Float和Double都提供了isInfinite()和isNaN()方法,d1为一个对象
 * d1.isInfinite()测试值在数量上为无穷大或者无穷小
 * d1.isNaN()方法,如果测试的值不是数字,那么返回true
 * <p>
 * 3.将数字的字符串表示形式转换成相应的内部二进制格式
 * Byte,Short,Integer以及Long类分别提供了parseByte(),parseShort(),parseInt(),parseLong()方法,这些方法返回数值字符串的
 * byte,short,int,long的等价形式(Float和Double类也有类似的方法)
 * <p>
 * 4.为了将整数转换为十进制字符串,可以使用Byte,Short,Integer或者Long类定义的toString()方法
 * Integer和Long类还定义了toBinaryString(),toHexString()和toOctalString()方法,这些方法相应地将数值转换为二进制,十六进制或八进制字符串
 * Integer.toHexString(123)
 * <p>
 * 5.Character是char类型的简单封装器,方法charValue()
 * <p>
 * 6.Boolean是封装布尔值的非常轻量级的封装器
 * <p>
 * 7.执行其他程序(进程)Runtime.exec()或者ProcessBuilder,参考书 P467
 * <p>
 * 8.System.currentTimeMillis() 返回自1970年1月1日午夜到当前时间的毫秒数
 * <p>
 * 9.arraycopy() 拷贝数组
 * <p>
 * 10.System.getProperties() 查看环境变量
 * <p>
 * 11.Object类中的clone方法,只有实现了Cloneable接口的类才可以被复制
 * <p>
 * 12.Class类封装了类或接口的运行时状态,Class是java的内置类,Class类型的对象是加载类时自动创建的,不能显式地声明Class对象,
 * 通过Object类定义的getClass()/getSuperclass()方法来获取Class对象,也可以Class<?> c = Meta.class；
 * <p>
 * 13.Math类和及各种数学计算函数方法
 */
public class LangUsage {
    public static void main(String[] args) {
        /**
         * TODO 内存管理
         */
        Runtime r = Runtime.getRuntime();
        long mem1 = 0, men2;
        Integer someints[] = new Integer[1000];
        System.out.println( "Total memory1 is: " + r.totalMemory() / (1024 * 1024) + "MB" );
        System.out.println( "Total memory1 is: " + (r.totalMemory() - r.freeMemory()) );
        r.gc();
        System.out.println( "Total memory2 is: " + r.totalMemory() / (1024 * 1024) + "MB" );
        System.out.println( "used memory2 is: " + (r.totalMemory() - r.freeMemory()) );
        for (int i = 0; i < 1000; i++) {
            someints[i] = new Integer( i );
            //数组空间已经被分配好了,不会循环减少
//            System.out.println( "used memory" + i + " is: " + r.freeMemory() );
        }

        long m = 0;
        ArrayList someints2 = new ArrayList();
        for (int i = 0; i < 1000; i++) {
            someints2.add( i );
            if (m != r.freeMemory()) {
//                System.out.println( r.freeMemory() + "," + someints2.size() );
            }
            m = r.freeMemory();
        }
        System.out.println( "used memory is: " + r.freeMemory() );

        /**
         * String
         */
        String aaa = "asdfaehasdjazasdpa12asd3a1asd4";
        //limit可以看做是分割之后数组的长度,limit为负或0分割无数次,1不分割,2分割一次,3分割2......
        String[] as = aaa.split( "asd", 3 );
        System.out.println( "as.length:" + as.length );
        Arrays.stream( as ).forEach( System.out::println );

        /**
         * TODO Process 执行其他进程
         *
         * 调用shell
         * String bash="sh " + .....
         * Process exec = Runtime.getRuntime().exec( bash );
         * int i = exec.waitFor();
         *
         * Process.waitFor() 操作系统错误代码0：成功, 当前线程等待，如有必要，一直要等到由该 Process 对象表示的进程已经终止。但是如果我们在调用此方法时，如果不注意的话，很容易出现主线程阻塞，
         * Process也挂起的情况。在调用waitFor() 的时候，Process需要向主线程汇报运行状况，所以要注意清空缓存区，即InputStream和ErrorStream，在网上，很多只提到处理InputStream，
         * 忽略了ErrorStream。以下一段代码，贴出来，仅做参考。
         *
         * Process.exitValue() 操作系统错误代码0：成功 采用非阻塞的方式返回，如果没有立即拿到返回值，则抛出异常
         *
         * 执行笔记本端的程序,可以使用ProcessBulid,器本质也是使用Process,proc.start()返回的就是Process,不过ProcessBulid提供了更多控制
         */
        ProcessBuilder proc = new ProcessBuilder( "notepad.exe",
                "E:\\日常文件\\知识点整理\\CDH\\DW\\project\\中信银行\\BigData\\src\\resources\\tmp\\aa.csv" );
        try {
            proc.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}