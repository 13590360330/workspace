package com.javabasic.service.officialjava.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/**
 * TODO 注解(元数据)
 * 在源文件中嵌入补充信息,这类信息称为注解(annotation),注解不会改变程序的动作,因此也就不会改变程序的语义,但是在开发和部署期间,
 * 各种工具可以使用这类信息,"元数据(metadata)"也用于表示这个特性,但是术语"注解"更具描述性并且更常用
 * <p>
 * 1,注解是通过基于接口的机制创建的
 * 关键字interface前面的@,这告诉编译器正在声明一种注解类型
 * 所有注解都只包含方法声明,但是不能提供方法体,而是由java实现这些方法,这些方法的行为更像是域变量
 * 对所有情况,注解都要放在声明的最前面
 * <p>
 * 2,注解不能包含extends子句,所有注解类型都自动扩展了Annotation接口,Annotation是所有注解的超接口,annotationType()方法返回表示调用注解的Class对象
 * <p>
 * 3,保留策略  @Retention是java的内置注解
 * SOURCE : 只在源文件中保留,在编译期间会被抛弃
 * CALSS  : 在编译时被存储到.class文件中,但是在运行时通过JVM不能得到这些注解 (默认策略)
 * RUNTIME: 在编译时被存储到.class文件中,并且在运行时可以通过JVM获取到这些注解,因此,RUNTIME保留策略提供了最永久的注解
 * 注意:局部变量声明的注解不能存储在.class文件中
 * <p>
 * 4,标记注解,不含成员的一类注解,唯一目的就是标记声明,不含方法定义 如:重写@Override 参考书P295
 * <p>
 * 5,单成员注解 参考书P296
 * <p>
 * 6,内置注解 参考书P297
 * <p>
 * 7,类型注解 参考书299  JDK1.8新增,难点
 * <p>
 * 8,重复注解 参考书303  JDK1.8新增,难点
 */
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno {
    String str();

    int val();
}

@Retention(RetentionPolicy.RUNTIME)
@interface What {
    String description();
}

class Meta {

    /**
     * 应用注解时,需要为注解的成员提供值
     */
    @MyAnno(str = "Annotation Example", val = 100)
    public static void myMeth() {
        try {
            Meta ob = new Meta();
            //当注解指定了RUNTIME保留策略,那么这个包里的任何程序都可以在运行时通过反射来查询注解,反射是能够在运行时获取类相关信息的特征
            //获取class对象
            Class<?> c = ob.getClass();
            Method m = c.getMethod( "myMeth" );
            MyAnno anno = m.getAnnotation( MyAnno.class ); //anno是一个MyAnno类型的Class对象,即注解,这种结构被称为"类字面值"
            System.out.println( anno.str() + "," + anno.val() );
        } catch (NoSuchMethodException e) {
            System.out.println( "Method Not Found" );
            e.printStackTrace();
        }

    }

    @MyAnno(str = "Annotation Example", val = 100)
    public static void myMeth(String str, int i) {
        try {
            Meta ob = new Meta();
            Class<?> c = ob.getClass();
            //這個方法中传递了参数myMeth(String str, int i),获取方法的信息改成如下方式
            Method m = c.getMethod( "myMeth", String.class, int.class );
            MyAnno anno = m.getAnnotation( MyAnno.class );
            System.out.println( anno.str() + "," + anno.val() );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        myMeth();
    }
}
