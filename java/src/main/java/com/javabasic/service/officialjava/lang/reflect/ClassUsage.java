package com.javabasic.service.officialjava.lang.reflect;


import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * TODO 类的加载,初始化及实例化
 * Java类的加载方式是按需加载，遇到new、getstatic、putstatic或invokestatic这4条字节码指令时，如果类没有进行过初始化，则需要先触发其初始化。
 * 生成这4条指令的最常见的Java代码场景是：使用new关键字实例化对象的时候、读取或设置一个类的静态字段（被final修饰、已在编译期把结果放入常量池的静态字段除外）的时候，
 * 以及调用一个类的静态方法的时候。
 * <p>
 * 1.初始化
 * 初始化这个阶段就是将静态变量（类变量）赋值的过程，即只有static修饰的才能被初始化，执行的顺序就是：父类静态域或着静态代码块，然后是子类静态域或者子类静态代码块（静态代码块先被加载，
 * 然后再是静态属性）
 * <p>
 * 2.对象实例化：
 * 就是执行类中构造函数的内容
 */

public class ClassUsage {
    public static Random rand = new Random( 50 );

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, CloneNotSupportedException {
//        new Initable();
        Class<Initable> initableClass = Initable.class;
//        System.out.println(Initable.staticFinal);
//        System.out.println( Initable.staticFinal2);
//        System.out.println( Initable.staticFinal3);
//        System.out.println(Initable2.staticFinal);
        Class<?> aClass = Class.forName( "com.javabasic.service.officialjava.lang.reflect.Initable3" );  //只加载Initable3.class文件,并获得Initable3的Class类对象
        System.out.println( Initable3.staticFinal );   //Initable3.staticFinal只会初始化,但不会实例化Initable3

        /**
         * TODO 创建类对象的4中方式
         * 4.采用序列化(Serializable),反序列化机制
         */
        //1.使用new创建对象
        Initable3 initable3 = new Initable3();
//        2.使用反射的机制创建对象
        aClass.newInstance();
        //3.采用clone
        Initable3 clone2 = initable3.clone();
        System.out.println( clone2 == initable3 ); //结果是false,clone2 与 initable3 不是指向同一个对象,新创建了一个对象
        System.out.println( clone2 );
        System.out.println( initable3 );

        Class<Initable3> initable3Class = Initable3.class;  //同forName,getClass()的结果一样都是字节码对象
        System.out.println( "initable3Class:" + initable3Class ); //class com.java.com.service.javacore.lang.reflect.Initable3
        Class<?>[] aclasses = initable3Class.getClasses();  // 获取的是内部类的字节码对象数组
        System.out.println( "classes:" + aclasses );  //[Ljava.lang.Class;@c2e1f26
        Class<? extends Initable3> classes = initable3.getClass();  // Class类对象
        System.out.println( "classes:" + classes );  //class com.java.com.service.javacore.lang.reflect.Initable3

    }
}

/**
 * TODO 一个类中的初始化顺序
 * 1.类内容（静态初始化块） => 类内容(静态变量)
 * 2.static final的值是一个"编译期常量",这个值不需要对Initable类进行初始化就可以被读取
 * <p>
 * TODO 静态代码块
 * 静态代码块里的变量都是局部变量，只在块内有效。静态代码块只能访问类的静态成员，而不允许访问实例成员。
 */
class Initable {
    static final int staticFinal = 47;
    static int staticFinal3 = 40;
    static final int staticFinal2 = new Integer( 11 );

    static {
        System.out.println( "Initialzing Initable" );
    }

    Initable() {
        System.out.println( "running Initable constructor" );
    }
}

class Initable2 {
    static final int staticFinal = 147;

    static {
        System.out.println( "Initialzing Initable2" );
    }

    Initable2() {
        System.out.println( "running Initable2 constructor" );
    }
}

class Initable3 implements Cloneable {
    static final int staticFinal = 74;

    static {
        System.out.println( "Initialzing Initable3" );
    }

    Initable3() {
        System.out.println( "running Initable3 constructor" );
    }

    @Override
    public Initable3 clone() throws CloneNotSupportedException {
        Initable3 initable3 = null;
        try {
            initable3 = (Initable3) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return initable3;
    }
}

