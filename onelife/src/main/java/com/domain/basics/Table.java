package com.domain.basics;

import java.util.ArrayList;

/**
 * sys           系统名
 * tablename     表名
 * tablecnname   标中文名
 * columns       字段名
 */
public class Table {
    private String sys;
    private String tablename;
    private String tablecnname;
    private ArrayList<Column> columns;

    public Table(String sys, String tablename, String tablecnname, ArrayList<Column> columns) {
        this.sys = sys;
        this.tablename = tablename;
        this.tablecnname = tablecnname;
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Table{" +
                "sys='" + sys + '\'' +
                ", tablename='" + tablename + '\'' +
                ", tablecnname='" + tablecnname + '\'' +
                ", columns=" + columns +
                '}';
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTablecnname() {
        return tablecnname;
    }

    public void setTablecnname(String tablecnname) {
        this.tablecnname = tablecnname;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }
}
