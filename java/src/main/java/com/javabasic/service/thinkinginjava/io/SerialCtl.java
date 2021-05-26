package com.javabasic.service.thinkinginjava.io;

import java.io.*;

/**
 * TODO [Externalizable的替代方法：Serializable 的writeObject()和readObject()方法 P580]
 */
public class SerialCtl implements Serializable {
    private String a;
    private transient String b;

    public SerialCtl(String aa, String bb) {
        a = "Not Transient: " + aa;
        b = "Transient: " + bb;
    }

    @Override
    public String toString() {
        return "SerialCtl{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                '}';
    }

    //可以在以下两个方法中控制ObjectOutputStream和ObjectInputStream,用法如下实例
    private void writeObject(ObjectOutputStream stream) throws IOException {
//        stream.defaultWriteObject();     默认的writeObject()
        stream.writeObject( a );
        stream.writeObject( b );   //会将a,b放入类似集合中
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
//        stream.defaultReadObject();    默认的readObject()
        a = "aaaaa";
        b = (String) stream.readObject()+","+ stream.readObject();  //readObject() 会类似指针一个个读
    }

    public static void main(String[] args) throws Exception {
        Logs.getLogs( "SerialCtl" );
        SerialCtl sc = new SerialCtl( "Test1", "Test2" );
        System.out.println( "Before:\n" + sc );
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream( buf );
        o.writeObject( sc );    //调用sc的writeObject()
        ObjectInputStream in = new ObjectInputStream( new ByteArrayInputStream( buf.toByteArray() ) );
        SerialCtl sc2 = (SerialCtl) in.readObject();    //调用((SerialCtl))readObject()
        System.out.println( "After:\n" + sc2 );
    }
}


/**
 * [序列化的控制Externalizable P575]
 */
class Blip1 implements Externalizable {

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}