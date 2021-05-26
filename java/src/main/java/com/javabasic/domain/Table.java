package com.javabasic.domain;

import java.util.HashMap;
import java.util.List;

public class Table {

    private String tablename;
    private String[] columnname;

    public Table(String tablename, List<String> lstr) {
        this.tablename = tablename;
        this.columnname = stcFile( tablename, lstr );
    }

    private String[] stcFile(String tablename, List<String> lstr) {
        HashMap hm = new HashMap();
        for (String str : lstr) {
            String[] sp = str.split( "=" );
            hm.put( sp[0], sp[1] );
        }
        String[] split = null;
        Object str = hm.get( tablename );
        split = str.toString().split( "," );
        return split;
    }

    public String[] getColumnname() {
        return columnname;
    }
}
