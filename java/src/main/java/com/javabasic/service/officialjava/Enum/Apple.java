package com.javabasic.service.officialjava.Enum;

import java.util.Arrays;

/**
 * TODO Enum类, 是java.lang包定义的
 * 枚举是已命名常量的列表(请记住,枚举是使用enum关键字创建的),所有枚举自动继承自Enum,Enum是泛型类,Enum没有公有构造函数
 * 其声明: class Enum<E extends Enum<E>>
 */
enum Apple {
    /**
     * 1.如下标识符Jonathan,GoldenDel,RedDel,Winesap,Cortland被称为枚举常量,每个枚举常量被隐式的声明为Apple的公有,静态final成员
     * 枚举常量的类型是声明它们的枚举的类型,对于这个例子为Apple,因此在java语言中,这些常量被称为是"自类型化的"(self-typed),其中的"自"
     * 是指封装常量的枚举
     * <p>
     * 2.枚举是类类型,却不能使用new实例化枚举,反而,枚举变量的声明和使用方式在许多方面与基本类型相同,
     * 如在其他类中引用 Apple ap;  ap=Apple.Jonathan;
     * ap是Apple类型,只能被赋值为Jonathan,GoldenDel,RedDel,Winesap,Cortland中的值
     * 枚举和类的其他很多功能都相同
     * <p>
     * 3.compareTo()方法比较相同类型的两个枚举常量的序数值 ap=Apple.RedDel; ap2=Apple.RedDel; ap.compareTo(ap2)==0? 1;0 -----1
     *  equals()方法比较枚举常量和其他对象的相等性,只有当两个对象都引用同一个枚举中相同的常量时,他们才相等,如果两个常量来自不同的枚举,
     *  即时他们序数值相同,equals()方法也不会返回true;  实例同上
     */
    Jonathan, GoldenDel, RedDel, Winesap, Cortland;

    public static void main(String[] args) {
        Class<Enum> enumClass = Enum.class;
    }

}

class EnumDemo {
    public static void main(String[] args) {
        Apple ap;
        ap = Apple.RedDel;
        System.out.println( ap );

        ap = Apple.GoldenDel;
        if (ap == Apple.GoldenDel)
            System.out.println( "ap contains GoldenDel" );


        System.out.println( appleno( ap ) );
        ap = Apple.Jonathan;
        System.out.println( appleno1( ap ) );
    }

    //以下的每个方法都是实现返回序列加1
    public static int appleno(Apple ap) {
        int i = 0;
        switch (ap) {
            case Jonathan:
                i = 1;
                break;
            case GoldenDel:
                i = 2;
                break;
            case RedDel:
                i = 3;
                break;
            case Winesap:
                i = 4;
                break;
            case Cortland:
                i = 5;
                break;
        }
        return i;
    }

    /**
     * value()方法返回一个包含枚举常量列表的数组
     * valueOf()方法返回与传递到参数str的字符串相对应的枚举常量 Apple.valueof("Jonathan") --Jonathan
     */
    public static int appleno1(Apple ap) {
        int i = 0;
        Apple allapples[] = Apple.values();
        i = Arrays.asList( allapples ).indexOf( ap );
        return i + 1;
    }

    /**
     * ordinal():获取枚举常量的序数值
     *
     * @param ap
     * @return
     */
    public static int no(Apple ap) {
        return ap.ordinal() + 1;
    }
}