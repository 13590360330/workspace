package com.javabasic.service.officialjava.T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add( 1 );

        List<String> strings = Arrays.asList( "a,b,c,d".split( "," ) );
        System.out.println(strings.getClass().getTypeName());   //java.util.Arrays$ArrayList
        List<String> strings1 = strings.subList( 1, 2 );
        Collection<String> strings2 = new ArrayList<>( strings1 );
        System.out.println(strings2.getClass().getTypeName());   //java.util.ArrayList
        System.out.println(strings.retainAll( strings2 ));

    }
}
