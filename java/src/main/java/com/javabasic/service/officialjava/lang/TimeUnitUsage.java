package com.javabasic.service.officialjava.lang;


import com.javabasic.service.thinkinginjava.io.Logs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * TODO [TimeUnit枚举及各种时间转换方法,线程休眠:TimeUnit.SECONDS.sleep( 5 )]
 * 1,TimeUnit包含各种时分秒转换的方法
 */
public class TimeUnitUsage {
    public static void main(String[] args) throws InterruptedException {

        Logs.getLogs( "TimeUnitUsage" );

        System.out.println( "currentTimeMillis:" + System.currentTimeMillis() );
        long l = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep( 5 );      //休眠5s
        long l1 = System.currentTimeMillis();
        long l2 = l1 - l;
        System.out.println( TimeUnit.MILLISECONDS.toSeconds( l2 ) );
        System.out.println( "l2 : " + TimeUnit.SECONDS.convert( l2, TimeUnit.MILLISECONDS ) ); //毫秒转换成秒

        System.out.println( TimeUnit.SECONDS.toMinutes( 60000 ) );
        System.out.println( TimeUnit.SECONDS.toDays( 60000 ) );
        System.out.println( TimeUnit.SECONDS.toHours( 60000 ) );
        System.out.println( "------------------------------------------" );

        /**
         * TODO Date类
         * 用下面的方法,可以获得任何时间的转换和时间的计算
         */
        Date date1 = new Date();     //获取当前时间
        Date date = new Date( "2020/09/12 17:48:12" );  //使用日期字符串初始化时间
        System.out.println( "date:" + date );
        long msec = date.getTime();     //获取date的毫秒数
        System.out.println( "msec:" + msec );
        date.setTime( new Long( "1599903683517" ) ); //使用毫秒设置成date格式
        System.out.println( "date:" + date );

        /**
         * TODO DateFormat类
         * DateFormat是抽象类,提供了格式化和解析日期与时间的能力.getDateInstance()方法返回DateFormat实例,这种实例可以格式化日期信息
         *     static final DateFormat getDateInstance()
         *     static final DateFormat getDateInstance(int style)
         *     static final DateFormat getDateInstance(int style,Locale locale)
         *     style是下列值之一:DEFAULT,SHORT,MEDIUM,LONG或FULL,这些值是由DateFormat定义的int型常量,它们使得日期显示的相关细节有所不同
         *     locale是由Locate类定义的静态引用之一
         *
         *     getTimeInstance()方法同上
         *
         *     SimpleDateFormat()用法如下
         */
        DateFormat df;
        df = DateFormat.getDateInstance( DateFormat.SHORT, Locale.CHINA );
        System.out.println( "chinaDate:" + df.format( date ) );
        df = DateFormat.getTimeInstance( DateFormat.DEFAULT, Locale.CHINA );
        System.out.println( "chinaTime:" + df.format( date ) );

        df = DateFormat.getDateInstance( DateFormat.MEDIUM, Locale.JAPAN );
        System.out.println( "JAPAN:" + df.format( date ) );

        df = DateFormat.getDateInstance( DateFormat.LONG, Locale.UK );
        System.out.println( "UK:" + df.format( date ) );

        df = DateFormat.getDateInstance( DateFormat.FULL, Locale.US );
        System.out.println( "US:" + df.format( date ) );

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "hh:mm:ss" );
        System.out.println( simpleDateFormat.format( date ) );

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat( "dd MMM YYY hh:mm:ss zzz" );
        System.out.println( simpleDateFormat1.format( date ) );

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat( "E MMM dd yyyy" );
        System.out.println( simpleDateFormat2.format( date ) );
        System.out.println( "------------------------------------------" );

        /**
         * TODO JDK8新增时间和日期API
         * java.time.LocalDate
         * java.time.LocalTime
         * java.time.LocalDateTime
         */
        System.out.println( LocalDate.now() );
        System.out.println( LocalTime.now() );
        System.out.println( LocalDateTime.now() );
        System.out.println( LocalDateTime.now().toLocalDate() );
        System.out.println( LocalDateTime.now().toLocalTime() );
//        //格式化时间字符串
//        System.out.println("format0" + LocalDateTime.parse( "June 21,2014 12:01 AM",DateTimeFormatter.ofPattern( "MMMM d',' yyyy hh':'mm a" ) ));
        //自定义格式
        System.out.println( "format1:" + LocalDateTime.now().format( DateTimeFormatter.ofPattern( "MMMM d',' yyyy h':'mm a" ) ) );
        //系统格式
//        System.out.println( "format2:" + LocalDateTime.now().format( DateTimeFormatter.ofLocalizedDateTime( FormatStyle.FULL ) ) );
//        System.out.println( "format3:" + LocalDateTime.now().format( DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT ) ) );
        System.out.println( "------------------------------------------" );


        /**
         * 1 s=1000 ms(毫秒),1 ms=1000 us(微秒),1 us=1000 ns(纳秒)
         */
        System.out.println( "System:" + System.currentTimeMillis() + " ms, " + System.nanoTime() + " ns" );
        System.out.println( "------------------------------------------" );

        /**
         * TODO Calendar类(推荐日期API)
         */
        String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Calendar calendar = Calendar.getInstance();
        System.out.println( months[calendar.get( Calendar.MONTH )] );
        System.out.println( calendar.get( Calendar.YEAR ) );
        System.out.println( calendar.get( Calendar.HOUR ) );
        System.out.println( calendar.get( Calendar.MINUTE ) );
        System.out.println( calendar.get( Calendar.SECOND ) );
        //更新时间
        calendar.set( Calendar.HOUR, 10 );
        calendar.set( Calendar.MINUTE, 29 );
        calendar.set( Calendar.SECOND, 22 );
        System.out.println( "Update time: " );
        System.out.println( calendar.get( Calendar.HOUR ) + ":" );
        System.out.println( calendar.get( Calendar.MINUTE ) + ":" );
        System.out.println( calendar.get( Calendar.SECOND ) + ":" );
    }
}
