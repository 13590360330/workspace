package com.javabasic.service.officialjava.basic;

/**
 * TODO 嵌套类,内部类
 * 1,可以在类的内部定义另外一个类,这种类就是所谓的嵌套类,嵌套类的作用域被限制在包含它的类中,因此类B在类A中定义,那么类B不能独立于类A而存在
 * 嵌套类可以访问包含它的类的成员,包括私有成员,但是包含类(包含嵌套类的类)不能访问嵌套类的成员
 * 2,嵌套类有两种类型,静态的和非静态的 ,静态的用的很少,原因是包含类的非静态成员不能直接引用了,参考书P151
 * 3,嵌套类最重要的类型是内部类,内部类是非静态的嵌套类,可以访问外部类的所有变量和方法,并且可以直接引用他们,但是反过来不可以,
 *   内部类的成员只有在内部类的作用域内才是已知的,并且外部类不能使用
 */

public class Outer {
    int outer_x = 100;

    void test() {
        Inner inner = new Inner();
        inner.display();
    }

    class Inner {
        void display() {
            System.out.println( "dispaly:outer_x=" + outer_x );
        }
    }
}

class InnerClassDemo {
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.test();
    }
}
