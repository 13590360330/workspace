package com.javabasic.service.thinkinginjava.string;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.util.Formatter;

/**
 * TODO Formatter 格式化输出
 * <p>
 * Formatter提供了对空格与对齐的强大控制能力
 * <p>
 * Formatter转换(类型转换 P292)
 * <p>
 * String.format() P294 它的内部,也是创建一个Formatter对象,但是这个静态方法特殊场合更方便使用
 * eg:com.java.com.service.Thinking_In_Java.exception.DatabaseException
 */
public class Receipt {
    private double total = 0;
    private Formatter f = new Formatter( System.out );

    public void printTitle() {
        f.format( "%-15s %5s %10s\n", "Item", "Qty", "Price" );
        f.format( "%-15s %5s %10s\n", "----", "---", "-----" );
    }

    public void print(String name, int qty, double price) {
        f.format( "%-15.15s %5d %10.2f\n", name, qty, price );
        total += price;
    }

    public void printTotal() {
        f.format( "%-15s %5s %10.2f\n", "Tax", "", total * 0.06 );
        f.format( "%-15s %5s %10s\n", "", "", "-----" );
        f.format( "%-15s %5s %10.2f\n", "Total", "", total * 1.06 );
    }

    public static void main(String[] args) {
        Logs.getLogs( "Receipt", true );
        Receipt receipt = new Receipt();
        receipt.printTitle();
        receipt.print( "Jack's Magic Beans", 4, 4.25 );
        receipt.print( "Princess Peas", 3, 5.1 );
        receipt.print( "Three Bears Porridge", 1, 14.29 );
        receipt.printTotal();
    }
}
