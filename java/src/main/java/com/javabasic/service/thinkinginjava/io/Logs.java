package com.javabasic.service.thinkinginjava.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO [重定向 P549]
 */
public class Logs {

    private static int count = 1;
    private final int no = count++;

    private PrintStream printStream = null;

    private Logs(String Filename) {
        setOutLogs( Filename );
    }

    private Logs(String Filename, boolean coverage) {
        setOutLogs( Filename, coverage );
    }

    private void setOutLogs(String Filename) {
        String logfiledir = "../BigData/src/resources/logs/";
        try {
            File logdir = new File( logfiledir );
            if (!logdir.exists()) {
                FileUtils.forceMkdir( logdir );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File logfile = new File( logfiledir + "/" + Filename + ".log" );

        try {
            printStream = new PrintStream( new BufferedOutputStream( new FileOutputStream( logfile ) ), true );
            System.setOut( printStream );
//            System.setErr( printStream );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setOutLogs(String Filename, boolean coverage) {
        String logfiledir = "../bigdata/logs/";
        try {
            File logdir = new File( logfiledir );
            if (!logdir.exists()) {
                FileUtils.forceMkdir( logdir );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date date = new Date();     //获取当前时间
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat( "YYYYMMddhhmmss" );
        String format = simpleDateFormat1.format( date );
        File logfile = new File( logfiledir + "/" + Filename + "_" + format + ".log" );

        try {
            printStream = new PrintStream( new BufferedOutputStream( new FileOutputStream( logfile ) ), true );
            System.setOut( printStream );
            if(coverage) {
                System.setErr( printStream );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Logs getLogs(String name) {
        return new Logs( name );
    }

    public static Logs getLogs(String name, boolean coverage) {
        return new Logs( name, coverage );
    }

    public void closePrintStream() {
        printStream.close();
    }

    public String getNo() {
        return "Logs" + no;
    }

    public static void main(String[] args) throws IOException {
        String logfiles = "../BigData/src/resources/logs/GetData.log";
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream( new FileOutputStream( logfiles ) );
        bufferedOutputStream.write( "222222\n".getBytes() );
        PrintStream printStream = new PrintStream( bufferedOutputStream );
        //调用流对象的close() 会调用BufferedWriter类和OutputStream类里的close()方法
        printStream.close();
        bufferedOutputStream.write( "1".getBytes() );
        bufferedOutputStream.flush();
//        PrintStream printStream2 = new PrintStream( bufferedOutputStream );
//        printStream2.write( "aaaaa".getBytes() );
        final Class<? extends String> aClass = logfiles.getClass();

    }
}
