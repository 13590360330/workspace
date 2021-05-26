package com.javabasic.service.thinkinginjava.basic;

import com.javabasic.service.thinkinginjava.io.Logs;

/**
 * TODO  switch [enum搭配switch选择 P74,P107]
 *
 *
 */

enum Spiciness {
    NOT, MILD, MEDIUM, HOT, FLAMING
}

public class Burrito {
    Spiciness degree;

    public Burrito(Spiciness degree) {
        this.degree = degree;
    }

    public void describe() {
        System.out.println( "This burrito is " );
        switch (degree) {
            case HOT:
                System.out.println( "it's hot" );
                break;                              //退出switch选择,如果没有遇到break,就会继续执行下一个case,直至default 执行完,break会是程序跳转到switch主体的末尾
            case MILD:
            case MEDIUM:
                System.out.println( "a little hot" );
                return;                            // 跳出这次方法调用,并返回相应值
            case NOT:
                System.out.println( "not hot" );
            case FLAMING:
                System.out.println( "it maybe too hot" );
            default:
                System.out.println("i am going to eat");    //default里也可以用break,但没有任何用处
        }
    }

    public static void main(String[] args) {
        Logs.getLogs("Burrito");
        Burrito
                plain = new Burrito( Spiciness.NOT ),
                greenChile = new Burrito( Spiciness.MEDIUM ),
                jalapeno = new Burrito( Spiciness.HOT );
        plain.describe();
        greenChile.describe();
        jalapeno.describe();
    }
}
