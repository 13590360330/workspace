package com.javabasic.service.officialjava.Enum;

/**
 * 枚举和类的其他很多功能都相同
 */
public enum Apple2 {

    Jonathan( 10 ), GoldenDel( 11 ), RedDel( 5 ), Winesap( 6 ), Cortland( 4 ), Hongfushi;

    //价格属性
    private int no;

    //默认构造函数,可以不写后面的都是对默认构造函数的重载,将no初始化为-1,表名不能获取的价格
    Apple2() {
        no = -1;
    }

    Apple2(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}

class EnumDemo2 {
    public static void main(String[] args) {
        Apple2 ap;
        System.out.println( Apple2.Hongfushi.getNo() );

        for (Apple2 a : Apple2.values())
            System.out.println( a + " costs " + a.getNo() + " cents " );
    }
}
