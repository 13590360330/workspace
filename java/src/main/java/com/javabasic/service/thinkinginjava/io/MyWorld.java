package com.javabasic.service.thinkinginjava.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO [使用Serializable及深拷贝]  P582
 * <p>
 * 一个比较诱人的使用序列化的想法是:保存程序的一些状态,以便我们随后可以很容易地将程序恢复到当前状态
 */

class House implements Serializable {
};

class Animal implements Serializable {
    private String name;
    private House preferredHouse;

    Animal(String nm, House h) {
        name = nm;
        preferredHouse = h;
    }

    @Override
    public String toString() {
        return "Animal {" +
                "name=' [" + super.toString() + '\'' +
                "], preferredHouse=" + preferredHouse +
                "} \n";
    }
}

public class MyWorld {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Logs.getLogs( "MyWorld" );
        House house = new House();
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add( new Animal( "Bosco the dog", house ) );
        animals.add( new Animal( "Ralph the hamster", house ) );
        animals.add( new Animal( "Molly the cat", house ) );
        System.out.println( "animals: \n" + animals );
        ByteArrayOutputStream buf1 = new ByteArrayOutputStream();
        ObjectOutputStream o1 = new ObjectOutputStream( buf1 );
        o1.writeObject( animals );
        o1.writeObject( animals );
        ByteArrayOutputStream buf2 = new ByteArrayOutputStream();
        ObjectOutputStream o2 = new ObjectOutputStream( buf2 );
        o2.writeObject( animals );
        ObjectInputStream in1 = new ObjectInputStream( new ByteArrayInputStream( buf1.toByteArray() ) );
        ObjectInputStream in2 = new ObjectInputStream( new ByteArrayInputStream( buf2.toByteArray() ) );
        List
                animals1 = (List) in1.readObject(),
                animals2 = (List) in1.readObject(),
                animals3 = (List) in2.readObject();
        System.out.println( "animals1 \n" + animals1 );
        System.out.println( "animals2 \n" + animals2 );
        System.out.println( "animals3 \n" + animals3 );
    }
}
