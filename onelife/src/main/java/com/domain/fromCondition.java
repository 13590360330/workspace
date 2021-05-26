package com.domain;

import com.domain.basics.Join;

import java.util.ArrayList;

public class fromCondition {
    private String target_tablename;
    private ArrayList<Join> joins;

    public fromCondition(String target_tablename, ArrayList<Join> joins) {
        this.target_tablename = target_tablename;
        this.joins = joins;
    }

    @Override
    public String toString() {
        return "fromCondition{" +
                "target_tablename='" + target_tablename + '\'' +
                ", joins=" + joins +
                '}';
    }

    public String getTarget_tablename() {
        return target_tablename;
    }

    public void setTarget_tablename(String target_tablename) {
        this.target_tablename = target_tablename;
    }

    public ArrayList<Join> getJoins() {
        return joins;
    }

    public void setJoins(ArrayList<Join> joins) {
        this.joins = joins;
    }
}
