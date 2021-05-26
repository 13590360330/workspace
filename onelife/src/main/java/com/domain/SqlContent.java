package com.domain;

import com.domain.basics.Column;

import java.util.ArrayList;

public class SqlContent<T extends String> {
    private T targetTablename;
    private ArrayList<Column> column;
    private T selectCondition;
    private T fromCondition;
    private T whereCondition;

    @Override
    public String toString() {
        return "SqlContent{" +
                "targetTablename='" + targetTablename + '\'' +
                ", column=" + column +
                ", selectCondition='" + selectCondition + '\'' +
                ", fromCondition='" + fromCondition + '\'' +
                ", whereCondition='" + whereCondition + '\'' +
                '}';
    }

    public T getTargetTablename() {
        return targetTablename;
    }

    public void setTargetTablename(T targetTablename) {
        this.targetTablename = targetTablename;
    }

    public ArrayList<Column> getColumn() {
        return column;
    }

    public void setColumn(ArrayList<Column> column) {
        this.column = column;
    }

    public T getSelectCondition() {
        return selectCondition;
    }

    public void setSelectCondition(T selectCondition) {
        this.selectCondition = selectCondition;
    }

    public T getFromCondition() {
        return fromCondition;
    }

    public void setFromCondition(T fromCondition) {
        this.fromCondition = fromCondition;
    }

    public T getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(T whereCondition) {
        this.whereCondition = whereCondition;
    }

    public static void main(String[] args) {

    }
}
