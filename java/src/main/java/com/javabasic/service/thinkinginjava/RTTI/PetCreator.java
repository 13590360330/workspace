package com.javabasic.service.thinkinginjava.RTTI;

import com.javabasic.service.thinkinginjava.RTTI.pets.Pet;

import java.util.*;

/**
 * TODO 类型检查
 * <p>
 * 通过子类实现types(),去获得相应的对象
 */
public abstract class PetCreator {
    private Random random = new Random( 47 );

    //抽象方法没有方法体
    public abstract List<Class<? extends Pet>> types();

    private Pet randomPet() {
        int n = random.nextInt( types().size() );   //最大不超过types().size()的随机整数
        try {
            return types().get( n ).newInstance();
        } catch (Exception e) {
//            e.printStackTrace();  如果用这个,在末尾还必须有return语句
            throw new RuntimeException( e );
        }
    }

    private Pet[] createArray(int size) {
        Pet[] pets = new Pet[size];
        for (int i = 0; i < size; i++)
            pets[i] = randomPet();
        return pets;
    }

    public ArrayList<Pet> arrayList(int size) {
        ArrayList<Pet> pets = new ArrayList<>();
        Collections.addAll( pets, createArray( size ) );
        return pets;
    }
}

/**
 * 对外接口的实现案例
 * 注册工厂 P331
 */
class ForNameCreator extends PetCreator {

    private static List<Class<? extends Pet>> types =
            new ArrayList<Class<? extends Pet>>();

    private static String[] typeNames = {
            "com.java.com.service.Thinking_In_Java.RTTI.pets.Mutt",
            "com.java.com.service.Thinking_In_Java.RTTI.pets.Pug",
            "com.java.com.service.Thinking_In_Java.RTTI.pets.EgyptianMau",
            "com.java.com.service.Thinking_In_Java.RTTI.pets.Dog",
            "com.java.com.service.Thinking_In_Java.RTTI.pets.Individual",
            "com.java.com.service.Thinking_In_Java.RTTI.pets.Pet",
            "com.java.com.service.Thinking_In_Java.RTTI.pets.Rodent"
    };

    @SuppressWarnings("unchecked")
    private static void loader() {
        try {
            for (String name : typeNames)
                types.add( (Class<? extends Pet>) Class.forName( name ) );  //向上转型
        } catch (ClassNotFoundException e) {
            throw new RuntimeException( e );
        }
    }

    static {
        loader();
    }

    @Override
    public List<Class<? extends Pet>> types() {
        return types;
    }
}