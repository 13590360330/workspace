package com.javabasic.service.thinkinginjava.io;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * TODO [压缩,jar命令,对象序列化 Serializable/ˈsɪˌriəˌlaɪzəbl/ P573 , XML P586 , Preferences P588]
 */

class Data implements Serializable {
    private int n;

    public Data(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "Data{" +
                "n=" + n +
                '}';
    }
}

public class Worn implements Serializable {
    private static Random rand = new Random();
    private Data[] d = {
            new Data( rand.nextInt( 10 ) ),
            new Data( rand.nextInt( 10 ) ),
            new Data( rand.nextInt( 10 ) )
    };
    private Worn next;
    private char c;

    public Worn(int i, char x) {
        System.out.println( "Worm constructor: " + i );
        c = x;
        //i起到递归的作用
        if (--i > 0) {
            next = new Worn( i, (char) (x + 1) );   //除了string其他的+运算都进行unicode码加减
        }
    }

    public Worn() {
        System.out.println( "Default constructor" );
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder( ":" );
//        System.out.println("stringBuilder:"+ System.identityHashCode(stringBuilder));
        stringBuilder.append( c );
        stringBuilder.append( "=" );
        stringBuilder.append( Arrays.toString( d ) );
        // TODO ??????
        if (next != null)
            stringBuilder.append( next );  //第一个Worn对象的next字段是下一个Worn的对象的引用
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws Exception {
        Logs.getLogs( "Worn" );
        Worn w = new Worn( 6, 'a' );
        System.out.println( "w = " + w );
        ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( "../BigData/src/resources/tmp/test/worn.dat" ) );   //持久化到文件
        out.writeObject( "Worm storage\n" );
        out.writeObject( w );   //持久化对象
        out.close();
        ObjectInputStream in = new ObjectInputStream( new FileInputStream( "../BigData/src/resources/tmp/test/worn.dat" ) );
        String s = (String) in.readObject();
        Worn w2 = (Worn) in.readObject();
        System.out.println( s + "w2 = " + w2 );
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out2 = new ObjectOutputStream( bout );
        out2.writeObject( "Worn storage \n" );
        out2.writeObject( w );
        out2.flush();
        ObjectInputStream in2 = new ObjectInputStream( new ByteArrayInputStream( bout.toByteArray() ) );
        String s1 = (String) in2.readObject();
        Worn w3 = (Worn) in2.readObject();
        System.out.println( s + "w3 = " + w3 );
    }
}

/**
 * TODO [transient /ˈtrænʃnt/ 瞬时]
 * 某个特定对象不想让java的序列化机制自动保存与恢复(如密码)
 */
class Logon implements Serializable {
    private Date date = new Date();
    private String username;
    private transient String password;   //transient瞬时

    Logon(String name, String pwd) {
        username = name;
        password = pwd;
    }

    @Override
    public String toString() {
        return "Logon{" +
                "date=" + date +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static void main(String[] args) throws Exception {
        Logs.getLogs( "Logon" );
        Logon a = new Logon( "Hulk", "myLittlePony" );
        System.out.println( "logon a = " + a );
        ObjectOutputStream o = new ObjectOutputStream( new FileOutputStream( "../BigData/src/resources/tmp/test/Logon.dat" ) );
        o.writeObject( a );
        o.close();
        TimeUnit.SECONDS.sleep( 1 );
        ObjectInputStream I = new ObjectInputStream( new FileInputStream( "../BigData/src/resources/tmp/test/Logon.dat" ) );
        System.out.println( "Recovering object at" + new Date() );
        a = (Logon) I.readObject();
        System.out.println( "logon a = " + a );
    }
}