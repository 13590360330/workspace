package com.javabasic.service.thinkinginjava.collection;


import static sun.misc.Version.print;

/**
 * TODO [理解map]
 * AssociativeArray<K, V>类基于2元数组模拟map
 * @param <K>
 * @param <V>
 */
public class AssociativeArray<K, V> {
    private Object[][] pairs;
    private int index;

    public AssociativeArray(int length) {
        pairs = new Object[length][2];    //创建1元长度为length,2元长度为2的Object数组pairs
    }

    public void put(K key, V value) {
        if (index >= pairs.length)
            throw new ArrayIndexOutOfBoundsException();
        pairs[index++] = new Object[]{key, value};   //存数据
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        for (int i = 0; i < index; i++)
            if (key.equals( pairs[i][0] ))
                return (V) pairs[i][1];   //取数据
        return null;
    }

    //打印这个数组
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < index; i++) {
            stringBuilder.append( pairs[i][0].toString() );
            stringBuilder.append( ":" );
            stringBuilder.append( pairs[i][1].toString() );
            if (i < index - 1)
                stringBuilder.append( "\n" );
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        AssociativeArray<String, String> stringStringAssociativeArray = new AssociativeArray<>( 6 );
        stringStringAssociativeArray.put( "sky", "blue" );
        stringStringAssociativeArray.put( "grass", "green" );
        stringStringAssociativeArray.put( "ocean", "dancing" );
        stringStringAssociativeArray.put( "tree", "tall" );
        stringStringAssociativeArray.put( "earth", "brown" );
        stringStringAssociativeArray.put( "sun", "warm" );

        try {
            stringStringAssociativeArray.put( "extra", "object" );
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println( "Too many objects!" );
        }
        System.out.println( stringStringAssociativeArray );
        System.out.println( stringStringAssociativeArray.get( "ocean" ) );
    }
}
