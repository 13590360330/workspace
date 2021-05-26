package com.javabasic.service.thinkinginjava.io;

/**
 *   TODO Cloneable
 */
class Address implements Cloneable {
    private String add;

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    @Override
    public Object clone() {
        Address addr = null;
        try{
            addr = (Address)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return addr;
    }
}

class Student implements Cloneable{
    private int number;

    private Double number2;

    private Address addr;

    @Override
    public String toString() {
        return "Student{" +
                "number=" + number +
                ", number2=" + number2 +
                ", addr=" + addr +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Double getNumber2() {
        return number2;
    }

    public void setNumber2(Double number2) {
        this.number2 = number2;
    }

    public Address getAddr() {
        return addr;
    }

    public void setAddr(Address addr) {
        this.addr = addr;
    }

    @Override
    public Object clone() {
        Student stu = null;
        try{
            stu = (Student)super.clone();   //浅复制
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        stu.addr = (Address)addr.clone();   //深度复制
        return stu;
    }
}

public class CloneableUsage {

    public static void main(String args[]) {

        Address addr = new Address();
        addr.setAdd("杭州市");
        Student stu1 = new Student();
        stu1.setNumber(123);
        Double aaa=345.00;
        stu1.setNumber2(aaa);
        stu1.setAddr(addr);

        Student stu2 = (Student)stu1.clone();

        System.out.println("学生1:" + stu1.getNumber() + ",地址:" + stu1.getAddr().getAdd());
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu2.getAddr().getAdd());
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu1.getNumber2());
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu2.getNumber2());

        addr.setAdd("西湖区");
        aaa=675.00;
        stu1.setNumber2(aaa);

        System.out.println("学生1:" + stu1.getNumber() + ",地址:" + stu1.getAddr().getAdd());
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu2.getAddr().getAdd());
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu1.getNumber2());
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu2.getNumber2());
    }
}
