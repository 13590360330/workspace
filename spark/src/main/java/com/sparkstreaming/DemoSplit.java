package com.sparkstreaming;

import com.javabasic.domain.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据文件切分,获得对应数据
 */
public class DemoSplit {

    private String[] str = null;

    public DemoSplit(String[] sp, String dataFile, String tablename, List<String> collect) {
        this.str = splitDateFile( sp, dataFile, tablename, collect );
    }

    /**
     * dataFile是全数据文件,tablename是表,sp是Rdd中每一行的数据切片
     *
     * @param sp
     * @param dataFile
     * @param tablename
     * @return
     */
    private String[] splitDateFile(String[] sp, String dataFile, String tablename, List<String> collect) {
        ArrayList<String> ob = new ArrayList<>();
        Table df = new Table( dataFile, collect );
        Table tb = new Table( tablename, collect );
        List<String> lstr = Arrays.asList( df.getColumnname() );
        String[] columnname1 = tb.getColumnname();
        for (String column : columnname1) {
            try {
                int i = lstr.indexOf( column );
                ob.add( sp[i] );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] objects = ob.toArray( new String[ob.size()] );
        return objects;
    }

    public String[] getStr() {
        return str;
    }
}
