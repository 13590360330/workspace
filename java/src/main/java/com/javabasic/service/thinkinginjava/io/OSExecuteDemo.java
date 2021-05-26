package com.javabasic.service.thinkinginjava.io;

/**
 * TODO [进程控制（ProcessBuilder）]
 */
public class OSExecuteDemo{
    public static void main(String[] args) {
        Logs.getLogs( "OSExecuteDemo" );
        String commandstring ="javac -encoding UTF-8 " +
                "E:\\日常文件\\知识点整理\\CDH\\DW\\project\\中信银行\\BigData\\src\\main\\com\\java\\com.service\\Thinking_In_Java\\io\\OSExecute.java";
        OSExecute.command( commandstring );
    }
}