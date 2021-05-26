package com.javabasic.service.thinkinginjava.Enum;

import java.util.EnumMap;

import static com.javabasic.service.thinkinginjava.Enum.Explore.*;   //静态导入

/**
 * TODO  [EnumMaps,常量相关方法 P603,命令设计模式]
 *
 * 通过常量相关方法,每个enum实例可以具备自己独特的行为,enum的多态
 * 枚举的每个元素,都是该枚举的 static final 实例
 */
interface Command {
    void actions();
}

//一个key,对应一条命令
public class EnumMaps {
    public static void main(String[] args) {
        EnumMap<Explore, Command> em = new EnumMap<Explore, Command>( Explore.class );
        em.put( KITCHEN, () -> System.out.println( "Kitchen fire!" ) );
        em.put( BATHROOM, () -> System.out.println( "Bathroom alert!" ) );
        em.entrySet().stream().forEachOrdered( x->{
            System.out.println(x.getKey() + ": ");
            x.getValue().actions();
        } );

        try {
            em.get(UTILITY).actions();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
