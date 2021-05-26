package com.javabasic.service.officialjava.T;

/**
 * TODO 泛型
 * <p>
 * 1,方法 static <T extends Comparable<T>,V extends T> boolean isIn(T x,V[] y){}  --类型参数在返回值类型之前声明
 * 类和接口:interface MinMax<T extends Comparable<T>>{}                          --类型参数在....
 * class MyClass<T extends Comparable<T>> implements MinMax<T>{}        --类型参数在....
 * 注意:泛型的限制,在官方参考书382面
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2,擦除
 * 原则:泛型代码必须能够与以前的非泛型代码相兼容
 * 因此,对java语言的语法或JVM所做的任何修改必须避免破坏以前的代码,为了满足这条约束,java使用擦除实现泛型
 * 擦除的工作原理:编译java时,所有泛型信息被移除(擦除),这意味着使用它们的界定类型替换类型参数,如果没有显示的指定界定类型,则使用Object,
 * 然后应用适当的类型转换(根据类型参数而定),以保持与类型参数所指定类型的兼容性,编译器也会强制实现这种类型兼容性
 * <p>
 * 桥接 参考书P379
 */
public class Gen<T, V> {

    /**
     * 3,模糊性错误
     * void set(T o){ob1=o;};    void set(V o){ob2=o;}; 会发生模糊性错误
     * 当擦除导致两个看起来不同的泛型声明,在擦除之后变成相同的类型而导致冲突时,就会发生模糊性错误
     */
    T ob1;
    V ob2;

    void set(T o) {
        ob1 = o;
    }

//    void set(V o){ob2=o;};

    /**
     * 4,使用泛型的一些限制
     * a,不能实例化类型参数
     */
    Gen(){}; //T知识一个占位符,编译器并不知道要创建哪种类型的对象
    /**
     * b,静态成员不能使用在类中声明的类型参数,也就是说类声明的泛型参数都不能被静态成员使用
     */
//    static T ob3;
//    static T getob1(){return null;};
    /**
     * c,对泛型数组的一些限制,
     * (1)不能实例化元素类型为类型参数的数组
     * (2)不能创建特定类型的泛型引用数组
     */
    String aa[] = new String[10];
    String[] aa1 = new String[10];

    T vals[];

    //    vals=new T[]; //T知识占位符,编译器并不知道要创建哪种类型的数组 模糊性错误
    Gen(T o, T[] nums) {
        ob1 = o;
        vals = nums;  //调用构造函数时,传递的nums的类型是明确的
    }
}

class GenArrays {
    public static void main(String[] args) {
        Integer n[] = {1, 2, 3, 4, 5};
        Gen<Integer, String> iob = new Gen<Integer, String>( 50, n ); //可以创建指定类型为Gen的对象
//        Gen<Integer, String> gens[] = new Gen<Integer,String>[10];//不能创建特定类型的泛型引用数组,因为gens[]里的每一个对象类型都被限制为<Integer, String>,与泛型设计不符
        Gen<?,?> gens2[] = new Gen<?,?>[10];//可以用通配符,泛型类型类对象数组应该这么创建
        Gen gens3[] = new Gen[10];

        ArrayList strings = new ArrayList();
        strings.add(1);
        strings.add("aa");
        strings.add('2');

        List<? extends Object> numbers = Arrays.asList( new Gen() );
        System.out.println(numbers.contains( new Gen() ));
        System.out.println(numbers.indexOf( new Gen() ));

        ArrayList<Integer> strings1 = new ArrayList();
        Integer integer = new Integer(1);
        strings1.add( integer );
        System.out.println(strings1.get( 0 ));//get方法在编译时编译器会自动插入转型操作(由object向下转型为了Integer),add操作,编译器会做类型检测(编程思想P380)--即泛型的所有动作都发生在边界处,由底层代码(C++)实现

//        T[] a=new T[];   //擦除只会加将变量或者方法参数类型擦除为object或者上限,其他的都去掉了
        Object[] b= new Object[3];
    }
}
