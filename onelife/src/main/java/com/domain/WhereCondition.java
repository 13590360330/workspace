package com.domain;

import com.domain.basics.Where;

import java.util.ArrayList;

public class WhereCondition {
    private String target_tablename;
    private ArrayList<Where> wheres;

    public WhereCondition(String target_tablename, ArrayList<Where> wheres) {
        this.target_tablename = target_tablename;
        this.wheres = wheres;
    }

    @Override
    public String toString() {
        return "WhereCondition{" +
                "target_tablename='" + target_tablename + '\'' +
                ", wheres=" + wheres +
                '}';
    }

    public String getTarget_tablename() {
        return target_tablename;
    }

    public void setTarget_tablename(String target_tablename) {
        this.target_tablename = target_tablename;
    }

    public ArrayList<Where> getWheres() {
        return wheres;
    }

    public void setWheres(ArrayList<Where> wheres) {
        this.wheres = wheres;
    }
}
