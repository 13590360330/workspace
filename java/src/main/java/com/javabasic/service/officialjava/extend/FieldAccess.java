package com.javabasic.service.officialjava.extend;

/**
 * 多态-不同的导出类有不同的接口
 *   1.向上转型:结合(StaticPolymorphism类) 向上转型之后,引用对象还是原先的对象,功能与初始化这个对象时初始化的父类对象几乎一样,方法(接口)如果有重写,则父类对象方法被覆盖,可访问的域为子类本身域(字段),示例 getField()和getField2()
 *
 *   2.向上转型之后可以向下转型转回来,除此之外的向下转型都会报ClassCastException
 *
 *   3.重写的方法使用的字段为子类的字段,需要使用super.field来在重写方法里调用父类字段
 *   基于此,结论 :(重点)父子类的域字段最好不要同名,并且要设置成private,虽然怎样做只能通过方法来访问字段,但是避免了混淆,
 *               父子类private方法虽然没有关系,但是也不要同名
 */
public class FieldAccess {
    public static void main(String[] args) {
        Super aSuper = new Super();
        System.out.println();
        System.out.println( "aSuper:" + aSuper.field + "," + aSuper.getField() + "," );  //sub.getField() 多态特性,sub向上转型后,sup.getField()调用的是重写方法,他的作用域在Sub的默认域
        Super sup = new Sub();
        System.out.println( "sup:" + sup.field + "," + sup.getField() + "," +sup.getField2());  //sub.getField() 多态特性,sub向上转型后,即转成其父类对象,sup.getField()调用的是重写方法,他的作用域在Sub的默认域
//      System.out.println( "Super" + sup.field + "," + sup.getField() + "," + sup.getSuperField() ); 向上转型之后sup是一种Super类型 sup.getSuperField()是不存在与此类的方法
        Sub sub = new Sub();
        System.out.println( "sub:" + sub.field + "," + sub.getField() + "," +  sub.getSuperField() );
    }
}

class Super {
    public int field = 4;
    public int field2 = 2;
    private int field3=5;

    //重写的方法
    public int getField() {
        return field;
    }

    //没有重写的方法
    public int getField2() {
        return field2;
    }

    private int getField3() {
        return field2+field;
    }

    public static void main(String[] args) {
        Super sub = new Sub();
        System.out.println(sub.field3+","+sub.field+","+sub.field2+","+sub.getField()+","+sub.getField2()+","+ sub.getField3()); //sub可视为是被(new Sub())初始化后的Super对象,并且重写的方法已被覆盖

        Super aSuper = new Super();
        System.out.println(aSuper.getField3());

    }
}

class Sub extends Super {
    public int field = 1;
    private int field2 = 3;

    public int getField() {
        return field;
    }

    public int getSuperField() {
        return super.field;
    }
}