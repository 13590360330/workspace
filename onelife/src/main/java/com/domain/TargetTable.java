package com.domain;

import com.domain.basics.Column;
import com.domain.basics.Table;

import java.util.ArrayList;

public class TargetTable extends Table {
    public TargetTable(String sys, String tablename, String tablecnname, ArrayList<Column> columns) {
        super( sys, tablename, tablecnname, columns );
    }

    public static void main(String[] args) {
        ArrayList a = null;
        Table targetTable = new TargetTable( "", "", "", a );
        System.out.println( targetTable.getClass() );

    }

    protected void test() {
        getColumns();
    }
}
