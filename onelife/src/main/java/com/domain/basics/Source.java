package com.domain.basics;

import java.util.HashMap;

public class Source<V, T extends String> {
    private T sys;
    private T tablename;
    private T tablecnname;
    private T column;
    private V iskey;
    private T columncnname;
    private T columntype;
    private T columnMapping;
    private T columnMappingdesc;

    @Override
    public String toString() {
        return "Source{" +
                "sys='" + sys + '\'' +
                ", tablename='" + tablename + '\'' +
                ", tablecnname='" + tablecnname + '\'' +
                ", column='" + column + '\'' +
                ", iskey=" + iskey +
                ", columncnname='" + columncnname + '\'' +
                ", columntype='" + columntype + '\'' +
                ", columnMapping='" + columnMapping + '\'' +
                ", columnMappingdesc='" + columnMappingdesc + '\'' +
                '}';
    }

    public T getSys() {
        return sys;
    }

    public void setSys(T sys) {
        this.sys = sys;
    }

    public T getTablename() {
        return tablename;
    }

    public void setTablename(T tablename) {
        this.tablename = tablename;
    }

    public T getTablecnname() {
        return tablecnname;
    }

    public void setTablecnname(T tablecnname) {
        this.tablecnname = tablecnname;
    }

    public T getColumn() {
        return column;
    }

    public void setColumn(T column) {
        this.column = column;
    }

    public V getIskey() {
        return iskey;
    }

    public void setIskey(V iskey) {
        this.iskey = iskey;
    }

    public T getColumncnname() {
        return columncnname;
    }

    public void setColumncnname(T columncnname) {
        this.columncnname = columncnname;
    }

    public T getColumntype() {
        return columntype;
    }

    public void setColumntype(T columntype) {
        this.columntype = columntype;
    }

    public T getColumnMapping() {
        return columnMapping;
    }

    public void setColumnMapping(T columnMapping) {
        this.columnMapping = columnMapping;
    }

    public T getColumnMappingdesc() {
        return columnMappingdesc;
    }

    public void setColumnMappingdesc(T columnMappingdesc) {
        this.columnMappingdesc = columnMappingdesc;
    }
}
