package com.javabasic.service.thinkinginjava.innerclass;

import com.javabasic.service.thinkinginjava.io.Logs;

/**
 * TODO [内部类可以被覆盖吗? P212]
 *
 * 内部类在明确的指定继承关系后可以,如下
 */
class Egg2 {
    protected class Yolk {
        public Yolk() {
            System.out.println( "Egg2.Yolk();" );
        }

        public void f() {
            System.out.println( "Egg2.Yolk,f();" );
        }
    }

    private Yolk y = new Yolk();

    public Egg2() {
        System.out.println( "New Egg2();" );
    }

    public void insertYolk(Yolk yy) {
        y = yy;
    }

    public void g() {
        y.f();
    }
}

public class BigEgg2 extends Egg2 {
    public class Yolk extends Egg2.Yolk {
        public Yolk() {
            System.out.println( "BigEgg2.Yolk()" );
        }

        public void f() {
            System.out.println( "BigEgg2.Yolk.f()" );
        }
    }

    public BigEgg2() {
//        super(); 在无参的构造器中,隐藏了这个super(),类是这种继承模式,含参构造,必须显示的含有super(...)
        insertYolk( new Yolk() );   //insertYolk()方法只能接受Egg2.Yolk   所以这里的new Yolk()发生了向上转型
    }

//    private static BigEgg2.Yolk yolk;

    public static void main(String[] args) {
        Logs.getLogs("BigEgg2");
        Egg2 e2 = new BigEgg2();
        e2.g();
    }
}
