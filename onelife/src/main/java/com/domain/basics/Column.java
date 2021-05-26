package com.domain.basics;

/**
 * tablecnname:      表中文名
 * columnsequence:   字段顺序
 * columnname:       字段名称
 * columncnname      字段中文名称
 * columntype        字段类型
 * iskey              是否主键
 */
public class Column<V, T extends Number> {
    private T columnsequence;
    private V columnname;
    private V columncnname;
    private V columntype;
    private V iskey;

    @Override
    public String toString() {
        return "Column{" +
                "columnsequence=" + columnsequence +
                ", columnname=" + columnname +
                ", columncnname=" + columncnname +
                ", columntype=" + columntype +
                ", iskey=" + iskey +
                '}';
    }

    public T getColumnsequence() {
        return columnsequence;
    }

    public void setColumnsequence(T columnsequence) {
        this.columnsequence = columnsequence;
    }

    public V getColumnname() {
        return columnname;
    }

    public void setColumnname(V columnname) {
        this.columnname = columnname;
    }

    public V getColumncnname() {
        return columncnname;
    }

    public void setColumncnname(V columncnname) {
        this.columncnname = columncnname;
    }

    public V getColumntype() {
        return columntype;
    }

    public void setColumntype(V columntype) {
        this.columntype = columntype;
    }

    public V getIskey() {
        return iskey;
    }

    public void setIskey(V iskey) {
        this.iskey = iskey;
    }
}
