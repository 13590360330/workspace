package com.javabasic.service.thinkinginjava.basic;

/**
 * TODO finalizer
 */
class Book {
    private static int count = 1;
    private final int no = count++;

    boolean checkedOut = false;

    Book(boolean checkOut) {
        checkedOut = checkOut;
    }

    void checkIn() {
        checkedOut = false;
    }

    /**
     * System.gc() 并不会保证一定会执行终结方法,终结方法和JDK9中的clear方法都是应当避免使用的,
     * 如果遇到gc无法回收的对象如native方法中经C或C++创建的未被回收的对象,应当将该方法的包含类实现 AutoCloseAble接口,
     * 用try-with-resources自动关闭对象（effectivejava P27）
     */
    protected void finalize() {
        if (checkedOut)
            System.out.println( "Book-" + no + " Error: checked out" );
    }
}


public class TerminationCondition {
    public static void main(String[] args) {
        Book book = new Book( true );
        book.checkIn();
        /**内存泄露的第2种情况,books是一个图书管书本借出管理的一个容器,它会伴随程序启动一直存在,如果书本返回后,没有执行books.remove(...)
         * 则该引用会一直存在,对象得不到清理,就会导致程序内存泄露*/
        for (int i = 1; i <= 1000000; i++) {
            if (i / 3 == 0)
                new Book( false );
            else
                new Book( true );
        }

        System.gc();
    }
}

class Finalizer {

    @Override
    protected void finalize() throws Throwable {
        System.out.println( "Finalizer-->finalize()" );
    }

    public static void main(String[] args) {
        Finalizer f = new Finalizer();
        f = null;

        System.gc();//手动请求gc
    }
}