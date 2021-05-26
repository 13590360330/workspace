package com.javabasic.service.officialjava.util.concurrent;

/**
 * TODO 多线程
 * Java中线程的创建有两种方式：
 *     1．  通过继承Thread类，重写Thread的run()方法，将线程运行的逻辑放在其中
 *     2．  通过实现Runnable接口，实例化Thread类
 *
 * 传统的多线程Thread用法是类implements Runnable接口,在run方法中实现程序逻辑,在主线程mian函数中通过构造函数new Thread().start执行
 *                      或者 类extends Thread,在run方法中实现程序逻辑,在主线程mian函数中new类对象,对象调用start()方法
 *
 *  线程中的重要方法,run(),await()等待计时 P928,sleep(),notify(),synchronized等
 *  获取正在运行的线程的Id:Thread.currentThread().getName()
 *
 */

//继承Thread类
class MyThread extends Thread{

    private int ticket = 10;
    private String name;
    public MyThread(String name){
        this.name =name;
    }

    public void run(){
        for(int i =0;i<500;i++){
            if(this.ticket>0){
                System.out.println(this.name+"卖票---->"+(this.ticket--));
            }
        }
    }
}

class ThreadDemo {


    public static void main(String[] args) {
        MyThread mt1= new MyThread("一号窗口");
        MyThread mt2= new MyThread("二号窗口");
        MyThread mt3= new MyThread("三号窗口");
        mt1.start();
        mt2.start();
        mt3.start();
    }

}

//实现Runnable接口
class MyThread1 implements Runnable{
    private int ticket =10;
    private String name;
    public void run(){
        for(int i =0;i<500;i++){
            if(this.ticket>0){
                System.out.println(Thread.currentThread().getName()+"卖票---->"+(this.ticket--));
            }
        }
    }
}

class RunnableDemo {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //设计三个线程
        MyThread1 mt = new MyThread1();
        Thread t1 = new Thread(mt,"一号窗口");
        Thread t2 = new Thread(mt,"二号窗口");
        Thread t3 = new Thread(mt,"三号窗口");
//         MyThread1 mt2 = new MyThread1();
//         MyThread1 mt3 = new MyThread1();
        t1.start();
        t2.start();
        t3.start();
    }

}