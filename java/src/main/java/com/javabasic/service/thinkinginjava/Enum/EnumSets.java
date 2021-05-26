package com.javabasic.service.thinkinginjava.Enum;

import com.javabasic.service.thinkinginjava.io.Logs;

import java.util.EnumSet;

import static com.javabasic.service.thinkinginjava.Enum.Explore.*;   //静态导入

/**
 * TODO [EnumSet 装载enum元素的set P601]
 */
public class EnumSets {
    public static void main(String[] args) {
        Logs.getLogs( "EnumSets" );
        EnumSet<Explore> points = EnumSet.noneOf( Explore.class );    //Empty set
        points.add( BATHROOM );
        System.out.println( "1:" + points );
        points.addAll( EnumSet.of( STAIR1, STAIR2, KITCHEN ) );
        System.out.println( "2:" + points );
        points = EnumSet.allOf( Explore.class );
        points.removeAll( EnumSet.range( OFFICE1, OFFICE4 ) );
        System.out.println( "3:" + points );
        points = EnumSet.complementOf( points );
        System.out.println( "4:" + points );
    }
}
