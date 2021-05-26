package com.javabasic.service.thinkinginjava.exception;

/**
 * TODO 自定义Exception,并打印自定义信息
 */
public class DatabaseException extends Exception {
    public DatabaseException(int transactionID, int queryID, String message) {
        //String.format部分整个就是一个参数,多参的构造器,可以super()任何父类构造器
//        super();
        super( String.format( "(t%d,q%d) %s", transactionID, queryID, message ) );
    }

    public static void main(String[] args) {
        try {
            throw new DatabaseException( 3,7,"Write failed" );
        } catch (DatabaseException e) {
            System.err.println(e);
        }
    }
}
