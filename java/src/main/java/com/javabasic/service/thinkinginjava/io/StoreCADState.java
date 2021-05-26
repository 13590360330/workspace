package com.javabasic.service.thinkinginjava.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * TODO [利用serializable保存系统,程序状态, P585]
 *
 * static 字段在序列化的时候不会被序列化,需要传递static字段的值看137行和150行
 */
class Circle extends Shape {
    private static int color = RED;

    @Override
    public void setColor(int newColor) {
        color = newColor;
    }

    @Override
    public int getColor() {
        return color;
    }

    Circle(int length, int wide, int hight) {
        super( length, wide, hight );
    }
}

class Square extends Shape {
    private static int color;    //加载初始化的时候会变成0

    @Override
    public void setColor(int newColor) {
        color = newColor;
    }

    @Override
    public int getColor() {
        return color;
    }

    Square(int length, int wide, int hight) {
        super( length, wide, hight );
        color = RED;
    }

}

class Line extends Shape {
    private static int color = RED;

    public static void serializeStaticState(ObjectOutputStream os) throws IOException {
        System.out.println( "color=" + color );
        os.writeInt( color );
    }

    public static void deserializeStaticState(ObjectInputStream os) throws IOException {
        color = os.readInt();
    }

    Line(int length, int wide, int hight) {
        super( length, wide, hight );
    }

    @Override
    public void setColor(int newColor) {
        color = newColor;
    }

    @Override
    public int getColor() {
        return color;
    }
}

abstract class Shape implements Serializable {
    public static final int RED = 1, BLUE = 2, GREEN = 3;
    private int xPos, yPos, dimension;
    private static Random rand = new Random( 47 );
    private static int counter = 0;

    public abstract void setColor(int newColor);

    public abstract int getColor();

    public Shape(int xVal, int yVal, int dim) {
        xPos = xVal;
        yPos = yVal;
        dimension = dim;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                ", dimension=" + dimension + ", " +
                this.getClass() + ".Color = " + getColor() +
                '}' + "\n";
    }

    public static Shape randomFactory() {
        int xVal = rand.nextInt( 100 );
        int yVal = rand.nextInt( 100 );
        int dim = rand.nextInt( 100 );
        switch (counter++ % 3) {
            default:
            case 0:
                return new Circle( xVal, yVal, dim );
            case 1:
                return new Square( xVal, yVal, dim );
            case 2:
                return new Line( xVal, yVal, dim );
        }
    }
}


public class StoreCADState {
    public static void main(String[] args) throws Exception {
        Logs.getLogs( "StoreCADState" );
        List<Class<? extends Shape>> shapeTypes = new ArrayList<>();
        shapeTypes.add( Circle.class );
        shapeTypes.add( Square.class );
        shapeTypes.add( Line.class );
        List<Shape> shapes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            shapes.add( Shape.randomFactory() );
        }
        for (int i = 0; i < 10; i++) {
            ((Shape) shapes.get( i )).setColor( Shape.GREEN );
        }
        ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( "CADState.out" ) );
//        out.writeObject( shapeTypes );
        Line.serializeStaticState( out );    //第131行 -> color = 3   传递目标值,反序列化的时候可以用这个值去修改对象
        out.writeObject( shapes );
        System.out.println( shapes );
    }
}


class RecoverCADState {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Logs.getLogs( "RecoverCADState" );
        ObjectInputStream in = new ObjectInputStream( new FileInputStream( "CADState.out" ) );
//        List<Class<? extends Shape>> shapeTypes = (List<Class<? extends Shape>>) in.readObject();
        Line.deserializeStaticState( in );     //对应上一步的Line.serializeStaticState(out)   将Line类的color的值变为了3 所有的Line实例化对象的color就都是3
        List<Shape> shapes = (List<Shape>) in.readObject();
        System.out.println( shapes );
    }
}
