package com.javabasic.domain;

import java.io.Serializable;

public class AvgCount implements Serializable {

    public AvgCount(int total,int num){
        total_=total;
        num_=num;
    }
    public int total_;
    public int num_;
    public float avg(){
        return total_/(float)num_;
    }
}
